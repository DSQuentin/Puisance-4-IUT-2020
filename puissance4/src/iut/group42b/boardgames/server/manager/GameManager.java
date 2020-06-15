package iut.group42b.boardgames.server.manager;

import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.game.GameRegistry;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.IGameArena;
import iut.group42b.boardgames.game.IGameHandler;
import iut.group42b.boardgames.game.packet.PlayerJoinPacket;
import iut.group42b.boardgames.game.packet.PlayerSurrenderPacket;
import iut.group42b.boardgames.game.player.Player;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.Logger;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameManager implements INetworkHandler {

	/* Logger */
	private final static Logger LOGGER = new Logger(DatabaseInterface.class);

	/* Singleton */
	private final static GameManager INSTANCE = new GameManager();

	/* Variables */
	private final Map<Player, IGameArena> playerToArenaMap;

	/* Constructor */
	private GameManager() {
		this.playerToArenaMap = new HashMap<>();
	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {
		for (IGameHandler gameHandler : GameRegistry.get().allGameHandlers()) {
			if (gameHandler instanceof INetworkHandler) {
				((INetworkHandler) gameHandler).handlePacket(handler, packet);
			}
		}
	}

	public IGameArena findArena(SocketHandler socketHandler) {
		for (Map.Entry<Player, IGameArena> entry : playerToArenaMap.entrySet()) {
			Player player = entry.getKey();
			IGameArena arena = entry.getValue();

			if (player.getSocketHandler().equals(socketHandler)) {
				return arena;
			}
		}

		return null;
	}

	public void start(IGame game, List<SocketHandler> readyToPlaySockets) {
		List<Player> players = readyToPlaySockets.stream().map(Player::new).collect(Collectors.toList());

		IGameHandler gameHandler = GameRegistry.get().getHandlerFor(game);
		IGameArena arena = gameHandler.createArena(players);

		for (Player player : players) {
			playerToArenaMap.put(player, arena);
		}

		arena.broadcast(new PlayerJoinPacket(GameRegistry.get().getIdFor(game)));

		// waiting 5 seconds opponent
		// I try with 1sec but it was too quick if the second play have some ping
		new Thread(() -> {
			try {
				Thread.sleep(5000); // TODO Use more packet to sync the two clients
			} catch (Exception exception) { }

			arena.start(gameHandler);
		}).start();

	}

	public static GameManager get() {
		return INSTANCE;
	}

}