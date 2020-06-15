package iut.group42b.boardgames.server.manager;

import iut.group42b.boardgames.game.GameRegistry;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.packet.matchmaking.MatchmakingJoinPacket;
import iut.group42b.boardgames.game.packet.matchmaking.MatchmakingLeavePacket;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.Logger;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MatchmakingManager implements INetworkHandler {

	/* Logger */
	private final static Logger LOGGER = new Logger(DatabaseInterface.class);

	/* Singleton */
	private final static MatchmakingManager INSTANCE = new MatchmakingManager();

	/* Variables */
	private final Map<IGame, List<SocketHandler>> waitingToPlaySocketsMap;

	/* Constructor */
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

			synchronized (waitingToPlaySocketsMap) {
				List<SocketHandler> socketList = waitingToPlaySocketsMap.computeIfAbsent(game, (key) -> new LinkedList<>());

				socketList.add(handler);

				if (socketList.size() >= game.getRequiredPlayer()) {
					readyToPlaySockets = socketList.subList(0, game.getRequiredPlayer());
				}
			}

			if (readyToPlaySockets != null) {
				GameManager.get().start(game, readyToPlaySockets);
			}
		} else if (packet instanceof MatchmakingLeavePacket) {
			synchronized (waitingToPlaySocketsMap) {
				waitingToPlaySocketsMap.values().forEach((socketList) -> socketList.remove(handler));
			}
		}

	}

	public static MatchmakingManager get() {
		return INSTANCE;
	}

}