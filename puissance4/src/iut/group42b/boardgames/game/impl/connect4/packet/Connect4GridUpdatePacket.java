package iut.group42b.boardgames.game.impl.connect4.packet;

import iut.group42b.boardgames.game.impl.connect4.Connect4Game;
import iut.group42b.boardgames.game.impl.connect4.Connect4Side;
import iut.group42b.boardgames.util.DataBuffer;

import java.util.Arrays;

public class Connect4GridUpdatePacket implements IConnect4Packet {

	/* Variables */
	private String linearGrid;
	private Connect4Side nextSideToPlay;


	/**
	 * Constructor Connect4GridUpdatePacket to rebuild
	 */
	public Connect4GridUpdatePacket() {
		this((String) null, null);
	}

	/* Constructor */

	/**
	 * Constructor Connect4GridUpdatePacket
	 *
	 * @param grid
	 */
	public Connect4GridUpdatePacket(Connect4Side[][] grid) {
		this(grid, Connect4Side.NONE);
	}


	/**
	 * Constructor Connect4GridUpdatePacket
	 *
	 * @param linearGrid
	 */
	public Connect4GridUpdatePacket(String linearGrid) {
		this(linearGrid, Connect4Side.NONE);
	}


	/**
	 * Constructor Connect4GridUpdatePacket
	 *
	 * @param grid
	 * @param nextSideToPlay
	 */
	public Connect4GridUpdatePacket(Connect4Side[][] grid, Connect4Side nextSideToPlay) {
		this(toLinearGrid(grid), nextSideToPlay);
	}


	/**
	 * Constructor Connect4GridUpdatePacket
	 *
	 * @param linearGrid
	 * @param nextSideToPlay
	 */
	public Connect4GridUpdatePacket(String linearGrid, Connect4Side nextSideToPlay) {
		this.linearGrid = linearGrid;
		this.nextSideToPlay = nextSideToPlay;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.linearGrid);
		buffer.write((byte) this.nextSideToPlay.ordinal());
	}

	@Override
	public void read(DataBuffer buffer) {
		this.linearGrid = buffer.readString();
		this.nextSideToPlay = Connect4Side.values()[buffer.readByte()];
	}

	/***
	 * Transform connect4 side grid object grid into string grid .
	 * @param grid
	 * @return
	 */
	public static String toLinearGrid(Connect4Side[][] grid) {
		StringBuilder builder = new StringBuilder();

		System.out.println(grid);
		System.out.println(Arrays.deepToString(grid));

		for (int y = 0; y < Connect4Game.NUMBER_OF_ROWS; y++) {
			for (int x = 0; x < Connect4Game.NUMBER_OF_COLUMNS; x++) {
				builder.append(grid[y][x].getLetter());
			}
		}

		return builder.toString();
	}

	/**
	 * Transform string grid into connect4 side grid object.
	 *
	 * @return Connect4Side[][]
	 */
	public Connect4Side[][] toSideGrid() {
		Connect4Side[][] grid = new Connect4Side[Connect4Game.NUMBER_OF_ROWS][Connect4Game.NUMBER_OF_COLUMNS];

		int offset = 0;

		for (int y = 0; y < Connect4Game.NUMBER_OF_ROWS; y++) {
			for (int x = 0; x < Connect4Game.NUMBER_OF_COLUMNS; x++) {
				grid[y][x] = Connect4Side.fromLetter(this.linearGrid.charAt(offset++));
			}
		}

		return grid;
	}

	/**
	 * Get the linear grid.
	 *
	 * @return String.
	 */
	public String getLinearGrid() {
		return this.linearGrid;
	}

	/**
	 * Get the next side to play.
	 *
	 * @return Connect4Side.
	 */
	public Connect4Side getNextSideToPlay() {
		return this.nextSideToPlay;
	}

}