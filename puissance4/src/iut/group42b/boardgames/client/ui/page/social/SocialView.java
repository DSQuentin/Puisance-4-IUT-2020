package iut.group42b.boardgames.client.ui.page.social;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.social.model.ExchangedMessage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SocialView extends AbstractView {

	/* UI */
	private final ImageView myUserProfileImageView;
	private final ListView friendsListView;
	private final ListView messagesListView;
	private final TextField friendSearchInputTextField;
	private final TextField messageInputTextField;
	private final Button sendMessageButton;
	private final Button fightButton;
	private final ImageView logo;
	private final ImageView profileImageView;
	private final Button logoutButton;
	private final Button addFriendsButton;
	private final Button checkProfile;
	private final ImageView otherProfile;


	/* Constructor */
	public SocialView() {
		super();

		this.myUserProfileImageView = this.findById("home-imageview-profile");
		this.friendsListView = this.findById("home-list-view-friend");
		this.messagesListView = this.findById("home-list-view-messages");
		this.friendSearchInputTextField = this.findById("home-textfield-search-friend");
		this.messageInputTextField = this.findById("home-textfield-message-input");
		this.sendMessageButton = this.findById("home-button-send-message");
		this.fightButton = this.findById("home-button-fight");
		this.logo = this.findById("logo");
		this.profileImageView = this.findById("home-imageview-profile");
		this.logoutButton = this.findById("home-button-tologout");
		this.addFriendsButton = this.findById("social-button-add-friend");
		this.checkProfile = this.findById("check-other-profile");
		this.otherProfile = this.findById("other-profile");
	}

	@Override
	public String getViewPath() {
		return "social.fxml";
	}

	@Override
	public IController createController() {
		return new SocialController();
	}

	public ListView getFriendsListView() {
		return this.friendsListView;
	}

	public TextField getFriendSearchInputTextField() {
		return this.friendSearchInputTextField;
	}

	public ListView<ExchangedMessage> getMessagesListView() {
		return this.messagesListView;
	}

	public ImageView getMyUserProfileImageView() {
		return this.myUserProfileImageView;
	}

	public TextField getMessageInputTextField() {
		return this.messageInputTextField;
	}

	public Button getSendMessageButton() {
		return this.sendMessageButton;
	}

	public Button getFightButton() {
		return this.fightButton;
	}

	public ImageView getLogo() {
		return this.logo;
	}

	public ImageView getProfileImageView() {
		return this.profileImageView;
	}

	public Button getLogoutButton() {
		return this.logoutButton;
	}

	public Button getAddFriendsButton() {
		return this.addFriendsButton;
	}

	public Button getCheckProfile() {
		return this.checkProfile;
	}

	public ImageView getOtherProfile() {
		return this.otherProfile;
	}
}