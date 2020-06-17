package iut.group42b.boardgames.client.ui.page.social;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.helper.NoSelectionModel;
import iut.group42b.boardgames.client.ui.list.friend.MessageFriendListViewCellController;
import iut.group42b.boardgames.client.ui.list.message.MessagesListViewCellController;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.client.ui.page.profile.own.OwnView;
import iut.group42b.boardgames.game.GameRegistry;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.impl.connect4.Connect4Game;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.ExchangedMessage;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.social.model.aware.ReadAwareUserProfile;
import iut.group42b.boardgames.social.packet.friendship.FriendListPacket;
import iut.group42b.boardgames.social.packet.message.MessageListPacket;
import iut.group42b.boardgames.social.packet.message.OpenedMessagesPacket;
import iut.group42b.boardgames.social.packet.message.SendMessagePacket;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class SocialController implements IController, INetworkHandler {

	/* UI */
	private SocialView view;

	/* Controllers */
	private MessageFriendListViewCellController messageFriendListViewCellController;
	private MessagesListViewCellController messagesListViewCellController;

	/* Variables */
	private final ObservableList<ReadAwareUserProfile> friendObservableList;
	private final ObservableList<ExchangedMessage> messagesList;
	private FilteredList<ReadAwareUserProfile> friendFilterList;
	private UserProfile currentlyTalkingUserProfile;
	private boolean scrolledToBottom;


	/* Constructor */
	public SocialController() {
		this.friendObservableList = FXCollections.observableArrayList((friend) -> new Observable[]{friend.connectedProperty(), friend.notReadProperty()});
		this.messagesList = FXCollections.observableArrayList();
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getLogoutButton()) {
			UserInterface.get().set(new LogoutView());
		}

		if (event.getSource() == this.view.getSendMessageButton()) {
			this.callMessageButton();
		} else if (event.getSource() == this.view.getLogoutButton()) {
			UserInterface.get().set(new LogoutView());
		} else if (event.getSource() == this.view.getAddFriendsButton()) {
			this.addFriendsAlertBox();
		} else if (event.getSource() == this.view.getFightButton()) {
			List<String> choices = GameRegistry.get().playables().stream().map(IGame::getName).collect(Collectors.toList());

			IGame defaultGame = new Connect4Game();
			ChoiceDialog<String> dialog = new ChoiceDialog<>(defaultGame.getName(), choices);
			dialog.setTitle(Messages.UI_ALERT_TITLE_FIGHT.use());
			dialog.setHeaderText(Messages.UI_ALERT_HEADER_FIGHT.use());
			dialog.setContentText(Messages.UI_ALERT_CONTENT_FIGHT.use());

			Optional<String> result = dialog.showAndWait();
			// TODO : send invitations to user
			result.ifPresent(s -> System.out.println("Your choice: " + s));

		} else if (event.getSource() == this.view.getCheckProfile() && this.currentlyTalkingUserProfile != null) {
			UserInterface.get().set(new OwnView(this.currentlyTalkingUserProfile));
		}
	}

	public void addFriendsAlertBox() {
		TextInputDialog dialog = new TextInputDialog("Walter02");
		dialog.setTitle(Messages.UI_ALERT_TITLE_FRIEND.use());
		dialog.setHeaderText(Messages.UI_ALERT_HEADER_FRIEND.use());
		dialog.setContentText(Messages.UI_ALERT_CONTENT_FRIEND.use());

		//TODO : call server
		Optional<String> result = dialog.showAndWait();

		result.ifPresent(name -> System.out.println("Your name: " + name));
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof SocialView)) {
			throw new IllegalArgumentException();
		}

		this.view = (SocialView) view;

		this.messageFriendListViewCellController = new MessageFriendListViewCellController(this);
		this.messagesListViewCellController = new MessagesListViewCellController();

		this.view.getMyUserProfileImageView().setImage(new Image(NetworkInterface.get().getSocketHandler().getUserProfile().getImageUrl(), true));

		this.view.getFriendsListView().setItems(this.friendObservableList);
		this.view.getFriendsListView().setCellFactory(this.messageFriendListViewCellController.cellFactory());

		this.view.getMessagesListView().setItems(this.messagesList);
		this.view.getMessagesListView().setSelectionModel(new NoSelectionModel<>());
		this.view.getMessagesListView().setCellFactory(this.messagesListViewCellController.cellFactory());

		this.view.getSendMessageButton().setOnAction(this);

		this.view.getProfileImageView().setImage(new Image(NetworkInterface.get().getSocketHandler().getUserProfile().getImageUrl(), true));
		this.view.getLogoutButton().setOnAction(this);
		this.view.getFightButton().setOnAction(this);
		this.view.getAddFriendsButton().setOnAction(this);


		this.view.getCheckProfile().setOnAction(this);


		this.view.getLogo().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new HomeView());
		});
		this.view.getProfileImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new OwnView(this.currentlyTalkingUserProfile));
		});

		this.view.getMessageInputTextField().setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				this.callMessageButton();
			}
		});

		this.view.getFriendSearchInputTextField().textProperty().addListener((observable) -> {
			String filter = this.view.getFriendSearchInputTextField().getText();

			if (this.friendFilterList == null) {
				return;
			}

			if (filter == null || filter.length() == 0) {
				this.friendFilterList.setPredicate((user) -> true);
			} else {
				this.friendFilterList.setPredicate((user) -> user.getUserProfile().getUsername().contains(filter));
			}
		});

		NetworkInterface.get().getSocketHandler().queue(new FriendListPacket());
	}

	public void callMessageButton() {
		if (this.currentlyTalkingUserProfile != null && !this.view.getMessageInputTextField().getText().isEmpty()) {
			String textMessageToSend = this.view.getMessageInputTextField().getText();

			ExchangedMessage message = new ExchangedMessage(this.currentlyTalkingUserProfile.getId(), textMessageToSend);

			NetworkInterface.get().getSocketHandler().queue(new SendMessagePacket(message));

			this.view.getMessageInputTextField().setText("");
		}
	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {
		if (packet instanceof FriendListPacket) {
			FriendListPacket friendListPacket = (FriendListPacket) packet;

			this.friendObservableList.clear();
			this.friendObservableList.addAll(friendListPacket.getUsers());

			this.friendFilterList = new FilteredList<>(this.friendObservableList, (item) -> true);
			this.view.getFriendsListView().setItems(this.friendFilterList);

			this.view.getFriendSearchInputTextField().setText("");

		} else if (packet instanceof MessageListPacket) {
			MessageListPacket messageListPacket = (MessageListPacket) packet;

			ReadAwareUserProfile target = this.findFriendById(messageListPacket.getSenderId());

			if (this.currentlyTalkingUserProfile != null && this.currentlyTalkingUserProfile.getId() == messageListPacket.getSenderId()) {
				NetworkInterface.get().getSocketHandler().queue(new OpenedMessagesPacket(this.currentlyTalkingUserProfile.getId()));

				if (target != null) {
					target.notReadProperty().set(0);
				}

				Platform.runLater(() -> {
					this.messagesList.clear();
					this.messagesList.addAll(messageListPacket.getMessages());

					if (!this.scrolledToBottom) {
						this.scrolledToBottom = true;

						this.view.getMessagesListView().scrollTo(this.messagesList.size() - 1);
					}
				});
			} else {
				if (target != null) {
					target.notReadProperty().set(target.notReadProperty().get() + 1);
				}
			}
		}
	}

	private ReadAwareUserProfile findFriendById(int id) {
		for (ReadAwareUserProfile profile : this.friendObservableList) {
			if (profile.getUserProfile().getId() == id) {
				return profile;
			}
		}

		return null;
	}

	@Override
	public void onMount() {
		NetworkInterface.get().getSocketHandler().subscribe(this);
	}

	@Override
	public void onUnmount() {
		NetworkInterface.get().getSocketHandler().unsubscribe(this);
	}

	public void requestMessageList(UserProfile targetUserProfile) {
		this.scrolledToBottom = false;

		this.messagesListViewCellController.setCurrentlyTalkingToUserProfile(this.currentlyTalkingUserProfile = targetUserProfile);

		this.messagesList.clear();

		NetworkInterface.get().getSocketHandler().queue(new MessageListPacket(targetUserProfile.getId()));
	}

}