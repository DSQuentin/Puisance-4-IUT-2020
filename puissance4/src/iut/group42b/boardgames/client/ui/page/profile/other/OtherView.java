package iut.group42b.boardgames.client.ui.page.profile.other;

import iut.group42b.boardgames.client.ui.component.circularprogressbar.RingProgressIndicator;
import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
	private final AnchorPane winCircle;
	private final AnchorPane defeatCircle;

	public OtherView() {
		super();
		this.logoImageView = this.findById("other-profile-imageview-logo");
		this.avatarImageView = this.findById("other-profile-imageview-avatar");
		this.gamesPlayedText = this.findById("other-profile-text-games-played");
		this.usernameText = this.findById("other-profile-text-username");
		this.friendsText = this.findById("other-profile-text-friends");
		this.winsText = this.findById("other-profile-text-wins");
		this.addFriendButton = this.findById("other-profile-button-add-friend");
		this.averageTimeText = this.findById("other-profile-text-average-time-per-game");
		this.maxScoreText = this.findById("other-profile-text-max-score");
		this.logoutButton = this.findById("other-button-logout");
		this.ownAvatarImageView = this.findById("profile-picture");
		this.winCircle = this.findById("win-cirle");
		this.defeatCircle = this.findById("defeat-circle");
		this.winCircle.getChildren().add(new RingProgressIndicator());
		this.defeatCircle.getChildren().add(new RingProgressIndicator());
	}

	public AnchorPane getWinCircleAnchor() {
		return this.winCircle;
	}

	public RingProgressIndicator getWinCircle() {
		for (Node n : this.getWinCircleAnchor().getChildren()) {
			if (n instanceof RingProgressIndicator) {
				return (RingProgressIndicator) n;
			}
		}
		return new RingProgressIndicator();
	}

	public AnchorPane getDefeatCircleAnchor() {
		return this.defeatCircle;
	}

	public RingProgressIndicator getDefeatCircle() {
		for (Node n : this.getDefeatCircleAnchor().getChildren()) {
			if (n instanceof RingProgressIndicator) {
				return (RingProgressIndicator) n;
			}
		}
		return new RingProgressIndicator();
	}

	@Override
	public String getViewPath() {
		return "other-profile.fxml";
	}

	public ImageView getLogoImageView() {
		return this.logoImageView;
	}

	public ImageView getAvatarImageView() {
		return this.avatarImageView;
	}

	public Text getGamesPlayedText() {
		return this.gamesPlayedText;
	}

	public Text getUsernameText() {
		return this.usernameText;
	}

	public Text getFriendsText() {
		return this.friendsText;
	}

	public Text getWinsText() {
		return this.winsText;
	}

	public Button getAddFriendButton() {
		return this.addFriendButton;
	}

	public Text getAverageTimeText() {
		return this.averageTimeText;
	}

	public Text getMaxScoreText() {
		return this.maxScoreText;
	}


	public Button getLogoutButton() {
		return this.logoutButton;
	}

	public ImageView getOwnAvatarImageView() {
		return this.ownAvatarImageView;
	}

	@Override
	public IController createController() {
		return new OtherController();
	}
}
