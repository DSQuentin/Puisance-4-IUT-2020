package iut.group42b.boardgames.application;

import iut.group42b.boardgames.Bootstrap;
import iut.group42b.boardgames.client.i18n.impl.I18nMessage;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.util.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.Socket;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientApplication extends Application {

	/* Logger */
	private final static Logger LOGGER = new Logger(ClientApplication.class);

	public static void startFromBootstrap() throws Exception {
		LOGGER.verbose("Starting client...");

		/* Loading global resource bundle for i18n. */
		Locale locale = Resource.DEFAULT_LOCALE_ENGLISH;

		if (Bootstrap.FRENCH_LANGUAGE.isUsed()) {
			locale = Resource.DEFAULT_LOCALE_FRENCH;
		}

		ResourceBundle resourceBundle = Resource.loadResourceBundle("strings", locale);
		I18nMessage.setGlobalResourceBundle(resourceBundle);

		launch(new String[0]);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Socket socket = new Socket((String) Bootstrap.IP_OPTION.getValue(), (int) Bootstrap.PORT_OPTION.getValue());
		SocketHandler socketHandler = new SocketHandler(socket);

		socketHandler.newThread();

		NetworkInterface.get().initialize(socketHandler);
		UserInterface.get().initialize(primaryStage);

		primaryStage.show();
	}

}
