package iut.group42b.boardgames.network.packet;

import iut.group42b.boardgames.util.DataBuffer;

public interface IEmptyPacket extends IPacket {

	/**
	 * Write in buffer.
	 *
	 * @param buffer DataBuffer.
	 */
	default void write(DataBuffer buffer) {
	}

	/**
	 * Read from a buffer.
	 *
	 * @param buffer DataBuffer.
	 */
	default void read(DataBuffer buffer) {
	}


}