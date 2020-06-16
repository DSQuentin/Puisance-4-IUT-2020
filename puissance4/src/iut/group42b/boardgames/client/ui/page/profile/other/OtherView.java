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
		this.logoImageView = (ImageView) this.findById("other-profile-imageview-logo");
		this.avatarImageView = (ImageView) this.findById("other-profile-imageview-avatar");
		this.gamesPlayedText = (Text) this.findById("other-profile-text-games-played");
		this.usernameText = (Text) this.findById("other-profile-text-username");
		this.friendsText = (Text) this.findById("other-profile-text-friends");
		this.winsText = (Text) this.findById("other-profile-text-wins");
		this.addFriendButton = (Button) this.findById("other-profile-button-add-friend");
		this.averageTimeText = (Text) this.findById("other-profile-text-average-time-per-game");
		this.maxScoreText = (Text) this.findById("other-profile-text-max-score");
		this.logoutButton = (Button) this.findById("other-button-logout");
		this.ownAvatarImageView = (ImageView) this.findById("profile-picture");
		this.winCircle = (AnchorPane) this.findById("win-cirle");
		this.defeatCircle = (AnchorPane) this.findById("defeat-circle");
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
