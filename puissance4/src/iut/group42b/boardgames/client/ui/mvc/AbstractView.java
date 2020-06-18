package iut.group42b.boardgames.client.ui.mvc;

import iut.group42b.boardgames.client.i18n.impl.I18nMessage;
import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.util.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

public abstract class AbstractView implements IView {

	/* Variables */
	private final Logger logger;
	private final Parent root;

	/* Constructor */
	protected AbstractView() {
		this.logger = new Logger(this.getClass());

		try {
			this.root = FXMLLoader.load(Resource.loadForm(this.getViewPath()), I18nMessage.getGlobalResourceBundle());
			//Debug.dump(this.root);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	protected <T extends Node> T findById(String id) {
		Node node = this.root.lookup("#" + id);

		if (node == null) {
			this.getLogger().warning("There is no view with ID: %s", id);
		}

		return (T) node;
	}

	@Override
	public Parent getRoot() {
		return this.root;
	}

	public Logger getLogger() {
		return this.logger;
	}

}