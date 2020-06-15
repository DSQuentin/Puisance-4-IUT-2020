package iut.group42b.boardgames.game;

import iut.group42b.boardgames.util.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GameRegistry {

	/* Logger */
	private final static Logger LOGGER = new Logger(GameRegistry.class);

	/* Singleton */
	private static final GameRegistry INSTANCE = new GameRegistry();

	/* Variables */
	private final AtomicInteger gameIdIncrement;
	private final Map<IGame, Integer> gameToIdMap;
	private final Map<Integer, IGame> idToGameMap;
	private final Map<IGame, IGameHandler> games;

	/* Constructor */
	private GameRegistry() {
		this.gameIdIncrement = new AtomicInteger(0);
		this.gameToIdMap = new HashMap<>();
		this.idToGameMap = new HashMap<>();
		this.games = new HashMap<>();
	}

	public void register(IGame game) {
		LOGGER.debug("Registered game '%s' with class: %s", game.getName(), game.getClass().getCanonicalName());

		IGameHandler handler = game.createHandler();
		if (handler != null) {
			handler.registerPackets();
		}

		int gameId = gameIdIncrement.getAndIncrement();
		gameToIdMap.put(game, gameId);
		idToGameMap.put(gameId, game);

		this.games.put(game, handler);
	}

	public Collection<IGame> all() {
		return games.keySet();
	}

	public IGame getById(int gameId) {
		return idToGameMap.get(gameId);
	}

	public int getIdFor(IGame game) {
		return gameToIdMap.get(game);
	}

	public IGameHandler getHandlerFor(IGame game) {
		return games.get(game);
	}

	public Collection<IGameHandler> allGameHandlers() {
		return games.values();
	}

	public static GameRegistry get() {
		return INSTANCE;
	}

}