package iut.group42b.boardgames.social.packet.history;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.gamehistory.GameHistoryItem;
import iut.group42b.boardgames.util.DataBuffer;

import java.util.ArrayList;
import java.util.List;

public class GameListHistoryPacket implements IPacket {


	/* Variables */
	private int userId;
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
		this(userId, new ArrayList<>());
	}

	/**
	 * @param userId
	 */
	public GameListHistoryPacket(int userId, List<GameHistoryItem> gameListHistory) {
		this.userId = userId;
		this.gameListHistory = gameListHistory;
	}


	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.userId);
		buffer.write(this.gameListHistory);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.userId = buffer.readInt();
		this.gameListHistory = buffer.readList(GameHistoryItem::new);
	}


	public int getUserId() {
		return this.userId;
	}

	public List<GameHistoryItem> getGameListHistory() {
		return this.gameListHistory;
	}
}
