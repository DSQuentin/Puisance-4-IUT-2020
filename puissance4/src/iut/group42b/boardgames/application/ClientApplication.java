package iut.group42b.boardgames.application;

import iut.group42b.boardgames.Bootstrap;
import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.i18n.impl.I18nMessage;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.util.Logger;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.ConnectException;
import java.net.Socket;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientApplication extends Application {

	/* Logger */
	private final static Logger LOGGER = new Logger(ClientApplication.class);

	/**
	 * Start the client application.
	 *
	 * @throws Exception
	 */
	public static void startFromBootstrap() throws Exception {
		LOGGER.verbose("Starting client...");

		/* Loading global resource bundle for i18n. */
		Locale locale = Resource.DEFAULT_LOCALE_ENGLISH;

		if (Bootstrap.FRENCH_LANGUAGE.isUsed()) {
			locale = Resource.DEFAULT_LOCALE_FRENCH;
		}

		ResourceBundle resourceBundle = Resource.loadResourceBundle("strings", locale);
		I18nMessage.setGlobalResourceBundle(resourceBundle);

		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
			Socket socket = new Socket((String) Bootstrap.IP_OPTION.getValue(), Integer.parseInt(Bootstrap.PORT_OPTION.getValue().toString()));
			SocketHandler socketHandler = new SocketHandler(socket);

			socketHandler.newThread();

			NetworkInterface.get().initialize(socketHandler);
			UserInterface.get().initialize(primaryStage);

			primaryStage.show();
		}
		catch (ConnectException e){
			Alert al = new Alert(Alert.AlertType.ERROR);
			al.setTitle(Messages.ALERT_SERVER_ERROR_TITLE.use());
			al.setHeaderText(Messages.ALERT_SERVER_ERROR_HEADER.use());
			al.setContentText(Messages.ALERT_SERVER_ERROR_CONTENT.use());

			Optional<ButtonType> result = al.showAndWait();
			if (result.isPresent() && (result.get() == ButtonType.OK || result.get() == ButtonType.CLOSE)){
				System.exit(0);
			}
		}
	}

}
