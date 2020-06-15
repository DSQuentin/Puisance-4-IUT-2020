package iut.group42b.boardgames.game.packet.matchmaking;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class MatchmakingJoinPacket implements IPacket {

	/* Variables */
	private int gameId;

	/* Constructor */
	public MatchmakingJoinPacket() {
		this(0);
	}

	/* Constructor */
	public MatchmakingJoinPacket(int gameId) {
		this.gameId = gameId;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(gameId);
	}

	@Override
	public void read(DataBuffer buffer) {
		gameId = buffer.readInt();
	}

	public int getGameId() {
		return gameId;
	}

}