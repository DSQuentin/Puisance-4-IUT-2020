package iut.group42b.boardgames.game.impl.connect4;

import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.game.GameAvailability;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.IGameHandler;
import iut.group42b.boardgames.game.impl.connect4.ui.Connect4UIView;

import java.util.Arrays;
import java.util.List;

public class Connect4Game implements IGame {

	/* Constants */
	public static final int NUMBER_OF_ROWS = 6;
	public static final int NUMBER_OF_COLUMNS = 7;

	@Override
	public IGameHandler createHandler() {
		return new Connect4GameHandler();
	}

	@Override
	public String getName() {
		return "Connect 4";
	}

	@Override
	public List<String> getTags() {
		return Arrays.asList("Free", "Duel", "Strategy");
	}

	@Override
	public GameAvailability getAvailability() {
		return GameAvailability.PLAYABLE;
	}

	@Override
	/* public String picturePath() {
		return "connect4cover.png";
	}*/
	public String picturePath() {
		//return "connect4-waiting-alertbox.png";
		return "connect4cover.png";
	}

	@Override
	public int getRequiredPlayer() {
		return 2;
	}

	@Override
	public IView createClientView() {
		return new Connect4UIView();
	}

}