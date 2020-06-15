package iut.group42b.boardgames.client.ui.page.profile.own;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class OwnView extends AbstractView {

    private ImageView logo;
    private Button toLogOutButton;
    private ImageView profileImageView;
    private ImageView profileImageOnProfile;
    private Text numberGamesPlayed;
    private Text usernameText;
    private Text numberFriends;
    private Text numberWin;
    private Button toParametersButton;
    private Text time;
    private Text score;



    public OwnView() {
        super();
        this.logo = (ImageView) findById("logo");
        this.toLogOutButton = (Button) findById("logout");
        this.profileImageView = (ImageView) findById("profile-picture");
        this.profileImageOnProfile = (ImageView) findById("own-avatar");
        this.numberGamesPlayed = (Text) findById("number-game-played");
        this.usernameText = (Text) findById("username");
        this.numberFriends = (Text) findById("number-friends");
        this.numberWin = (Text) findById("number-win");
        this.toParametersButton = (Button) findById("toparameters-button");
        this.time = (Text) findById("time");
        this.score = (Text) findById("score");

    }

    @Override
    public String getViewPath() {
        return "own-profile.fxml";
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

    public Button getToParametersButton() {
        return toParametersButton;
    }

    public ImageView getLogo() {
        return logo;
    }

    public ImageView getProfileImageOnProfile() {
        return profileImageOnProfile;
    }

    public Text getNumberGamesPlayed() {
        return numberGamesPlayed;
    }

    public Text getNumberFriends() {
        return numberFriends;
    }

    public Text getNumberWin() {
        return numberWin;
    }

    public Text getTime() {
        return time;
    }

    public Text getScore() {
        return score;
    }

    @Override
    public IController createController() {
        return new OwnController();
    }
}
