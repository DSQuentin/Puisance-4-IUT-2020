package iut.group42b.boardgames.game.impl.connect4.packet;

import iut.group42b.boardgames.game.impl.connect4.Connect4Side;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.DataBuffer;

public class Connect4GameInfoPacket implements IConnect4Packet {

	/* Variables */
	private Connect4Side mySide;
	private UserProfile opponentProfile;


	/**
	 * Constructor Connect4GameInfoPacket Empty constructor to rebuild
	 */
	public Connect4GameInfoPacket() {
		this(null, null);
	}


	/**
	 * Constructor Connect4GameInfoPacket
	 *
	 * @param mySide          Connect4Side
	 * @param opponentProfile UserProfile
	 */
	public Connect4GameInfoPacket(Connect4Side mySide, UserProfile opponentProfile) {
		this.mySide = mySide;
		this.opponentProfile = opponentProfile;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write((byte) this.mySide.ordinal());
		this.opponentProfile.write(buffer);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.mySide = Connect4Side.values()[buffer.readByte()];

		this.opponentProfile = new UserProfile();
		this.opponentProfile.read(buffer);
	}

	/**
	 * Get this own side
	 *
	 * @return Connect4Side
	 */
	public Connect4Side getMySide() {
		return this.mySide;
	}

	/**
	 * Get the opponent userprofile
	 *
	 * @return
	 */
	public UserProfile getOpponentProfile() {
		return this.opponentProfile;
	}

}