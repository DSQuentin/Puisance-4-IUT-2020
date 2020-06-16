package iut.group42b.boardgames.game.impl.connect4;

import iut.group42b.boardgames.game.IGameArena;
import iut.group42b.boardgames.game.IGameHandler;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4GameInfoPacket;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4GridUpdatePacket;
import iut.group42b.boardgames.game.player.Player;
import iut.group42b.boardgames.network.packet.IPacket;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Connect4GameArena implements IGameArena {

	/* Constants */
	public static final int GRID_WIDTH = 7;
	public static final int GRID_HEIGHT = 6;
	/* Variables */
	public int scoreRed, scoreYellow;
	private long startedAt, endAt;
	private State state;
	private final Player redPlayer;
	private final Player yellowPlayer;
	private Connect4Side sideToPlay;
	private final Connect4Side[][] grid;
	private int databaseId;

	/* Constructor */
	public Connect4GameArena(Player redPlayer, Player yellowPlayer) {
		this.redPlayer = redPlayer;
		this.yellowPlayer = yellowPlayer;

		this.sideToPlay = ThreadLocalRandom.current().nextBoolean() ? Connect4Side.YELLOW : Connect4Side.RED;
		this.grid = new Connect4Side[GRID_HEIGHT][GRID_WIDTH];

		this.state = State.PLAYING; // TODO Move

		for (int y = 0; y < GRID_HEIGHT; y++) {
			for (int x = 0; x < GRID_WIDTH; x++) {
				this.grid[y][x] = Connect4Side.NONE;
			}
		}
	}

	public Connect4GameArena putTokenAt(Connect4Side side, int x, int y) {
		if (side != Connect4Side.NONE && this.grid[y][x] != Connect4Side.NONE) {
			return this;
		}

		this.grid[y][x] = side;

		if (side == Connect4Side.RED) {
			this.scoreRed++;
		} else {
			this.scoreYellow++;
		}

		return this;
	}

	/**
	 * Found @see <a href="https://stackoverflow.com/questions/32770321/connect-4-check-for-a-win-algorithm">here<a/> how to check win on connect4 games
	 *
	 * @return a simple boolean
	 */
	public boolean checkWin(Connect4Side side) {
		/* Horizontal check. */
		for (int j = 0; j < GRID_WIDTH - 3; j++) {
			for (int i = 0; i < GRID_HEIGHT; i++) {
				if (this.grid[i][j] == side && this.grid[i][j + 1] == side && this.grid[i][j + 2] == side && this.grid[i][j + 3] == side) {
					return true;
				}
			}
		}

		/* Vertical check. */
		for (int i = 0; i < GRID_HEIGHT - 3; i++) {
			for (int j = 0; j < GRID_WIDTH; j++) {
				if (this.grid[i][j] == side && this.grid[i + 1][j] == side && this.grid[i + 2][j] == side && this.grid[i + 3][j] == side) {
					return true;
				}
			}
		}

		/* Ascending diagonal check. */
		for (int i = 3; i < GRID_HEIGHT; i++) {
			for (int j = 0; j < GRID_WIDTH - 3; j++) {
				if (this.grid[i][j] == side && this.grid[i - 1][j + 1] == side && this.grid[i - 2][j + 2] == side && this.grid[i - 3][j + 3] == side) {
					return true;
				}
			}
		}

		/* Descending diagonal check. */
		for (int i = 3; i < GRID_HEIGHT; i++) {
			for (int j = 3; j < GRID_WIDTH; j++) {
				if (this.grid[i][j] == side && this.grid[i - 1][j - 1] == side && this.grid[i - 2][j - 2] == side && this.grid[i - 3][j - 3] == side) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public void start(IGameHandler gameHandler) {
		try {
			((Connect4GameHandler) gameHandler).startArena(this, this.redPlayer, this.yellowPlayer);

			this.yellowPlayer.getSocketHandler().queue(new Connect4GameInfoPacket(Connect4Side.YELLOW, this.redPlayer.getSocketHandler().getUserProfile()));
			this.redPlayer.getSocketHandler().queue(new Connect4GameInfoPacket(Connect4Side.RED, this.yellowPlayer.getSocketHandler().getUserProfile()));

			this.broadcast(new Connect4GridUpdatePacket(this.grid, this.sideToPlay));

			this.startedAt = System.currentTimeMillis();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	public void end(IGameHandler gameHandler) {
		this.endAt = System.currentTimeMillis();
	}

	public long duration() {
		if (this.state == State.PLAYING) {
			throw new IllegalStateException("Game is still playing");
		}

		return this.endAt - this.startedAt;
	}

	public Connect4Side getSideToPlay() {
		return this.sideToPlay;
	}

	public Connect4GameArena inverseSide() {
		if (this.sideToPlay == Connect4Side.RED) {
			this.sideToPlay = Connect4Side.YELLOW;
		} else {
			this.sideToPlay = Connect4Side.RED;
		}

		return this;
	}

	public Connect4Side[][] getGrid() {
		return this.grid;
	}

	public Player getYellowPlayer() {
		return this.yellowPlayer;
	}

	public Player getRedPlayer() {
		return this.redPlayer;
	}

	public void broadcast(IPacket packet) {
		this.yellowPlayer.getSocketHandler().queue(packet);
		this.redPlayer.getSocketHandler().queue(packet);
	}

	@Override
	public void setDatabaseId(int databaseId) {
		this.databaseId = databaseId;
	}

	@Override
	public int getDatabaseId() {
		return this.databaseId;
	}

	@Override
	public String toJSONGameState() {
		StringBuilder builder = new StringBuilder();

		builder.append("{\n");
		builder.append("\t\"side_to_play\": \"" + this.sideToPlay.toString() + "\",\n");
		builder.append("\t\"grid\": [\n");

		for (int y = 0; y < Connect4Game.NUMBER_OF_ROWS; y++) {
			builder.append("\t\t\"");

			for (int x = 0; x < Connect4Game.NUMBER_OF_COLUMNS; x++) {
				builder.append(this.grid[y][x].getLetter());
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

	@Override
	public List<Player> getPlayers() {
		return Arrays.asList(this.redPlayer, this.yellowPlayer);
	}

	public int getScoreRed() {
		return this.scoreRed;
	}

	public int getScoreYellow() {
		return this.scoreYellow;
	}

	public void setState(State state) {
		this.state = state;
	}

	public State getState() {
		return this.state;
	}

	public static void main(String[] args) {
		System.out.println(new Connect4GameArena(null, null)
				.putTokenAt(Connect4Side.RED, 3, 3)
				.putTokenAt(Connect4Side.YELLOW, 4, 3)
				.inverseSide()
				.toJSONGameState());
	}

	public boolean isPlaying() {
		return this.state == State.PLAYING;
	}

	public boolean isDone() {
		return this.state == State.DONE;
	}

	public boolean isSurrender() {
		return this.state == State.SURRENDER;
	}

	public enum State {

		PLAYING(0),
		DONE(-1),
		SURRENDER(-2);

		/* Variables */
		private final int stateCode;

		/* Constants */
		State(int stateCode) {
			this.stateCode = stateCode;
		}

		/**
		 * @return State code in the database.
		 */
		public int getStateCode() {
			return this.stateCode;
		}
	}

}