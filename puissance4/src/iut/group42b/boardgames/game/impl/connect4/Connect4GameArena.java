package iut.group42b.boardgames.game.impl.connect4;

import iut.group42b.boardgames.game.IGameArena;
import iut.group42b.boardgames.game.IGameHandler;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4GameInfoPacket;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4GridUpdatePacket;
import iut.group42b.boardgames.game.player.Player;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.server.manager.DatabaseInterface;
import iut.group42b.boardgames.social.model.UserProfile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ThreadLocalRandom;

public class Connect4GameArena implements IGameArena {

	/* Constants */
	public static final int GRID_WIDTH = 7;
	public static final int GRID_HEIGHT = 6;
	/* Variables */
	public int scoreRed, scoreYellow;
	private long startedAt, endAt;
	private State state;
	private Player redPlayer;
	private Player yellowPlayer;
	private Connect4Side sideToPlay;
	private Connect4Side[][] grid;
	private int databaseId;

	/* Constructor */
	public Connect4GameArena(Player redPlayer, Player yellowPlayer) {
		this.redPlayer = redPlayer;
		this.yellowPlayer = yellowPlayer;

		this.sideToPlay = ThreadLocalRandom.current().nextBoolean() ? Connect4Side.YELLOW : Connect4Side.RED;
		this.grid = new Connect4Side[GRID_HEIGHT][GRID_WIDTH];

		for (int y = 0; y < GRID_HEIGHT; y++) {
			for (int x = 0; x < GRID_WIDTH; x++) {
				grid[y][x] = Connect4Side.NONE;
			}
		}
	}

	public Connect4GameArena putTokenAt(Connect4Side side, int x, int y) {
		if (side != Connect4Side.NONE && grid[y][x] != Connect4Side.NONE) {
			return this;
		}

		grid[y][x] = side;

		if (side == Connect4Side.RED) {
			scoreRed++;
		} else {
			scoreYellow++;
		}

		return this;
	}

	public boolean checkWin(Connect4Side side) {
		/* Horizontal check. */
		for (int j = 0; j < GRID_WIDTH - 3; j++) {
			for (int i = 0; i < GRID_HEIGHT; i++) {
				if (grid[i][j] == side && grid[i][j + 1] == side && grid[i][j + 2] == side && grid[i][j + 3] == side) {
					return true;
				}
			}
		}

		/* Vertical check. */
		for (int i = 0; i < GRID_HEIGHT- 3; i++) {
			for (int j = 0; j <  GRID_WIDTH; j++) {
				if (grid[i][j] == side && grid[i + 1][j] == side && grid[i + 2][j] == side && grid[i + 3][j] == side) {
					return true;
				}
			}
		}

		/* Ascending diagonal check. */
		for (int i = 3; i < GRID_HEIGHT; i++) {
			for (int j = 0; j < GRID_WIDTH - 3; j++) {
				if (grid[i][j] == side && grid[i - 1][j + 1] == side && grid[i - 2][j + 2] == side && grid[i - 3][j + 3] == side) {
					return true;
				}
			}
		}

		/* Descending diagonal check. */
		for (int i = 3; i < GRID_HEIGHT; i++) {
			for (int j = 3; j < GRID_WIDTH; j++) {
				if (grid[i][j] == side && grid[i - 1][j - 1] == side && grid[i - 2][j - 2] == side && grid[i - 3][j - 3] == side) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void start(IGameHandler gameHandler) {
		((Connect4GameHandler) gameHandler).startArena(this, redPlayer, yellowPlayer);

		yellowPlayer.getSocketHandler().queue(new Connect4GameInfoPacket(Connect4Side.YELLOW, redPlayer.getSocketHandler().getUserProfile()));
		redPlayer.getSocketHandler().queue(new Connect4GameInfoPacket(Connect4Side.RED, yellowPlayer.getSocketHandler().getUserProfile()));

		broadcast(new Connect4GridUpdatePacket(grid, sideToPlay));

		startedAt = System.currentTimeMillis();
	}

	@Override
	public void end(IGameHandler gameHandler) {
		endAt = System.currentTimeMillis();
	}

	public long duration() {
		if (state == State.PLAYING) {
			throw new IllegalStateException("Game is still playing");
		}

		return endAt - startedAt;
	}

	public Connect4Side getSideToPlay() {
		return sideToPlay;
	}

	public Connect4GameArena inverseSide() {
		if (sideToPlay == Connect4Side.RED) {
			sideToPlay = Connect4Side.YELLOW;
		} else {
			sideToPlay = Connect4Side.RED;
		}

		return this;
	}

	public Connect4Side[][] getGrid() {
		return grid;
	}

	public Player getYellowPlayer() {
		return yellowPlayer;
	}

	public Player getRedPlayer() {
		return redPlayer;
	}

	public void broadcast(IPacket packet) {
		yellowPlayer.getSocketHandler().queue(packet);
		redPlayer.getSocketHandler().queue(packet);
	}

	@Override
	public void setDatabaseId(int databaseId) {
		this.databaseId = databaseId;
	}

	@Override
	public int getDatabaseId() {
		return databaseId;
	}

	@Override
	public String toJSONGameState() {
		StringBuilder builder = new StringBuilder();

		builder.append("{\n");
		builder.append("\t\"side_to_play\": \"" + sideToPlay.toString() + "\",\n");
		builder.append("\t\"grid\": [\n");

		for (int y = 0; y < Connect4Game.NUMBER_OF_ROWS; y++) {
			builder.append("\t\t\"");

			for (int x = 0; x < Connect4Game.NUMBER_OF_COLUMNS; x++) {
				builder.append(grid[y][x].getLetter());
			}

			builder.append("\"");
			if (y != Connect4Game.NUMBER_OF_ROWS - 1) {
				builder.append(",");
			}
			builder.append("\n");
		}

		builder.append("\t]\n");
		builder.append("}");


		return builder.toString();
	}

	public int getScoreRed() {
		return scoreRed;
	}

	public int getScoreYellow() {
		return scoreYellow;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public static void main(String[] args) {
		System.out.println(new Connect4GameArena(null, null)
				.putTokenAt(Connect4Side.RED, 3, 3)
				.putTokenAt(Connect4Side.YELLOW, 4, 3)
				.inverseSide()
				.toJSONGameState());
	}

	public boolean isPlaying() {
		return state == State.PLAYING;
	}

	public boolean isDone() {
		return state == State.DONE;
	}

	public boolean isSurrender() {
		return state == State.SURRENDER;
	}

	public enum State {

		PLAYING(0),
		DONE(-1),
		SURRENDER(-2);

		/* Variables */
		private int stateCode;

		/* Constants */
		private State(int stateCode) {
			this.stateCode = stateCode;
		}

		/** @return State code in the database. */
		public int getStateCode() {
			return stateCode;
		}

	}

}