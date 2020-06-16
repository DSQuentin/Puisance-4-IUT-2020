package iut.group42b.boardgames.client.ui.page.social;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SocialView extends AbstractView {

	/* UI */
	private final ListView friendsListView;
	private final TextField friendSearchInputTextField;
	private final TextField messageInputTextField;
	private final Button sendMessageButton;
	private final Button fightButton;
	private final ImageView logo;
	private final ImageView profileImageView;
	private final Button logoutButton;

	/* Constructor */
	public SocialView() {
		super();

		this.friendsListView = (ListView) this.findById("home-list-view-friend");
		this.friendSearchInputTextField = (TextField) this.findById("home-textfield-search-friend");
		this.messageInputTextField = (TextField) this.findById("home-textfield-message-input");
		this.sendMessageButton = (Button) this.findById("home-button-send-message");
		this.fightButton = (Button) this.findById("home-button-fight");
		this.logo = (ImageView) this.findById("logo");
		this.profileImageView = (ImageView) this.findById("home-imageview-profile");
		this.logoutButton = (Button) this.findById("home-button-tologout");
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
}