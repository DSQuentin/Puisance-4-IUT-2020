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
	private final Button logoutButton;

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
		this.logoutButton = this.findById("home-button-tologout");


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

	public Button getLogoutButton() {
		return this.logoutButton;
	}

	public Button getFightButton() {
		return this.fightButton;
	}
}