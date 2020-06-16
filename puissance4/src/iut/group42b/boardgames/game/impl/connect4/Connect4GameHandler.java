package iut.group42b.boardgames.game.impl.connect4;

import iut.group42b.boardgames.game.IGameArena;
import iut.group42b.boardgames.game.IGameHandler;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4GameInfoPacket;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4GridUpdatePacket;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4PutTokenPacket;
import iut.group42b.boardgames.game.impl.connect4.packet.IConnect4Packet;
import iut.group42b.boardgames.game.packet.PlayerLoosePacket;
import iut.group42b.boardgames.game.packet.PlayerSurrenderPacket;
import iut.group42b.boardgames.game.packet.PlayerWinPacket;
import iut.group42b.boardgames.game.player.Player;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.PacketRegistry;
import iut.group42b.boardgames.network.packet.impl.connection.ConnectionLostPacket;
import iut.group42b.boardgames.server.manager.DatabaseInterface;
import iut.group42b.boardgames.server.manager.GameManager;
import iut.group42b.boardgames.util.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Connect4GameHandler implements IGameHandler, INetworkHandler {

	/* Logger */
	private final static Logger LOGGER = new Logger(Connect4GameHandler.class);

	/* Variables */
	private final AtomicInteger arenaIdIncrement;
	private final Map<Integer, Connect4GameArena> runningArena;

	/**
	 * Constructor Connect4GameHandler
	 */
	public Connect4GameHandler() {
		this.arenaIdIncrement = new AtomicInteger(0);
		this.runningArena = new HashMap<>();
	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {
		if (packet instanceof ConnectionLostPacket || packet instanceof PlayerSurrenderPacket) {
			Connect4GameArena arena = (Connect4GameArena) GameManager.get().findArena(handler);

			System.out.println(arena);


			if (arena == null) {
				return;
			}

			System.out.println(arena.getState());
			if (arena.isPlaying()) {
				SocketHandler losing = handler;
				SocketHandler winning = arena
						.getPlayers()
						.stream()
						.filter((player) -> player.getSocketHandler() != losing)
						.findFirst()
						.orElseThrow(() -> new IllegalStateException("is there no opponent in this arena?: " + arena.getDatabaseId()))
						.getSocketHandler();
				System.out.println(winning);

				int winMode = PlayerWinPacket.MODE_SURRENDER;

				if (packet instanceof ConnectionLostPacket) {
					winMode = PlayerWinPacket.MODE_CONNECTION_LOST;
				} else {
					losing.queue(new PlayerLoosePacket());
				}
				System.out.println(winMode);

				winning.queue(new PlayerWinPacket(winMode));

				arena.setState(Connect4GameArena.State.SURRENDER);
				this.stopArena(arena);
			}
		} else if (packet instanceof IConnect4Packet) {
			Connect4GameArena arena = (Connect4GameArena) GameManager.get().findArena(handler);
			if (arena == null) {
				LOGGER.warning("Packet received, but no arena found.");
				return;
			}

			if (!arena.isPlaying()) {
				LOGGER.warning("Packet received, but arena is not playing.");
				return;
			}

			if (packet instanceof Connect4PutTokenPacket) {
				synchronized (arena) {
					Connect4PutTokenPacket putTokenPacket = (Connect4PutTokenPacket) packet;

					Connect4Side side = putTokenPacket.getSide();
					if (arena.getSideToPlay() != side) {
						return;
					}

					arena.putTokenAt(arena.getSideToPlay(), putTokenPacket.getX(), putTokenPacket.getY());
					this.storeArena(arena);

					boolean oneHasWin = false;
					if (arena.checkWin(Connect4Side.RED)) {
						oneHasWin = true;

						arena.getRedPlayer().getSocketHandler().queue(new PlayerWinPacket());
						arena.getYellowPlayer().getSocketHandler().queue(new PlayerLoosePacket());
					} else if (arena.checkWin(Connect4Side.YELLOW)) {
						oneHasWin = true;

						arena.getRedPlayer().getSocketHandler().queue(new PlayerLoosePacket());
						arena.getYellowPlayer().getSocketHandler().queue(new PlayerWinPacket());
					}

					if (oneHasWin) {
						arena.setState(Connect4GameArena.State.DONE);

						this.stopArena(arena);

						// TODO Send
					} else {
						arena.inverseSide();
					}

					arena.broadcast(new Connect4GridUpdatePacket(arena.getGrid(), arena.getSideToPlay()));
				}
			}
		}
	}

	/**
	 * @param arena
	 * @param redPlayer
	 * @param yellowPlayer
	 */
	public void startArena(Connect4GameArena arena, Player redPlayer, Player yellowPlayer) {
		int arenaId = this.arenaIdIncrement.getAndIncrement();

		this.runningArena.put(arenaId, arena);

		try (PreparedStatement statement = DatabaseInterface.get().getConnection().prepareStatement("INSERT INTO `played_games` (`game_state`, `id_user_1`, `id_user_2`) VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, arena.toJSONGameState());
			statement.setInt(2, redPlayer.getSocketHandler().getUserProfile().getId());
			statement.setInt(3, yellowPlayer.getSocketHandler().getUserProfile().getId());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("failed to insert");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (!generatedKeys.next()) {
					throw new SQLException("no id obtained");
				}

				int id = generatedKeys.getInt(1);

				arena.setDatabaseId(id);
				LOGGER.info("Starting arena with database ID: %s", id);
			}
		} catch (SQLException exception) {
			throw new RuntimeException("failed to start arena (in database)", exception);
		}
	}

	/**
	 * Save arena into database.
	 *
	 * @param arena Connect4GameArena
	 */
	public void storeArena(Connect4GameArena arena) {
		try (PreparedStatement statement = DatabaseInterface.get().getConnection().prepareStatement("UPDATE `played_games` SET `game_state` = ?, `score_1` = ?, `score_2` = ? WHERE `id` = ?;")) {
			statement.setString(1, arena.toJSONGameState());
			statement.setInt(2, arena.getScoreRed());
			statement.setInt(3, arena.getScoreYellow());
			statement.setInt(4, arena.getDatabaseId());

			if (statement.executeUpdate() == 0) {
				throw new SQLException("failed to update arena with ID: " + arena.getDatabaseId());
			}
		} catch (SQLException exception) {
			throw new RuntimeException("failed to store arena (in database)", exception);
		}
	}

	/**
	 * Stop Arena in database.
	 *
	 * @param arena Connect4GameArena.
	 */
	public void stopArena(Connect4GameArena arena) {
		if (Connect4GameArena.State.PLAYING.equals(arena.getState())) {
			throw new IllegalStateException("stopping arena that have a playing state");
		}

		try {
			arena.end(this);
			this.storeArena(arena);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		try {
			GameManager.get().stop(arena);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		try (PreparedStatement statement = DatabaseInterface.get().getConnection().prepareStatement("UPDATE `played_games` SET `end_at` = NOW(), `state_number` = ? WHERE `id` = ?;")) {
			statement.setInt(1, arena.getState().getStateCode());
			statement.setInt(2, arena.getDatabaseId());

			if (statement.executeUpdate() == 0) {
				throw new SQLException("failed to update arena with ID: " + arena.getDatabaseId());
			}
		} catch (SQLException exception) {
			throw new RuntimeException("failed to stop arena (in database)", exception);
		}
	}

	@Override
	public void registerPackets() {
		PacketRegistry.get().register(Connect4GameInfoPacket.class);
		PacketRegistry.get().register(Connect4PutTokenPacket.class);
		PacketRegistry.get().register(Connect4GridUpdatePacket.class);
	}

	@Override
	public IGameArena createArena(List<Player> players) {
		Player redPlayer = players.get(0);
		Player yellowPlayer = players.get(1);

		return new Connect4GameArena(redPlayer, yellowPlayer);
	}

}