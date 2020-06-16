package iut.group42b.boardgames.game.packet;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class PlayerJoinPacket implements IPacket {

	/* Variables */
	private int gameId;

	/* Constructor */
	public PlayerJoinPacket() {
		this(0);
	}

	/* Constructor */
	public PlayerJoinPacket(int gameId) {
		this.gameId = gameId;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.gameId);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.gameId = buffer.readInt();
	}

	public int getGameId() {
		return this.gameId;
	}

	@Override
	public String toString() {
		return "PlayerJoinPacket{" +
				"gameId=" + this.gameId +
				'}';
	}

}