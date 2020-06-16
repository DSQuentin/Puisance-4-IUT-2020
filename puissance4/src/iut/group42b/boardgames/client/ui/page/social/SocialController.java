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
import iut.group42b.boardgames.social.packet.friendship.FriendListPacket;
import iut.group42b.boardgames.social.packet.message.MessageListPacket;
import iut.group42b.boardgames.social.packet.message.SendMessagePacket;
import javafx.application.Platform;
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

	/* Variables */
	private final ObservableList<UserProfile> friendObservableList;
	private final ObservableList<ExchangedMessage> messagesList;
	/* UI */
	private SocialView view;
	/* Controllers */
	private MessageFriendListViewCellController messageFriendListViewCellController;
	private MessagesListViewCellController messagesListViewCellController;
	private FilteredList<UserProfile> friendFilterList;
	private UserProfile currentlyTalkingUserProfile;


	/* Constructor */
	public SocialController() {
		this.friendObservableList = FXCollections.observableArrayList();
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

		this.view.getLogo().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new HomeView());
		});
		this.view.getProfileImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new OwnView());
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
				this.friendFilterList.setPredicate((userProfile) -> true);
			} else {
				this.friendFilterList.setPredicate((userProfile) -> userProfile.getUsername().contains(filter));
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

			Platform.runLater(() -> {
				this.messagesList.clear();
				this.messagesList.addAll(messageListPacket.getMessages());
			});
		}
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
		this.messagesListViewCellController.setCurrentlyTalkingToUserProfile(this.currentlyTalkingUserProfile = targetUserProfile);

		this.messagesList.clear();

		NetworkInterface.get().getSocketHandler().queue(new MessageListPacket(targetUserProfile.getId()));
	}

}