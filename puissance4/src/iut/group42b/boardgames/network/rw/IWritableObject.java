package iut.group42b.boardgames.network.rw;

import iut.group42b.boardgames.util.DataBuffer;

public interface IWritableObject {

	/**
	 * Write Object in socket.
	 *
	 * @param buffer DataBuffer.
	 */
	void write(DataBuffer buffer);

}