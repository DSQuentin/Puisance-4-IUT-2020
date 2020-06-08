package iut.group42b.boardgames.game.impl.werewolf;

import iut.group42b.boardgames.game.GameAvailability;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.IGameHandler;

import java.util.Arrays;
import java.util.List;

public class WereWolfGame implements IGame {

	@Override
	public IGameHandler createHandler() {
		return null;
	}

	@Override
	public String getName() {
		return "WereWolf";
	}

	@Override
	public List<String> getTags() {
		return Arrays.asList("Free", "Multiplayer", "Strategy");
	}

	@Override
	public GameAvailability getAvailability() {
		return GameAvailability.NOT_PLAYABLE;
	}

}