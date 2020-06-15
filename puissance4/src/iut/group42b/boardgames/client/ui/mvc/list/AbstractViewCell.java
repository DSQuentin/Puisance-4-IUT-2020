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
		this.logger = new Logger(getClass());
		this.partController = partController;

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
	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);

		setUserData(null);
		setText(null);

		currentItem = item;

		if (empty || item == null) {
			setGraphic(null);
		} else {
			setUserData(item);
			partController.updateItem(this, item);
			setGraphic(root);
		}
	}

	public Parent getRoot() {
		return root;
	}

	public Logger getLogger() {
		return logger;
	}

	public T getCurrentItem() {
		return currentItem;
	}

	public abstract String getViewPath();

}