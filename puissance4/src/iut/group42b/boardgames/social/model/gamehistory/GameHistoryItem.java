package iut.group42b.boardgames.social.model.gamehistory;

import iut.group42b.boardgames.network.rw.IWritableReadableObject;
import iut.group42b.boardgames.util.DataBuffer;

public class GameHistoryItem implements IWritableReadableObject {

	/* Variables */


	int gameState;
	int user1Id;
	String user1Name;
	int user2Id;
	String user2Name;
	int idUserWinner;
	String duration;
	int winnerScore;
	String startedAt;


	/**
	 * Constructor GameHistoryItem Empty to rebuild
	 */
	public GameHistoryItem() {
		this(0, 0, "", 0, "", 0, "", 0, "");
	}

	/**
	 * Constructor GameHistoryItem
	 *
	 * @param gameState
	 * @param user1Id
	 * @param user1Name
	 * @param user2Id
	 * @param user2Name
	 * @param idUserWinner
	 * @param duration
	 * @param winnerScore
	 * @param startedAt
	 */
	public GameHistoryItem(int gameState, int user1Id, String user1Name, int user2Id, String user2Name, int idUserWinner, String duration, int winnerScore, String startedAt) {
		this.gameState = gameState;
		this.user1Id = user1Id;
		this.user1Name = user1Name;
		this.user2Id = user2Id;
		this.user2Name = user2Name;
		this.idUserWinner = idUserWinner;
		this.duration = duration;
		this.winnerScore = winnerScore;
		this.startedAt = startedAt;
	}

	@Override
	public void write(DataBuffer buffer) {

		buffer.write(this.gameState);
		buffer.write(this.user1Id);
		buffer.write(this.user1Name);
		buffer.write(this.user2Id);
		buffer.write(this.user2Name);
		buffer.write(this.idUserWinner);
		buffer.write(this.duration);
		buffer.write(this.winnerScore);
		buffer.write(this.startedAt);

	}

	@Override
	public void read(DataBuffer buffer) {

		this.gameState = buffer.readInt();
		this.user1Id = buffer.readInt();
		this.user1Name = buffer.readString();
		this.user2Id = buffer.readInt();
		this.user2Name = buffer.readString();
		this.idUserWinner = buffer.readInt();
		this.duration = buffer.readString();
		this.winnerScore = buffer.readInt();
		this.startedAt = buffer.readString();


	}

	public int getGameState() {
		return this.gameState;
	}

	public int getUser1Id() {
		return this.user1Id;
	}

	public String getUser1Name() {
		return this.user1Name;
	}

	public int getUser2Id() {
		return this.user2Id;
	}

	public String getUser2Name() {
		return this.user2Name;
	}

	public int getIdUserWinner() {
		return this.idUserWinner;
	}

	public String getDuration() {
		return this.duration;
	}

	public int getWinnerScore() {
		return this.winnerScore;
	}

	public String getStartedAt() {
		return this.startedAt;
	}
}
