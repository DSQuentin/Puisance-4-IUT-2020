package iut.group42b.boardgames.game.packet.matchmaking;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class MatchmakingJoinPacket implements IPacket {

	/* Variables */
	private int gameId;


	/**
	 * Constructor MatchmakingJoinPacket Empty to rebuild
	 */
	public MatchmakingJoinPacket() {
		this(0);
	}

	/**
	 * Constructor MatchmakingJoinPacket
	 *
	 * @param gameId
	 */
	public MatchmakingJoinPacket(int gameId) {
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

}