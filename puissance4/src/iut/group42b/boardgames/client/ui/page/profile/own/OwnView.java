package iut.group42b.boardgames.client.ui.page.profile.own;

import iut.group42b.boardgames.client.ui.component.circularprogressbar.RingProgressIndicator;
import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.social.model.gamehistory.GameHistoryItem;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class OwnView extends AbstractView {

	private final ImageView logoImageView;
	private final Button toLogOutButton;
	private final Button toSettingsButton;
	private final ImageView profileImageView;
	private final ImageView profileImageOnProfile;
	private final Text numberGamesPlayedText;
	private final Text usernameText;
	private final Text numberFriendsText;
	private final Text numberWinText;
	private final Text timeText;
	private final Text scoreText;
	private final AnchorPane winCircle;
	private final AnchorPane defeatCircle;
	private final ListView gameHistory;
	private final UserProfile userprofile;

	public OwnView(UserProfile up) {
		super();
		this.logoImageView = this.findById("logo");
		this.toLogOutButton = this.findById("logout");
		this.profileImageView = this.findById("profile-picture");
		this.profileImageOnProfile = this.findById("own-avatar");
		this.numberGamesPlayedText = this.findById("number-game-played");
		this.usernameText = this.findById("username");
		this.numberFriendsText = this.findById("number-friends");
		this.numberWinText = this.findById("number-win");
		this.timeText = this.findById("time");
		this.scoreText = this.findById("score");
		this.toSettingsButton = this.findById("own-profile-button-goto-settings");
		this.winCircle = this.findById("win-circle");
		this.defeatCircle = this.findById("defeat-circle");
		this.gameHistory = this.findById("own-profile-listview-history");
		this.winCircle.getChildren().add(new RingProgressIndicator());
		this.defeatCircle.getChildren().add(new RingProgressIndicator());
		this.userprofile = up;
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

	public Button getToLogOutButton() {
		return this.toLogOutButton;
	}

	public ImageView getProfileImageView() {
		return this.profileImageView;
	}

	public Text getUsernameText() {
		return this.usernameText;
	}

	public ImageView getLogoImageView() {
		return this.logoImageView;
	}

	public ImageView getProfileImageOnProfile() {
		return this.profileImageOnProfile;
	}

	public Text getNumberGamesPlayedText() {
		return this.numberGamesPlayedText;
	}

	public Text getNumberFriendsText() {
		return this.numberFriendsText;
	}

	public Text getNumberWinText() {
		return this.numberWinText;
	}

	public Text getTimeText() {
		return this.timeText;
	}

	public UserProfile getUserprofile() {
		return this.userprofile;
	}

	public Text getScoreText() {
		return this.scoreText;
	}

	public Button getToSettingsButton() {
		return this.toSettingsButton;
	}

	public ListView<GameHistoryItem> getGameHistory() {
		return this.gameHistory;
	}

	@Override
	public String getViewPath() {
		return "own-profile.fxml";
	}

	@Override
	public IController createController() {
		return new OwnController();
	}
}
