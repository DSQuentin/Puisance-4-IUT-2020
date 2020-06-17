package iut.group42b.boardgames.social.model.gamehistory;

import iut.group42b.boardgames.network.rw.IWritableReadableObject;
import iut.group42b.boardgames.util.DataBuffer;

public class GameHistoryItem implements IWritableReadableObject {

	private String started_at;
	private String end_at;
	private int state_number;
	private String game_state;
	private int id_user_1;
	private int id_user_2;
	private int score_1;
	private int score_2;

	public GameHistoryItem() {
		this(null, null, 0, null, 0, 0, 0, 0);
	}


	public GameHistoryItem(String started_at, String end_at, int state_number, String game_state, int id_user_1, int id_user_2, int score_1, int score_2) {
		this.started_at = started_at;
		this.end_at = end_at;
		this.state_number = state_number;
		this.game_state = game_state;
		this.id_user_1 = id_user_1;
		this.id_user_2 = id_user_2;
		this.score_1 = score_1;
		this.score_2 = score_2;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.started_at);
		buffer.write(this.end_at);
		buffer.write(this.state_number);
		buffer.write(this.game_state);
		buffer.write(this.id_user_1);
		buffer.write(this.id_user_2);
		buffer.write(this.score_1);
		buffer.write(this.score_2);

	}

	@Override
	public void read(DataBuffer buffer) {
		this.started_at = buffer.readString();
		this.end_at = buffer.readString();
		this.state_number = buffer.readInt();
		this.game_state = buffer.readString();
		this.id_user_1 = buffer.readInt();
		this.id_user_2 = buffer.readInt();
		this.score_1 = buffer.readInt();
		this.score_2 = buffer.readInt();
	}

	public String getStarted_at() {
		return this.started_at;
	}

	public String getEnd_at() {
		return this.end_at;
	}

	public int getState_number() {
		return this.state_number;
	}

	public String getGame_state() {
		return this.game_state;
	}

	public int getId_user_1() {
		return this.id_user_1;
	}

	public int getId_user_2() {
		return this.id_user_2;
	}

	public int getScore_1() {
		return this.score_1;
	}

	public int getScore_2() {
		return this.score_2;
	}
}
