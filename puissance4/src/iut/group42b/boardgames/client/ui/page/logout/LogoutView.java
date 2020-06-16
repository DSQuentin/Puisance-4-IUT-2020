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
		this.backHome = this.findById("logout-button-gohome");
		this.disconnectMsg = this.findById("logout-msg");
		this.toRegisterButton = this.findById("index-button-register");
		this.toLoginButton = this.findById("index-button-login");
	}


	@Override
	public String getViewPath() {
		return "logout.fxml";
	}

	public Button getToRegisterButton() {
		return this.toRegisterButton;
	}

	public Button getToLoginButton() {
		return this.toLoginButton;
	}

	public Text getDisconnectMsg() {
		return this.disconnectMsg;
	}

	public Button getBackHome() {
		return this.backHome;
	}

	@Override
	public IController createController() {
		return new LogoutController();
	}
}
