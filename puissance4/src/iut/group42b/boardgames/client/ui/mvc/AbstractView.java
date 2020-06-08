package iut.group42b.boardgames.client.ui.mvc;

import iut.group42b.boardgames.client.i18n.impl.I18nMessage;
import iut.group42b.boardgames.client.resources.Resource;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

public abstract class AbstractView implements IView {

	/* Variables */
	private Parent root;

	/* Constructor */
	protected AbstractView() {
		try {
			this.root = FXMLLoader.load(Resource.loadForm(getViewPath()), I18nMessage.getGlobalResourceBundle());
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	protected Node findById(String id) {
		return root.lookup("#" + id);
	}

	@Override
	public Parent getRoot() {
		return root;
	}

}