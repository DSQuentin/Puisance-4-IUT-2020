package iut.group42b.boardgames.social.packet.history;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.gamehistory.GameHistoryItem;
import iut.group42b.boardgames.util.DataBuffer;

import java.util.ArrayList;
import java.util.List;

public class GameListHistoryPacket implements IPacket {


	/* Variables */
	private int userId;
	private int totalPlayed;
	private List<GameHistoryItem> gameListHistory;

	/**
	 * GameListHistoryPacket Constructor Empty to rebuild the packet.
	 */
	public GameListHistoryPacket() {
		this(0);
	}

	/**
	 * @param userId
	 */
	public GameListHistoryPacket(int userId) {
		this(userId, 0, new ArrayList<>());
	}

	/**
	 * @param userId
	 */
	public GameListHistoryPacket(int userId, int totalPlayed, List<GameHistoryItem> gameListHistory) {
		this.userId = userId;
		this.totalPlayed = totalPlayed;
		this.gameListHistory = gameListHistory;
	}


	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.userId);
		buffer.write(this.totalPlayed);
		buffer.write(this.gameListHistory);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.userId = buffer.readInt();
		this.totalPlayed = buffer.readInt();
		this.gameListHistory = buffer.readList(GameHistoryItem::new);
	}


	public int getUserId() {
		return this.userId;
	}

	public int getTotalPlayed() {
		return this.totalPlayed;
	}

	public List<GameHistoryItem> getGameListHistory() {
		return this.gameListHistory;
	}
}
