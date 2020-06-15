package iut.group42b.boardgames.client.manager;

import iut.group42b.boardgames.Bootstrap;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.index.IndexView;
import iut.group42b.boardgames.client.ui.page.login.LoginView;
import iut.group42b.boardgames.game.impl.connect4.ui.Connect4UIView;
import iut.group42b.boardgames.util.Logger;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
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
	private Stage currentDialog;

	/* Constructor */
	private UserInterface() {
		;
	}

	public static UserInterface get() {
		return INSTANCE;
	}

	/* Setting up first page  */
	public void initialize(Stage primaryStage) throws Exception {
		this.stage = primaryStage;

		if (Bootstrap.CLIENT_GIVEN_EMAIL_OPTION.isUsed() || Bootstrap.CLIENT_GIVEN_PASSWORD_OPTION.isUsed()) {
			set(new LoginView((String) Bootstrap.CLIENT_GIVEN_EMAIL_OPTION.getValue(), (String) Bootstrap.CLIENT_GIVEN_PASSWORD_OPTION.getValue()));
		} else {
			set(new IndexView());
		}
		//set(new Connect4UIView());
	}

	public void set(IView view) {
		Platform.runLater(() -> {
			if (currentController != null) {
				currentController.onUnmount();
				currentController = null;
			}

			currentView = view;
			currentController = view.createController();

			LOGGER.verbose("Set view to: %s", view.getClass().getCanonicalName());

			if (currentController != null) {
				currentController.onMount();
				currentController.attachView(view);
			}

			if (scene == null) {
				scene = new Scene(view.getRoot());
				stage.setScene(scene);
			} else {
				scene.setRoot(view.getRoot());
			}
		});
	}

	/*
	 * Open a dialog window
	 */
	public void openDialog(IView view) {
		// run dialog in a new thread
		Platform.runLater(() -> {
			IController controller = view.createController();

			LOGGER.verbose("Opening dialog to: %s", view.getClass().getCanonicalName());

			if (controller != null) {
				controller.onMount();
				controller.attachView(view);
			}

			this.currentDialog = new Stage();

			this.currentDialog.setScene(new Scene(view.getRoot()));

			this.currentDialog.initOwner(stage);
			this.currentDialog.initModality(Modality.APPLICATION_MODAL);
			this.currentDialog.showAndWait();

			System.out.println("Closed dialog");
			if (controller != null) {
				controller.onUnmount();
			}
		});
	}

	public Stage getCurrentDialog() {
		return currentDialog;
	}
}