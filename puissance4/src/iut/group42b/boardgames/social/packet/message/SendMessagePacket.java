package iut.group42b.boardgames.social.packet.message;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.ExchangedMessage;
import iut.group42b.boardgames.util.DataBuffer;

public class SendMessagePacket implements IPacket {

	/* Variable */
	private final ExchangedMessage message;

	/**
	 * Constructor  SendMessagePacket to rebuild the packet.
	 */
	public SendMessagePacket() {
		this(new ExchangedMessage());
	}

	/**
	 * Constructor  SendMessagePacket.
	 */
	public SendMessagePacket(ExchangedMessage message) {
		this.message = message;
	}

	@Override
	public void write(DataBuffer buffer) {
		this.message.write(buffer);

	}

	@Override
	public void read(DataBuffer buffer) {
		this.message.read(buffer);
	}

	public ExchangedMessage getMessage() {
		return this.message;
	}

}