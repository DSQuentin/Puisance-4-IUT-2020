package iut.group42b.boardgames.game.impl.connect4.packet;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

/**
 * DO NOT REGISTER
 */
public abstract class Connect4BasePacket implements IPacket {

	/* Variables */
	private int arenaIdentifier;


	/**
	 * Constructor Connect4BasePacket Empty packet to rebuild
	 */
	public Connect4BasePacket() {
		this(0);
	}


	/**
	 * Constructor Connect4BasePacket
	 *
	 * @param arenaIdentifier
	 */
	public Connect4BasePacket(int arenaIdentifier) {
		this.arenaIdentifier = arenaIdentifier;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.arenaIdentifier);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.arenaIdentifier = buffer.readInt();
	}

	/**
	 * Get the arena Id
	 *
	 * @return int
	 */
	public int getArenaIdentifier() {
		return this.arenaIdentifier;
	}
}