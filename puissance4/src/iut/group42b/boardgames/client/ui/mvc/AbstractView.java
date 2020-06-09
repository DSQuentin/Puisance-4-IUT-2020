package iut.group42b.boardgames.client.ui.mvc;

import iut.group42b.boardgames.client.i18n.impl.I18nMessage;
import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.util.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

public abstract class AbstractView implements IView {

	/* Variables */
	private Logger logger;
	private Parent root;

	/* Constructor */
	protected AbstractView() {
		this.logger = new Logger(getClass());

		try {
			this.root = FXMLLoader.load(Resource.loadForm(getViewPath()), I18nMessage.getGlobalResourceBundle());
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	protected Node findById(String id) {
		Node node = root.lookup("#" + id);

		if (node == null) {
			getLogger().warning("There is no view with ID: %s", id);
		}

		return node;
	}

	@Override
	public Parent getRoot() {
		return root;
	}

	public Logger getLogger() {
		return logger;
	}

}