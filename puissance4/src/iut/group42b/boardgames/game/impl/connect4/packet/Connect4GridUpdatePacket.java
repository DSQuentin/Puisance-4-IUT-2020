package iut.group42b.boardgames.game.impl.connect4.packet;

import iut.group42b.boardgames.game.impl.connect4.Connect4Game;
import iut.group42b.boardgames.game.impl.connect4.Connect4Side;
import iut.group42b.boardgames.util.DataBuffer;

import java.util.Arrays;

public class Connect4GridUpdatePacket implements IConnect4Packet {

	/* Variables */
	private String linearGrid;
	private Connect4Side nextSideToPlay;

	/* Constructor */
	public Connect4GridUpdatePacket() {
		this((String) null, null);
	}

	/* Constructor */
	public Connect4GridUpdatePacket(Connect4Side[][] grid) {
		this(grid, Connect4Side.NONE);
	}

	/* Constructor */
	public Connect4GridUpdatePacket(String linearGrid) {
		this(linearGrid, Connect4Side.NONE);
	}

	/* Constructor */
	public Connect4GridUpdatePacket(Connect4Side[][] grid, Connect4Side nextSideToPlay) {
		this(toLinearGrid(grid), nextSideToPlay);
	}

	/* Constructor */
	public Connect4GridUpdatePacket(String linearGrid, Connect4Side nextSideToPlay) {
		this.linearGrid = linearGrid;
		this.nextSideToPlay = nextSideToPlay;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(linearGrid);
		buffer.write((byte) nextSideToPlay.ordinal());
	}

	@Override
	public void read(DataBuffer buffer) {
		linearGrid = buffer.readString();
		nextSideToPlay = Connect4Side.values()[buffer.readByte()];
	}

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

	public Connect4Side[][] toSideGrid() {
		Connect4Side[][] grid = new Connect4Side[Connect4Game.NUMBER_OF_ROWS][Connect4Game.NUMBER_OF_COLUMNS];

		int offset = 0;

		for (int y = 0; y < Connect4Game.NUMBER_OF_ROWS; y++) {
			for (int x = 0; x < Connect4Game.NUMBER_OF_COLUMNS; x++) {
				grid[y][x] = Connect4Side.fromLetter(linearGrid.charAt(offset++));
			}
		}

		return grid;
	}

	public String getLinearGrid() {
		return linearGrid;
	}

	public Connect4Side getNextSideToPlay() {
		return nextSideToPlay;
	}

}