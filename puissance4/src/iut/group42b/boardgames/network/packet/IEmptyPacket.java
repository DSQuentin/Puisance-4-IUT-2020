package iut.group42b.boardgames.network.packet;

import iut.group42b.boardgames.util.DataBuffer;

public interface IEmptyPacket extends IPacket {

	public default void write(DataBuffer buffer) {
		;
	}

	public default void read(DataBuffer buffer) {
		;
	}


}