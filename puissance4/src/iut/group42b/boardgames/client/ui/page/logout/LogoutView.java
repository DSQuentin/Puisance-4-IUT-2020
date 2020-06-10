package iut.group42b.boardgames.client.ui.page.logout;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class LogoutView extends AbstractView {
    private final Button backHome;
    private final Button toRegisterButton;
    private final Button toLoginButton;
    private final Text disconnectMsg;

    public LogoutView() {
        super();
        this.backHome = (Button) findById("logout-button-gohome");
        this.disconnectMsg = (Text) findById("logout-msg");
        this.toRegisterButton = (Button) findById("index-button-register");
        this.toLoginButton = (Button) findById("index-button-login");
    }


    @Override
    public String getViewPath() {
        return "logout.fxml";
    }

    public Button getToRegisterButton() {
        return toRegisterButton;
    }

    public Button getToLoginButton() {
        return toLoginButton;
    }

    public Text getDisconnectMsg() {
        return disconnectMsg;
    }

    public Button getBackHome() {
        return backHome;
    }

    @Override
    public IController createController() {
        return new LogoutController();
    }
}
