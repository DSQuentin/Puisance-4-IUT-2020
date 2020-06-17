package iut.group42b.boardgames.client.ui.list.gamehistory;

import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.social.model.gamehistory.GameHistoryItem;

public class GameHistoryListCellView extends AbstractViewCell<GameHistoryItem> {


	protected GameHistoryListCellView(IListViewCellController<GameHistoryItem> partController) {
		super(partController);
	}

	@Override
	public String getViewPath() {
		return "history-cell.fxml";
	}
}
