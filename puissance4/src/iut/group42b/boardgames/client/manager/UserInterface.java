package iut.group42b.boardgames.client.manager;

import iut.group42b.boardgames.Bootstrap;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.index.IndexView;
import iut.group42b.boardgames.client.ui.page.login.LoginView;
import iut.group42b.boardgames.util.Logger;
import javafx.application.Platform;
import javafx.scene.Scene;
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

	/**
	 * Constructor UserInterface
	 */
	private UserInterface() {
	}

	/**
	 * Get the UserInterface.
	 *
	 * @return UserInterface.
	 */
	public static UserInterface get() {
		return INSTANCE;
	}


	/**
	 * Setting up first page
	 *
	 * @param primaryStage
	 * @throws Exception
	 */
	public void initialize(Stage primaryStage) throws Exception {
		this.stage = primaryStage;

		if (Bootstrap.CLIENT_GIVEN_EMAIL_OPTION.isUsed() || Bootstrap.CLIENT_GIVEN_PASSWORD_OPTION.isUsed()) {
			this.set(new LoginView((String) Bootstrap.CLIENT_GIVEN_EMAIL_OPTION.getValue(), (String) Bootstrap.CLIENT_GIVEN_PASSWORD_OPTION.getValue()));
		} else {
			this.set(new IndexView());
		}
		//set(new Connect4UIView());
	}

	/**
	 * Set view to an other.
	 *
	 * @param view View to switch
	 */
	public void set(IView view) {
		Platform.runLater(() -> {
			if (this.currentController != null) {
				this.currentController.onUnmount();
				this.currentController = null;
			}

			this.currentView = view;
			this.currentController = view.createController();

			LOGGER.verbose("Set view to: %s", view.getClass().getCanonicalName());

			if (this.currentController != null) {
				this.currentController.onMount();
				this.currentController.attachView(view);
			}

			if (this.scene == null) {
				this.scene = new Scene(view.getRoot());
				this.stage.setScene(this.scene);
			} else {
				this.scene.setRoot(view.getRoot());
			}
		});
	}

	/**
	 * Open a dialog window
	 *
	 * @param view View of Dialog
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

			this.currentDialog.initOwner(this.stage);
			this.currentDialog.initModality(Modality.APPLICATION_MODAL);
			this.currentDialog.showAndWait();

			if (controller != null) {
				controller.onUnmount();
			}

			this.currentDialog = null;
		});


	}

	/**
	 * Close current Dialog.
	 *
	 * @see <a href="https://stackoverflow.com/questions/28698106/why-am-i-unable-to-programmatically-close-a-dialog-on-javafx">How to close dialog</a>
	 */
	public void closeCurrentDialog() {
		if (this.currentDialog != null) {
			this.currentDialog.close();
		}
	}

	/**
	 * Get current Opened dialog.
	 *
	 * @return CurrentDialog.
	 */
	public Stage getCurrentDialog() {
		return this.currentDialog;
	}
}