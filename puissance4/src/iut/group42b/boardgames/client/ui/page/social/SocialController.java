package iut.group42b.boardgames.client.ui.page.social;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.list.friend.MessageFriendListViewCellController;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.client.ui.page.profile.own.OwnView;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.social.packet.friendship.FriendListPacket;
import iut.group42b.boardgames.social.packet.message.MessageListPacket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class SocialController implements IController, INetworkHandler {

	/* UI */
	private SocialView view;

	/* Controllers */
	private MessageFriendListViewCellController messageFriendListViewCellController;

	/* Variables */
	private final ObservableList<UserProfile> friendObservableList;
	private FilteredList<UserProfile> friendFilterList;

	/* Constructor */
	public SocialController() {
		this.friendObservableList = FXCollections.observableArrayList();
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getLogoutButton()) {
			UserInterface.get().set(new LogoutView());
		}
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof SocialView)) {
			throw new IllegalArgumentException();
		}

		this.view = (SocialView) view;

		this.messageFriendListViewCellController = new MessageFriendListViewCellController(this);

		this.view.getFriendsListView().setItems(this.friendObservableList);
		this.view.getFriendsListView().setCellFactory(this.messageFriendListViewCellController.cellFactory());

		this.view.getProfileImageView().setImage(new Image(NetworkInterface.get().getSocketHandler().getUserProfile().getImageUrl(), true));		this.view.getLogoutButton().setOnAction(this);
		this.view.getLogo().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new HomeView());
		});
		this.view.getProfileImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new OwnView());
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

			messageListPacket.getMessages().stream().forEach(System.out::println);
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
		NetworkInterface.get().getSocketHandler().queue(new MessageListPacket(targetUserProfile.getId()));
	}

}