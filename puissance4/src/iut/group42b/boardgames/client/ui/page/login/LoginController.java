package iut.group42b.boardgames.client.ui.page.login;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.register.RegisterView;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserAuthentificationErrorPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserAuthentificationSuccessPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserLoginPacket;
import iut.group42b.boardgames.util.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class LoginController implements IController, INetworkHandler {

	/* Logger */
	private final static Logger LOGGER = new Logger(LoginController.class);

	/* Variables */
	private LoginView view;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == view.getSubmitButton()) {
			String email = view.getEmailTextField().getText();
			String password = view.getPasswordField().getText();

			if (!email.isEmpty() && !password.isEmpty()) {
				view.getSubmitButton().setDisable(true);
				view.getRegisterHyperlink().setDisable(true);

				LOGGER.verbose("Trying to connect with '%s' and '%s'.", email, password);

				NetworkInterface.get().getSocketHandler().queue(new UserLoginPacket(email, password));
			}
		} else if (event.getSource() == view.getRegisterHyperlink()) {
			UserInterface.get().set(new RegisterView());
		}
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof LoginView)) {
			throw new IllegalArgumentException();
		}

		this.view = (LoginView) view;

		this.view.getSubmitButton().setOnAction(this);
		this.view.getRegisterHyperlink().setOnAction(this);

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
		view.getSubmitButton().setDisable(false);
		view.getRegisterHyperlink().setDisable(false);

		if (packet instanceof UserAuthentificationErrorPacket) {
			UserAuthentificationErrorPacket errorPacket = (UserAuthentificationErrorPacket) packet;

			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle(Messages.ALERT_LOGIN_TITLE.use());
				alert.setHeaderText(null);
				alert.setContentText(Messages.ENUM_USER_AUTH_ERROR.use(errorPacket.getErrorType(), errorPacket.getExtra()));

				alert.showAndWait();
			});
		} else if (packet instanceof UserAuthentificationSuccessPacket) {
			UserInterface.get().set(new HomeView());
		}
	}

}