package iut.group42b.boardgames.game.impl.chess;

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

}