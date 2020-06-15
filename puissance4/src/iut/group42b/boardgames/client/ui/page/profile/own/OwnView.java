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
		this.logoImageView = (ImageView) findById("logo");
		this.toLogOutButton = (Button) findById("logout");
		this.profileImageView = (ImageView) findById("profile-picture");
		this.profileImageOnProfile = (ImageView) findById("own-avatar");
		this.numberGamesPlayedText = (Text) findById("number-game-played");
		this.usernameText = (Text) findById("username");
		this.numberFriendsText = (Text) findById("number-friends");
		this.numberWinText = (Text) findById("number-win");
		this.timeText = (Text) findById("time");
		this.scoreText = (Text) findById("score");
		this.toSettingsButton = (Button) findById("own-profile-button-goto-settings");
		this.winCircle = (AnchorPane) findById("win-circle");
		this.defeatCircle = (AnchorPane) findById("defeat-circle");
		this.winCircle.getChildren().add(new RingProgressIndicator());
		this.defeatCircle.getChildren().add(new RingProgressIndicator());
	}

	public AnchorPane getWinCircleAnchor() {
		return winCircle;
	}

	public RingProgressIndicator getWinCircle(){
		for (Node n : this.getWinCircleAnchor().getChildren()){
			if (n instanceof RingProgressIndicator){
				return (RingProgressIndicator) n;
			}
		}
		return new RingProgressIndicator();
	}

	public AnchorPane getDefeatCircleAnchor() {
		return defeatCircle;
	}

	public RingProgressIndicator getDefeatCircle(){
		for (Node n : this.getDefeatCircleAnchor().getChildren()){
			if (n instanceof RingProgressIndicator){
				return (RingProgressIndicator) n;
			}
		}
		return new RingProgressIndicator();
	}

	public Button getToLogOutButton() {
		return toLogOutButton;
	}

	public ImageView getProfileImageView() {
		return profileImageView;
	}

	public Text getUsernameText() {
		return usernameText;
	}

	public ImageView getLogoImageView() {
		return logoImageView;
	}

	public ImageView getProfileImageOnProfile() {
		return profileImageOnProfile;
	}

	public Text getNumberGamesPlayedText() {
		return numberGamesPlayedText;
	}

	public Text getNumberFriendsText() {
		return numberFriendsText;
	}

	public Text getNumberWinText() {
		return numberWinText;
	}

	public Text getTimeText() {
		return timeText;
	}

	public Text getScoreText() {
		return scoreText;
	}

	public Button getToSettingsButton() {
		return toSettingsButton;
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