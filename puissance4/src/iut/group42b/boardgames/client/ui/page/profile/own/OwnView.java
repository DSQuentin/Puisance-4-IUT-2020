package iut.group42b.boardgames.client.ui.page.profile.own;

import iut.group42b.boardgames.client.ui.component.circularprogressbar.RingProgressIndicator;
import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.Node;
import javafx.scene.control.Button;
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


	public OwnView() {
		super();
		this.logoImageView = (ImageView) this.findById("logo");
		this.toLogOutButton = (Button) this.findById("logout");
		this.profileImageView = (ImageView) this.findById("profile-picture");
		this.profileImageOnProfile = (ImageView) this.findById("own-avatar");
		this.numberGamesPlayedText = (Text) this.findById("number-game-played");
		this.usernameText = (Text) this.findById("username");
		this.numberFriendsText = (Text) this.findById("number-friends");
		this.numberWinText = (Text) this.findById("number-win");
		this.timeText = (Text) this.findById("time");
		this.scoreText = (Text) this.findById("score");
		this.toSettingsButton = (Button) this.findById("own-profile-button-goto-settings");
		this.winCircle = (AnchorPane) this.findById("win-circle");
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

	public Text getScoreText() {
		return this.scoreText;
	}

	public Button getToSettingsButton() {
		return this.toSettingsButton;
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
