package iut.group42b.boardgames.client.ui.page.register;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.login.LoginView;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserAuthentificationErrorPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserAuthentificationSuccessPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserRegisterPacket;
import iut.group42b.boardgames.util.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;

public class RegisterController implements IController, INetworkHandler {

	/* Logger */
	private final static Logger LOGGER = new Logger(RegisterController.class);

	/* Variables */
	private String lastEmail, lastPassword;
	private RegisterView registerVue;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.registerVue.getSubmitButton()) {
			this.callSubmitButton();

		} else if (event.getSource() == this.registerVue.getToLoginHyperlink()) {
			try {
				UserInterface.get().set(new LoginView());
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof RegisterView)) {
			throw new IllegalArgumentException();
		}

		this.registerVue = (RegisterView) view;

		this.registerVue.getSubmitButton().setOnAction(this);
		this.registerVue.getToLoginHyperlink().setOnAction(this);

		this.registerVue.getPasswordPasswordField().setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				this.callSubmitButton();
			}
		});
	}

	public void callSubmitButton() {

		this.lastEmail = null;
		this.lastPassword = null;

		String username = this.registerVue.getUsernameTextField().getText();
		String email = this.registerVue.getEmailTextField().getText();
		String password = this.registerVue.getPasswordPasswordField().getText();

		if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
			this.registerVue.getSubmitButton().setDisable(true);
			this.registerVue.getToLoginHyperlink().setDisable(true);

			LOGGER.verbose("Trying to register with '%s', '%s' and '%s'.", username, email, password);

			this.lastEmail = email;
			this.lastPassword = password;

			NetworkInterface.get().getSocketHandler().queue(new UserRegisterPacket(username, email, password));
		}

	}

	@Override
	public void onMount() {
		NetworkInterface.get().getSocketHandler().subscribe(this);
	}

	@Override
	public void onUnmount() {
		NetworkInterface.get().getSocketHandler().unsubscribe(this);
	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {
		this.registerVue.getSubmitButton().setDisable(false);
		this.registerVue.getToLoginHyperlink().setDisable(false);

		if (packet instanceof UserAuthentificationErrorPacket) {
			UserAuthentificationErrorPacket errorPacket = (UserAuthentificationErrorPacket) packet;

			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle(Messages.ALERT_REGISTER_TITLE.use());
				alert.setHeaderText(null);
				alert.setContentText(Messages.ENUM_USER_AUTH_ERROR.use(errorPacket.getErrorType(), errorPacket.getExtra()));

				alert.showAndWait();
			});
		} else if (packet instanceof UserAuthentificationSuccessPacket) {
			handler.setProfile(null);

			UserInterface.get().set(new LoginView(this.lastEmail, this.lastPassword));
		}
	}

}