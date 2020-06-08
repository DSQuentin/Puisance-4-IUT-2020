package iut.group42b.boardgames.game.impl.connect4.packet;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

/**
 * DO NOT REGISTER
 */
public abstract class Connect4BasePacket implements IPacket {

	/* Variables */
	private int arenaIdentifier;

	/* Constructor */
	public Connect4BasePacket() {
		this(0);
	}

	/* Constructor */
	public Connect4BasePacket(int arenaIdentifier) {
		this.arenaIdentifier = arenaIdentifier;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(arenaIdentifier);
	}

	@Override
	public void read(DataBuffer buffer) {
		arenaIdentifier = buffer.readInt();
	}

	public int getArenaIdentifier() {
		return arenaIdentifier;
	}
}