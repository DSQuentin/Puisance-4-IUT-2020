package iut.group42b.boardgames.game;

import iut.group42b.boardgames.util.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameRegistry {

	/* Logger */
	private final static Logger LOGGER = new Logger(GameRegistry.class);

	/* Singleton */
	private static final GameRegistry INSTANCE = new GameRegistry();

	/* Variables */
	private final Map<IGame, IGameHandler> games;

	/* Constructor */
	private GameRegistry() {
		this.games = new HashMap<>();
	}

	public void register(IGame game) {
		LOGGER.debug("Registered game '%s' with class: %s", game.getName(), game.getClass().getCanonicalName());

		IGameHandler handler = game.createHandler();
		if (handler != null) {
			handler.registerPackets();
		}

		this.games.put(game, handler);
	}

	public static GameRegistry get() {
		return INSTANCE;
	}

	public Collection<IGame> all() {
		return games.keySet();
	}

}