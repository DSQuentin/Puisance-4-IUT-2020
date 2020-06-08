package iut.group42b.boardgames.client.ui.page.register;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserAuthentificationErrorPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserAuthentificationSuccessPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserRegisterPacket;
import iut.group42b.boardgames.client.ui.page.login.LoginView;
import iut.group42b.boardgames.util.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class RegisterController implements IController, INetworkHandler {

	/* Logger */
	private final static Logger LOGGER = new Logger(RegisterController.class);

	/* Variables */
	private String lastEmail, lastPassword;
	private RegisterView registerVue;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == registerVue.getSubmitButton()) {
			lastEmail = null;
			lastPassword = null;

			String username = registerVue.getUsernameTextField().getText();
			String email = registerVue.getEmailTextField().getText();
			String password = registerVue.getPasswordPasswordField().getText();

			if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
				registerVue.getSubmitButton().setDisable(true);
				registerVue.getToLoginHyperlink().setDisable(true);

				LOGGER.verbose("Trying to register with '%s', '%s' and '%s'.", username, email, password);

				lastEmail = email;
				lastPassword = password;

				NetworkInterface.get().getSocketHandler().queue(new UserRegisterPacket(username, email, password));
			}
		} else if (event.getSource() == registerVue.getToLoginHyperlink()) {
			try {
				UserInterface.get().set(new LoginView());
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	@Override
	public void attachVue(IView vue) {
		if (!(vue instanceof RegisterView)) {
			throw new IllegalArgumentException();
		}

		this.registerVue = (RegisterView) vue;

		registerVue.getSubmitButton().setOnAction(this);
		registerVue.getToLoginHyperlink().setOnAction(this);
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
		registerVue.getSubmitButton().setDisable(false);
		registerVue.getToLoginHyperlink().setDisable(false);

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
			UserInterface.get().set(new LoginView(lastEmail, lastPassword));
		}
	}

}