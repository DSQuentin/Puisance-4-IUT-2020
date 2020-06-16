package iut.group42b.boardgames.social.model;

import iut.group42b.boardgames.network.rw.IWritableReadableObject;
import iut.group42b.boardgames.util.DataBuffer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ExchangedMessage implements IWritableReadableObject {

	/* Constants */
	public static final DateFormat FORMATTER = new SimpleDateFormat("dd MMMM yyyy - HH:mm:ss");

	/* Variables */
	private int userId;
	private String date;
	private String content;

	/**
	 * Constructor ExchangedMessage empty to reconstruct the instance.
	 */
	public ExchangedMessage() {
		this(0, "", "");
	}

	/**
	 * Constructor ExchangedMessage to construct the object
	 *
	 * @param userId  Int, user id who send the message.
	 * @param content String witch is content of message.
	 */
	public ExchangedMessage(int userId, String content) {
		this(userId, new Date(), content);
	}

	/**
	 * Constructor ExchangedMessage to construct the object
	 *
	 * @param userId  Int, user id who send the message.
	 * @param date    String witch is the date of message.
	 * @param content String witch is content of message.
	 */
	public ExchangedMessage(int userId, Date date, String content) {
		this(userId, FORMATTER.format(date), content);
	}

	/**
	 * Constructor ExchangedMessage to construct the object
	 *
	 * @param userId  Int, user id who send the message.
	 * @param date    String witch is the date of message.
	 * @param content String witch is content of message.
	 */
	public ExchangedMessage(int userId, String date, String content) {
		this.userId = userId;
		this.date = date;
		this.content = content;
	}

	@Override
	public void write(DataBuffer buffer) {
		buffer.write(this.userId);
		buffer.write(this.date);
		buffer.write(this.content);
	}

	@Override
	public void read(DataBuffer buffer) {
		this.userId = buffer.readInt();
		this.date = buffer.readString();
		this.content = buffer.readString();
	}

	/**
	 * Get the id of message.
	 *
	 * @return
	 */
	public int getUserId() {
		return this.userId;
	}

	/**
	 * Get the date of the message.
	 *
	 * @return
	 */
	public String getDate() {
		return this.date;
	}

	public Date getDateObject() {
		try {
			return FORMATTER.parse(this.date);
		} catch (ParseException exception) {
			throw new IllegalStateException(exception);
		}
	}

	/**
	 * Get the content of the message.
	 *
	 * @return
	 */
	public String getContent() {
		return this.content;
	}

	@Override
	public String toString() {
		return "ExchangedMessage{" +
				"userId=" + this.userId +
				", date='" + this.date + '\'' +
				", content='" + this.content + '\'' +
				'}';
	}

}