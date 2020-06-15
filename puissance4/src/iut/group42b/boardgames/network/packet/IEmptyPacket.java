package iut.group42b.boardgames.network.packet;

import iut.group42b.boardgames.util.DataBuffer;

public interface IEmptyPacket extends IPacket {

	default void write(DataBuffer buffer) {
	}

	default void read(DataBuffer buffer) {
	}


}