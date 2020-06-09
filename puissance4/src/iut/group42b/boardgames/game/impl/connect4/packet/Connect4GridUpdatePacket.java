package iut.group42b.boardgames.game.impl.connect4.packet;

import iut.group42b.boardgames.util.DataBuffer;

public class Connect4GridUpdatePacket extends Connect4BasePacket {

	/* Variables */
	private String linearGrid;

	/* Constructor */
	public Connect4GridUpdatePacket() {
		this(0, null);
	}

	/* Constructor */
	public Connect4GridUpdatePacket(int arenaIdentifier, String linearGrid) {
		super(arenaIdentifier);

		this.linearGrid = linearGrid;
	}

	@Override
	public void write(DataBuffer buffer) {
		super.write(buffer);

		buffer.write(linearGrid);
	}

	@Override
	public void read(DataBuffer buffer) {
		super.read(buffer);

		linearGrid = buffer.readString();
	}

}