package iut.group42b.boardgames.client.manager;

import iut.group42b.boardgames.Bootstrap;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.index.IndexView;
import iut.group42b.boardgames.client.ui.page.login.LoginView;
import iut.group42b.boardgames.client.ui.page.user.settings.UserSettingsView;
import iut.group42b.boardgames.util.Logger;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UserInterface {

	/* Logger */
	private final static Logger LOGGER = new Logger(UserInterface.class);

	/* Singleton */
	private final static UserInterface INSTANCE = new UserInterface();

	/* Variables */
	private Stage stage;
	private Scene scene;
	private IView currentView;
	private IController currentController;

	/* Constructor */
	private UserInterface() {
		;
	}

	public static UserInterface get() {
		return INSTANCE;
	}

	public void initialize(Stage primaryStage) throws Exception {
		this.stage = primaryStage;

		if (Bootstrap.CLIENT_GIVEN_EMAIL_OPTION.isUsed() || Bootstrap.CLIENT_GIVEN_PASSWORD_OPTION.isUsed()) {
			set(new LoginView((String) Bootstrap.CLIENT_GIVEN_EMAIL_OPTION.getValue(), (String) Bootstrap.CLIENT_GIVEN_PASSWORD_OPTION.getValue()));
		} else {
			set(new IndexView());
		}
		// set(new UserSettingsView());
	}

	public void set(IView view) {
		Platform.runLater(() -> {
			if (currentController != null) {
				currentController.onUnmount();
				currentController = null;
			}

			currentView = view;
			currentController = view.createController();

			LOGGER.verbose("Set vue to: %s", view.getClass().getCanonicalName());

			if (currentController != null) {
				currentController.onMount();
				currentController.attachVue(view);
			}

			if (scene == null) {
				scene = new Scene(view.getRoot());
				stage.setScene(scene);
			} else {
				scene.setRoot(view.getRoot());
			}
		});
	}

}