package iut.group42b.boardgames.game.impl.connect4;

import iut.group42b.boardgames.game.GameAvailability;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.IGameHandler;

import java.util.Arrays;
import java.util.List;

public class Connect4Game implements IGame {

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

}