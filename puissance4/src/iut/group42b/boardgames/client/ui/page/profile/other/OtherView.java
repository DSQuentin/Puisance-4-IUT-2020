package iut.group42b.boardgames.client.ui.page.profile.other;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import jdk.internal.org.objectweb.asm.tree.FieldInsnNode;

import javax.print.attribute.standard.MediaSize;

public class OtherView extends AbstractView {
    private final ImageView logo;
    private final ImageView ownAvatar;
    private final ImageView avatar;
    private final Text gamesPlayed;
    private final Text username;
    private final Text friends;
    private final Text wins;
    private final Button addFriend;
    private final Text averageTime;
    private final Text maxScore;
    private final TextField msgInput;
    private final Button sendMsg;
    //private final ImageView sendIcon;
    private final Button logout;

    public OtherView() {
        super();
        this.logo = (ImageView) findById("other-profile-imageview-logo");
        this.avatar = (ImageView) findById("other-profile-imageview-avatar");
        this.gamesPlayed = (Text) findById("other-profile-text-games-played");
        this.username = (Text) findById("other-profile-text-username");
        this.friends = (Text) findById("other-profile-text-friends");
        this.wins = (Text) findById("other-profile-text-wins");
        this.addFriend = (Button) findById("other-profile-button-add-friend");
        this.averageTime = (Text) findById("other-profile-text-average-time-per-game");
        this.maxScore = (Text) findById("other-profile-text-max-score");
        this.msgInput = (TextField) findById("other-profile-textfield-message-input");
        this.sendMsg = (Button) findById("other-profile-send-message");
        //this.sendIcon = (ImageView) findById("other-profile-send-icon");
        this.logout = (Button) findById("other-button-logout");
        this.ownAvatar = (ImageView) findById("profile-picture");
    }

    @Override
    public String getViewPath() {
        return "other-profile.fxml";
    }

    public ImageView getLogo() {
        return logo;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public Text getGamesPlayed() {
        return gamesPlayed;
    }

    public Text getUsername() {
        return username;
    }

    public Text getFriends() {
        return friends;
    }

    public Text getWins() {
        return wins;
    }

    public Button getAddFriend() {
        return addFriend;
    }

    public Text getAverageTime() {
        return averageTime;
    }

    public Text getMaxScore() {
        return maxScore;
    }

    public TextField getMsgInput() {
        return msgInput;
    }

    public Button getSendMsg() {
        return sendMsg;
    }

    /*public ImageView getSendIcon() {
        return sendIcon;
    }*/

    public Button getLogout() {
        return logout;
    }

    public ImageView getOwnAvatar() {
        return ownAvatar;
    }

    @Override
    public IController createController() {
        return new OtherController();
    }
}
