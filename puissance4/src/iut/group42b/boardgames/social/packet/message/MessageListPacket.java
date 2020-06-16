package iut.group42b.boardgames.social.packet.message;

import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.ExchangedMessage;
import iut.group42b.boardgames.util.DataBuffer;

import java.util.Collections;
import java.util.List;

public class MessageListPacket implements IPacket {

	/* Variables */
	public int senderId;
	private List<ExchangedMessage> messages;

	/**
	 * Constructor MessageListPacket Empty to rebuild the packet.
	 */
	public MessageListPacket() {
		this(0);
	}

	/**
	 * Constructor MessageListPacket.
	 *
	 * @param senderId int.
	 */
	public MessageListPacket(int senderId) {
		this(senderId, Collections.emptyList());
	}

	/**
	 * Constructor MessageListPacket
	 *
	 * @param senderId int.
	 * @param messages List<ExchangedMessage>.
	 */
	public MessageListPacket(int senderId, List<ExchangedMessage> messages) {
		this.senderId = senderId;
		this.messages = messages;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.senderId);
		buffer.write(this.messages);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.senderId = buffer.readInt();
		this.messages = buffer.readList(ExchangedMessage::new);
	}

	/**
	 * Get the Sender Id.
	 *
	 * @return int.
	 */
	public int getSenderId() {
		return this.senderId;
	}

	/**
	 * Get the messages.
	 *
	 * @return List<ExchangedMessage>
	 */
	public List<ExchangedMessage> getMessages() {
		return this.messages;
	}

}