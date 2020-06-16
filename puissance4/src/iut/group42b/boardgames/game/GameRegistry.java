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

	/**
	 * Constructor GameRegistry
	 */
	private GameRegistry() {
		this.gameIdIncrement = new AtomicInteger(0);
		this.gameToIdMap = new HashMap<>();
		this.idToGameMap = new HashMap<>();
		this.games = new HashMap<>();
	}

	/**
	 * Register a new game.
	 *
	 * @param game IGame to register.
	 */
	public void register(IGame game) {
		LOGGER.debug("Registered game '%s' with class: %s", game.getName(), game.getClass().getCanonicalName());

		IGameHandler handler = game.createHandler();
		if (handler != null) {
			handler.registerPackets();
		}

		int gameId = this.gameIdIncrement.getAndIncrement();
		this.gameToIdMap.put(game, gameId);
		this.idToGameMap.put(gameId, game);

		this.games.put(game, handler);
	}

	/**
	 * Get all games.
	 *
	 * @return Collection<IGame>
	 */
	public Collection<IGame> all() {
		return this.games.keySet();
	}

	/**
	 * Get game by ID
	 *
	 * @param gameId ID of game.
	 * @return IGame.
	 */
	public IGame getById(int gameId) {
		return this.idToGameMap.get(gameId);
	}

	/**
	 * Get id of game.
	 *
	 * @param game IGame.
	 * @return Int.
	 */
	public int getIdFor(IGame game) {
		return this.gameToIdMap.get(game);
	}

	/**
	 * Get handler for specific game.
	 *
	 * @param game game handler wanted.
	 * @return IGameHandler.
	 */
	public IGameHandler getHandlerFor(IGame game) {
		return this.games.get(game);
	}

	/**
	 * Get all Game Handlers.
	 *
	 * @return Collection<IGameHandler>.
	 */
	public Collection<IGameHandler> allGameHandlers() {
		return this.games.values();
	}

	/**
	 * Get the GameRegistry.
	 *
	 * @return GameRegistry
	 */
	public static GameRegistry get() {
		return INSTANCE;
	}

}