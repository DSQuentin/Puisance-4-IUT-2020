package iut.group42b.boardgames.server.manager;

import iut.group42b.boardgames.game.GameRegistry;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.packet.matchmaking.MatchmakingJoinPacket;
import iut.group42b.boardgames.game.packet.matchmaking.MatchmakingLeavePacket;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.Logger;

import java.util.*;

public class MatchmakingManager implements INetworkHandler {

	/* Logger */
	private final static Logger LOGGER = new Logger(DatabaseInterface.class);

	/* Singleton */
	private final static MatchmakingManager INSTANCE = new MatchmakingManager();

	/* Variables */
	private final Map<IGame, List<SocketHandler>> waitingToPlaySocketsMap;

	/**
	 * Constructor MatchmakingManager.
	 */
	private MatchmakingManager() {
		this.waitingToPlaySocketsMap = new HashMap<>();
	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {
		if (packet instanceof MatchmakingJoinPacket) {
			MatchmakingJoinPacket joinPacket = (MatchmakingJoinPacket) packet;

			IGame game = GameRegistry.get().getById(joinPacket.getGameId());
			if (game == null) {
				LOGGER.warning("Unknown game ID: %s", joinPacket.getGameId());
				return;
			}

			List<SocketHandler> readyToPlaySockets = null;

			synchronized (this.waitingToPlaySocketsMap) {
				List<SocketHandler> socketList = this.waitingToPlaySocketsMap.computeIfAbsent(game, (key) -> new LinkedList<>());

				socketList.add(handler);

				if (socketList.size() >= game.getRequiredPlayer()) {
					synchronized (socketList) {
						readyToPlaySockets = new ArrayList<>(socketList.subList(0, game.getRequiredPlayer()));

						socketList.removeAll(readyToPlaySockets);
					}
				}
			}

			if (readyToPlaySockets != null) {
				GameManager.get().start(game, readyToPlaySockets);
			}
		} else if (packet instanceof MatchmakingLeavePacket) {
			synchronized (this.waitingToPlaySocketsMap) {
				this.waitingToPlaySocketsMap.values().forEach((socketList) -> socketList.remove(handler));
			}
		}

	}

	/**
	 * Get the MatchmakingManager.
	 *
	 * @return MatchmakingManager.
	 */
	public static MatchmakingManager get() {
		return INSTANCE;
	}

}