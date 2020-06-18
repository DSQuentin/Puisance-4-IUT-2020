package iut.group42b.boardgames.social.packet.friendship;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.DataBuffer;

public class FriendNumberPacket implements IPacket {

	private int number ;

	public FriendNumberPacket() {
		this(0);
	}

	public FriendNumberPacket(int number) {
		this.number = number;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.number);

	}

	@Override
	public void read(DataBuffer buffer) {
		this.number = buffer.readInt();
	}

	public int getNumber() {
		return this.number;
	}
}


	//select count(*) from are_friends where id_user_1=1 or id_user_2=1;