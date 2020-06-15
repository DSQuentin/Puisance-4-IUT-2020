package iut.group42b.boardgames.game.impl.chess;

import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.game.GameAvailability;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.IGameHandler;

import java.util.Arrays;
import java.util.List;

public class ChessGame implements IGame {

	@Override
	public IGameHandler createHandler() {
		return null;
	}

	@Override
	public String getName() {
		return "Chess";
	}

	@Override
	public List<String> getTags() {
		return Arrays.asList("Free", "Duel", "Strategy");
	}

	@Override
	public GameAvailability getAvailability() {
		return GameAvailability.NOT_PLAYABLE;
	}

	@Override
	public String picturePath() {
		return "chess.png";
	}

	@Override
	public int getRequiredPlayer() {
		return 2;
	}

	@Override
	public IView createClientView() {
		return null;
	}

}