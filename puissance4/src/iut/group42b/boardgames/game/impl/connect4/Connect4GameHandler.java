package iut.group42b.boardgames.game.impl.connect4;

import iut.group42b.boardgames.game.IGameHandler;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4BasePacket;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4GridUpdatePacket;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4PutTokenPacket;
import iut.group42b.boardgames.game.packet.PlayerJoinPacket;
import iut.group42b.boardgames.game.player.Player;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.PacketRegistry;
import iut.group42b.boardgames.util.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Connect4GameHandler implements IGameHandler, INetworkHandler {

	/* Logger */
	private final static Logger LOGGER = new Logger(Connect4GameHandler.class);

	/* Variables */
	private final AtomicInteger arenaIdIncrement;
	private final Map<Integer, Connect4GameArena> runningArena;

	/* Constructor */
	public Connect4GameHandler() {
		this.arenaIdIncrement = new AtomicInteger(0);
		this.runningArena = new HashMap<>();
	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {
		if (packet instanceof Connect4BasePacket) {
			Connect4BasePacket basePacket = (Connect4BasePacket) packet;

			Connect4GameArena arena = runningArena.getOrDefault(basePacket.getArenaIdentifier(), null);
			if (arena == null) {
				return;
			}

			if (basePacket instanceof Connect4PutTokenPacket) {
				Connect4PutTokenPacket putTokenPacket = (Connect4PutTokenPacket) basePacket;

				Connect4Side side = putTokenPacket.getSide();
				if (arena.getSideToPlay() != side) {
					return;
				}

				arena.putTokenAt(side, putTokenPacket.getX(), putTokenPacket.getY());

				boolean oneHasWin = false;
				if (arena.checkWin(Connect4Side.RED)) {
					oneHasWin = true;

					// TODO Send win packet as RED
				} else if (arena.checkWin(Connect4Side.YELLOW)) {
					oneHasWin = true;

					// TODO Send win packet as YELLOW
				}

				if (oneHasWin) {
					stopArena(arena);

					// TODO Send
				} else {
					arena.inverseSide();
					// TODO Send grid update
				}
			}
		}

		if (packet instanceof PlayerJoinPacket) {
			PlayerJoinPacket joinPacket = (PlayerJoinPacket) packet;

			LOGGER.verbose("Player with id %s join a power 4 game with id %s!", joinPacket.getPlayerId(), joinPacket.getGameId());
		}
	}

	public void stopArena(Connect4GameArena arena) {
		arena.end();

		// TODO Send arena stop

		storeArena(arena);
	}

	public void startArena(Player redPlayer, Player yellowPlayer) {
		int arenaId = arenaIdIncrement.getAndIncrement();
		Connect4GameArena arena = new Connect4GameArena(redPlayer, yellowPlayer);

		runningArena.put(arenaId, arena);

		arena.start();

		// TODO Send arena start
	}

	public void storeArena(Connect4GameArena arena) {
		// stockage dans base de données

		// INSERT INTO x(
	}

	@Override
	public void registerPackets() {
		PacketRegistry.get().register(Connect4PutTokenPacket.class);
		PacketRegistry.get().register(Connect4GridUpdatePacket.class);
	}

}