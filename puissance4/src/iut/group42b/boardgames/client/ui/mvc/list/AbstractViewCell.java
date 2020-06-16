package iut.group42b.boardgames.client.ui.mvc.list;

import iut.group42b.boardgames.client.i18n.impl.I18nMessage;
import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.util.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

public abstract class AbstractViewCell<T> extends ListCell<T> {

	/* Variables */
	private final Logger logger;
	private final Parent root;
	private final IListViewCellController<T> partController;
	private T currentItem;

	/* Constructor */
	protected AbstractViewCell(IListViewCellController<T> partController) {
		this.logger = new Logger(this.getClass());
		this.partController = partController;

		try {
			this.root = FXMLLoader.load(Resource.loadForm(this.getViewPath()), I18nMessage.getGlobalResourceBundle());
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	protected Node findById(String id) {
		Node node = this.root.lookup("#" + id);

		if (node == null) {
			this.getLogger().warning("There is no view with ID: %s", id);
		}

		return node;
	}

	@Override
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);

		this.setUserData(null);
		this.setText(null);

		this.currentItem = item;

		if (empty || item == null) {
			this.setGraphic(null);
		} else {
			this.setUserData(item);
			this.partController.updateItem(this, item);
			this.setGraphic(this.root);
		}
	}

	public Parent getRoot() {
		return this.root;
	}

	public Logger getLogger() {
		return this.logger;
	}

	public T getCurrentItem() {
		return this.currentItem;
	}

	public abstract String getViewPath();

}