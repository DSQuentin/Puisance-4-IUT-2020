package iut.group42b.boardgames.game.impl.connect4.packet;

import iut.group42b.boardgames.game.impl.connect4.Connect4Side;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.DataBuffer;

public class Connect4GameInfoPacket implements IConnect4Packet {

	/* Variables */
	private Connect4Side mySide;
	private UserProfile opponentProfile;

	/* Constructor */
	public Connect4GameInfoPacket() {
		this(null, null);
	}

	/* Constructor */
	public Connect4GameInfoPacket(Connect4Side mySide, UserProfile opponentProfile) {
		this.mySide = mySide;
		this.opponentProfile = opponentProfile;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write((byte) mySide.ordinal());
		opponentProfile.write(buffer);
	}

	@Override
	public void read(DataBuffer buffer) {
		mySide = Connect4Side.values()[buffer.readByte()];

		opponentProfile = new UserProfile();
		opponentProfile.read(buffer);
	}

	public Connect4Side getMySide() {
		return mySide;
	}

	public UserProfile getOpponentProfile() {
		return opponentProfile;
	}

}