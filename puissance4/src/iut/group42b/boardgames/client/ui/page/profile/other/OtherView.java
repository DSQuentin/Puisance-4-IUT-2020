package iut.group42b.boardgames.client.ui.page.profile.other;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class OtherView extends AbstractView {
	private final ImageView logoImageView;
	private final ImageView ownAvatarImageView;
	private final ImageView avatarImageView;
	private final Text gamesPlayedText;
	private final Text usernameText;
	private final Text friendsText;
	private final Text winsText;
	private final Button addFriendButton;
	private final Text averageTimeText;
	private final Text maxScoreText;
	private final Button logoutButton;

	public OtherView() {
		super();
		this.logoImageView = (ImageView) findById("other-profile-imageview-logo");
		this.avatarImageView = (ImageView) findById("other-profile-imageview-avatar");
		this.gamesPlayedText = (Text) findById("other-profile-text-games-played");
		this.usernameText = (Text) findById("other-profile-text-username");
		this.friendsText = (Text) findById("other-profile-text-friends");
		this.winsText = (Text) findById("other-profile-text-wins");
		this.addFriendButton = (Button) findById("other-profile-button-add-friend");
		this.averageTimeText = (Text) findById("other-profile-text-average-time-per-game");
		this.maxScoreText = (Text) findById("other-profile-text-max-score");
		this.logoutButton = (Button) findById("other-button-logout");
		this.ownAvatarImageView = (ImageView) findById("profile-picture");
	}

	@Override
	public String getViewPath() {
		return "other-profile.fxml";
	}

	public ImageView getLogoImageView() {
		return logoImageView;
	}

	public ImageView getAvatarImageView() {
		return avatarImageView;
	}

	public Text getGamesPlayedText() {
		return gamesPlayedText;
	}

	public Text getUsernameText() {
		return usernameText;
	}

	public Text getFriendsText() {
		return friendsText;
	}

	public Text getWinsText() {
		return winsText;
	}

	public Button getAddFriendButton() {
		return addFriendButton;
	}

	public Text getAverageTimeText() {
		return averageTimeText;
	}

	public Text getMaxScoreText() {
		return maxScoreText;
	}


	public Button getLogoutButton() {
		return logoutButton;
	}

	public ImageView getOwnAvatarImageView() {
		return ownAvatarImageView;
	}

	@Override
	public IController createController() {
		return new OtherController();
	}
}
