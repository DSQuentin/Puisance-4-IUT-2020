package iut.group42b.boardgames.client.ui.list.gamehistory;

import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.social.model.gamehistory.GameHistoryItem;
import javafx.event.ActionEvent;

public class GameHistoryListCellController implements IListViewCellController<GameHistoryItem> {


	@Override
	public void attachView(AbstractViewCell<GameHistoryItem> view) {

	}

	@Override
	public void updateItem(AbstractViewCell<GameHistoryItem> cellView, GameHistoryItem item) {

	}

	@Override
	public AbstractViewCell<GameHistoryItem> createView() {
		return null;
	}

	@Override
	public void handle(ActionEvent event) {

	}
}
