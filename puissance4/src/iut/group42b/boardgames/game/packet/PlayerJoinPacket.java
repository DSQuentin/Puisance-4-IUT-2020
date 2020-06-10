package iut.group42b.boardgames.game.packet;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class PlayerJoinPacket implements IPacket {

	// TODO ici le packet de join
	/* Variables */
	private long playerId;
	private long gameId;

	/* Constructor */
	public PlayerJoinPacket() {
		this(0, 0);
	}

	/* Constructor */
	public PlayerJoinPacket(long playerId, long gameId) {
		this.playerId = playerId;
		this.gameId = gameId;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(playerId);
		buffer.write(gameId);
	}

	@Override
	public void read(DataBuffer buffer) {
		playerId = buffer.readLong();
		gameId = buffer.readLong();
	}

	public long getPlayerId() {
		return playerId;
	}

	public long getGameId() {
		return gameId;
	}

	@Override
	public String toString() {
		return "PlayerJoinPacket{" +
				"playerId=" + playerId +
				", gameId=" + gameId +
				'}';
	}
}