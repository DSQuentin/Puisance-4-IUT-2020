package iut.group42b.boardgames.client.ui.mvc.list;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public interface IListViewCellController<T> extends EventHandler<ActionEvent> {

	void attachView(AbstractViewCell<T> view);

	void updateItem(AbstractViewCell<T> cellView, T item);

	AbstractViewCell<T> createView();

	default Callback<ListView<T>, ListCell<T>> cellFactory() {
		return (listView) -> {
			AbstractViewCell<T> cell = createView();

			attachView(cell);

			return cell;
		};
	}

}