package iut.group42b.boardgames.game.impl.connect4;

import iut.group42b.boardgames.game.IGameArena;
import iut.group42b.boardgames.game.player.Player;

public class Connect4GameArena implements IGameArena {

	/* Constants */
	public static final int GRID_WIDTH = 7;
	public static final int GRID_HEIGHT = 6;
	public int scoreRed, scoreYellow;
	/* Variables */
	private int id;
	private long startedAt, endAt;
	private State state;
	private Player redPlayer;
	private Player yellowPlayer;
	private Connect4Side sideToPlay;
	private Connect4Side[][] grid;

	/* Constructor */
	public Connect4GameArena(Player redPlayer, Player yellowPlayer) {
		this.redPlayer = redPlayer;
		this.yellowPlayer = yellowPlayer;

		this.grid = new Connect4Side[GRID_HEIGHT][GRID_WIDTH];

		for (int y = 0; y < grid.length; y++) {
			for (int x = 0; x < grid[y].length; x++) {
				grid[y][x] = Connect4Side.NONE;
			}
		}
	}

	public void putTokenAt(Connect4Side side, int x, int y) {
		if (side != Connect4Side.NONE && grid[y][x] != Connect4Side.NONE) {
			return;
		}

		grid[y][x] = side;

		if (side == Connect4Side.RED) {
			scoreRed++;
		} else {
			scoreYellow++;
		}
	}

	public boolean checkWin(Connect4Side side) {
		/* Horizontal check. */
		for (int j = 0; j < GRID_HEIGHT - 3; j++) {
			for (int i = 0; i < GRID_WIDTH; i++) {
				if (grid[i][j] == side && grid[i][j + 1] == side && grid[i][j + 2] == side && grid[i][j + 3] == side) {
					return true;
				}
			}
		}

		/* Vertical check. */
		for (int i = 0; i < GRID_WIDTH - 3; i++) {
			for (int j = 0; j < this.GRID_HEIGHT; j++) {
				if (grid[i][j] == side && grid[i + 1][j] == side && grid[i + 2][j] == side && grid[i + 3][j] == side) {
					return true;
				}
			}
		}

		/* Ascending diagonal check. */
		for (int i = 3; i < GRID_WIDTH; i++) {
			for (int j = 0; j < GRID_HEIGHT - 3; j++) {
				if (grid[i][j] == side && grid[i - 1][j + 1] == side && grid[i - 2][j + 2] == side && grid[i - 3][j + 3] == side) {
					return true;
				}
			}
		}

		/* Descending diagonal check. */
		for (int i = 3; i < GRID_WIDTH; i++) {
			for (int j = 3; j < GRID_HEIGHT; j++) {
				if (grid[i][j] == side && grid[i - 1][j - 1] == side && grid[i - 2][j - 2] == side && grid[i - 3][j - 3] == side) {
					return true;
				}
			}
		}

		return false;
	}

	public void start() {
		startedAt = System.currentTimeMillis();
	}

	public void end() {
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

	public void inverseSide() {
		if (sideToPlay == Connect4Side.RED) {
			sideToPlay = Connect4Side.YELLOW;
		} else {
			sideToPlay = Connect4Side.RED;
		}
	}

	public enum State {

		PLAYING,
		DONE,
		SURRENDED;

	}

}