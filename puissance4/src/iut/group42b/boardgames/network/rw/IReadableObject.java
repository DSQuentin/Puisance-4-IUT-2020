package iut.group42b.boardgames.network.rw;

import iut.group42b.boardgames.util.DataBuffer;

public interface IReadableObject {

	/**
	 * Read object form buffer.
	 *
	 * @param buffer DataBuffer.
	 */
	void read(DataBuffer buffer);

}