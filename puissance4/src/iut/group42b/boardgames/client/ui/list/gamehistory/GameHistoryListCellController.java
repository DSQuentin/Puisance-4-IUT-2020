package iut.group42b.boardgames.client.ui.list.gamehistory;

import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.client.ui.page.profile.own.OwnController;
import iut.group42b.boardgames.social.model.gamehistory.GameHistoryItem;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;

public class GameHistoryListCellController implements IListViewCellController<GameHistoryItem> {


	/* Variables */
	private GameHistoryListCellView view;
	private final OwnController controller;

	public GameHistoryListCellController(OwnController controller) {
		this.controller = controller;
	}

	@Override
	public void attachView(AbstractViewCell<GameHistoryItem> view) {


	}

	@Override
	public void updateItem(AbstractViewCell<GameHistoryItem> cellView, GameHistoryItem item) {
		GameHistoryListCellView view = (GameHistoryListCellView) cellView;

		view.getUser1().setText("");
		view.getUser2().setText(String.format("%s vs. %s", item.getUser1Name(), item.getUser2Name()));
		view.getDate().setText(item.getStartedAt());
		view.getDuration().setText(item.getDuration());
		view.getScore().setText(String.valueOf(item.getWinnerScore()));
		view.getTimeUnit().setText("");


		if (item.getIdUserWinner() == this.controller.getUserProfile().getId()) {
			view.getWonState().setStroke(Color.GREEN);
		} else {
			view.getWonState().setStroke(Color.RED);
		}


	}

	@Override
	public AbstractViewCell<GameHistoryItem> createView() {
		return new GameHistoryListCellView(this);
	}

	@Override
	public void handle(ActionEvent event) {

	}
}
