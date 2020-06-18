package iut.group42b.boardgames.client.ui.list.gamehistory;

import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.social.model.gamehistory.GameHistoryItem;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class GameHistoryListCellView extends AbstractViewCell<GameHistoryItem> {

	/* UI */
	private final Circle wonState;
	private final Text user1;
	private final Text game;
	private final Text user2;
	private final Text duration;
	private final Text timeUnit;
	private final Text score;
	private final Text date;


	protected GameHistoryListCellView(IListViewCellController<GameHistoryItem> partController) {
		super(partController);
		this.wonState = (Circle) this.findById("historycell-circle-wonstate");
		this.user1 = (Text) this.findById("historycell-text-current-username");
		this.game = (Text) this.findById("historycell-text-game");
		this.user2 = (Text) this.findById("historycell-text-enemy");
		this.duration = (Text) this.findById("historycell-text-time");
		this.timeUnit = (Text) this.findById("historycell-text-time-unit");
		this.score = (Text) this.findById("historycell-text-point");
		this.date = (Text) this.findById("historycell-text-date");

	}

	@Override
	public String getViewPath() {
		return "history-cell.fxml";
	}

	public Circle getWonState() {
		return this.wonState;
	}

	public Text getUser1() {
		return this.user1;
	}

	public Text getGame() {
		return this.game;
	}

	public Text getUser2() {
		return this.user2;
	}

	public Text getDuration() {
		return this.duration;
	}

	public Text getTimeUnit() {
		return this.timeUnit;
	}

	public Text getScore() {
		return this.score;
	}

	public Text getDate() {
		return this.date;
	}
}
