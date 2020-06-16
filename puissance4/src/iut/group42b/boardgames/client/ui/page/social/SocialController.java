package iut.group42b.boardgames.client.ui.page.social;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.ui.list.friend.MessageFriendListViewCellController;
import iut.group42b.boardgames.client.ui.list.message.MessagesListViewCellController;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
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
import javafx.scene.image.Image;

import java.sql.Date;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class SocialController implements IController, INetworkHandler {

	/* UI */
	private SocialView view;

	/* Controllers */
	private MessageFriendListViewCellController messageFriendListViewCellController;
	private MessagesListViewCellController messagesListViewCellController;

	/* Variables */
	private final ObservableList<UserProfile> friendObservableList;
	private final ObservableList<ExchangedMessage> messagesList;
	private FilteredList<UserProfile> friendFilterList;
	private UserProfile currentlyTalkingUserProfile;


	/* Constructor */
	public SocialController() {
		this.friendObservableList = FXCollections.observableArrayList();
		this.messagesList = FXCollections.observableArrayList();
	}

	@Override
	public void handle(ActionEvent event) {

		if (event.getSource() == this.view.getSendMessageButton()) {
			if (currentlyTalkingUserProfile != null && !this.view.getMessageInputTextField().getText().isEmpty()){
				String textMessageToSend = this.view.getMessageInputTextField().getText();

				ExchangedMessage message = new ExchangedMessage(currentlyTalkingUserProfile.getId(), textMessageToSend) ;

				NetworkInterface.get().getSocketHandler().queue(new SendMessagePacket(message));

				this.view.getMessageInputTextField().setText("");
			}
		}

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
		this.view.getMessagesListView().setItems(this.messagesList);
		this.view.getSendMessageButton().setOnAction(this);

		this.view.getMessagesListView().setFocusTraversable( false );
		this.view.getFriendsListView().setCellFactory(this.messageFriendListViewCellController.cellFactory());
		this.view.getMessagesListView().setCellFactory(this.messagesListViewCellController.cellFactory());

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
		messagesListViewCellController.setCurrentlyTalkingToUserProfile(currentlyTalkingUserProfile = targetUserProfile);

		this.messagesList.clear();

		NetworkInterface.get().getSocketHandler().queue(new MessageListPacket(targetUserProfile.getId()));
	}

}