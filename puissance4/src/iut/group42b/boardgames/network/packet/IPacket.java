package iut.group42b.boardgames.network.packet;

import iut.group42b.boardgames.util.DataBuffer;

public interface IPacket {

	public void write(DataBuffer buffer);

	public void read(DataBuffer buffer);

}