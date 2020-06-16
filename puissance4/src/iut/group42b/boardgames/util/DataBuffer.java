package iut.group42b.boardgames.util;

import iut.group42b.boardgames.network.rw.IReadableObject;
import iut.group42b.boardgames.network.rw.IWritableObject;

import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DataBuffer {

	private final List<Byte> bytes;
	/* Variables */
	private int index;

	/**
	 * Constructor DataBuffer, init an arrayList of bytes, and set index to zero.
	 */
	public DataBuffer() {
		this.index = 0;
		this.bytes = new ArrayList<>();
	}

	/**
	 * Read boolean from buffer.
	 *
	 * @return Boolean value.
	 */
	public boolean readBoolean() {
		return this.readByte() != 0;
	}

	/**
	 * Write boolean in buffer.
	 *
	 * @param b A Boolean value.
	 * @return DataBuffer.
	 */
	public DataBuffer writeBoolean(boolean b) {
		return this.writeByte(b ? (byte) 1 : (byte) 0);
	}

	/**
	 * Read byte value from buffer.
	 *
	 * @return
	 */
	public byte readByte() {
		return this.bytes.get(this.index++);
	}

	/**
	 * Write byte value in buffer.
	 *
	 * @param b A byte.
	 * @return DataBuffer.
	 */
	public DataBuffer writeByte(byte b) {
		this.bytes.add(b);

		return this;
	}

	/**
	 * Write bytes in buffer.
	 *
	 * @param array Array of bytes.
	 * @return DataBuffer.
	 */
	public DataBuffer writeBytes(byte[] array) {
		for (byte b : array) {
			this.bytes.add(b);
		}

		return this;
	}

	/**
	 * Write String in buffer.
	 *
	 * @param s A String value.
	 * @return DataBuffer.
	 */
	public DataBuffer writeString(String s) {
		byte[] stringBytes;

		try {
			// https://en.wikipedia.org/wiki/Windows-1252
			stringBytes = s.getBytes(Charset.forName("CP1252"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		for (byte b : stringBytes) {
			this.writeByte(b);
		}

		this.writeByte((byte) 0); // Write null terminator like in C -> "Hello\0"

		return this;
	}

	/**
	 * Read string value from buffer.
	 *
	 * @return String value.
	 */
	public String readString() {
		StringBuilder res = new StringBuilder();

		for (int i = this.index; i < this.bytes.size(); i++) {
			if (this.bytes.get(i) == 0) { // if we found null terminator
				break;
			}
			// https://stackoverflow.com/questions/5738633/is-there-a-simple-way-to-append-a-byte-to-a-stringbuffer-and-specify-the-encodin
			// to support é or à, è characters
			res.append(new String(new byte[]{this.bytes.get(i)}, Charset.forName("CP1252")));  // TODO: found better way
		}

		this.index += res.length() + 1;

		return res.toString();
	}


	/**
	 * Write int value in buffer.
	 * <p>AAAAAAAA BBBBBBB CCCCCCC DDDDDDD </p>
	 *
	 * @param i Int value.
	 * @return DataBuffer.
	 */
	public DataBuffer writeInt(int i) {
		this.bytes.add((byte) (i >> 24 & 0xFF));
		this.bytes.add((byte) (i >> 16 & 0xFF));
		this.bytes.add((byte) (i >> 8 & 0xFF));
		this.bytes.add((byte) (i >> 0 & 0xFF));

		return this;
	}

	/**
	 * Read int value form buffer.
	 *
	 * @return Int value.
	 */
	public int readInt() {
		return ((((int) this.readByte()) << 24) & 0xFF000000)
				+ ((((int) this.readByte()) << 16) & 0x00FF0000)
				+ ((((int) this.readByte()) << 8) & 0x0000FF00)
				+ ((((int) this.readByte()) << 0) & 0x000000FF);
	}


	/**
	 * Write a databuffer of short.
	 * <p>
	 * AAAAAAAA BBBBBBB CCCCCCC DDDDDDD
	 * </p>
	 *
	 * @param s
	 * @return A DataBuffer
	 */
	public DataBuffer writeShort(short s) {
		this.bytes.add((byte) (s >> 8 & 0xFF));
		this.bytes.add((byte) (s >> 0 & 0xFF));

		return this;
	}

	/**
	 * Retreive a short value form buffer.
	 *
	 * @return A short value.
	 */
	public short readShort() {
		return (short) (((((short) this.readByte()) << 8) & 0xFF00)
				+ ((((short) this.readByte()) << 0) & 0x00FF));
	}

	/**
	 * Write Long value into databuffer
	 * <p>AAAAAAAA BBBBBBBB CCCCCCCC DDDDDDDD EEEEEEEE FFFFFFFF GGGGGGGG HHHHHHHH</p>
	 *
	 * @param l A Long value.
	 * @see <a href="http://bitwisecmd.com/">Binary operations</a>
	 */
	public DataBuffer writeLong(long l) {
		this.bytes.add((byte) (l >> 56 & 0xFF));
		this.bytes.add((byte) (l >> 48 & 0xFF));
		this.bytes.add((byte) (l >> 40 & 0xFF));
		this.bytes.add((byte) (l >> 32 & 0xFF));
		this.bytes.add((byte) (l >> 24 & 0xFF));
		this.bytes.add((byte) (l >> 16 & 0xFF));
		this.bytes.add((byte) (l >> 8 & 0xFF));
		this.bytes.add((byte) (l >> 0 & 0xFF));

		return this;
	}

	/**
	 * Reading Long value from buffer.
	 *
	 * @return Long readed inside the buffer.
	 */
	public long readLong() {
		return ((((long) this.readByte()) << 56) & 0xFF00000000000000l)
				+ ((((long) this.readByte()) << 48) & 0x00FF000000000000l)
				+ ((((long) this.readByte()) << 40) & 0x0000FF0000000000l)
				+ ((((long) this.readByte()) << 32) & 0x000000FF00000000l)
				+ ((((long) this.readByte()) << 24) & 0x00000000FF000000l)
				+ ((((long) this.readByte()) << 16) & 0x0000000000FF0000l)
				+ ((((long) this.readByte()) << 8) & 0x000000000000FF00l)
				+ ((((long) this.readByte()) << 0) & 0x00000000000000FFl);
	}

	/**
	 * Write a List of WritableObject in buffer.
	 *
	 * @param list
	 * @return DataBuffer
	 */
	public DataBuffer writeList(List<? extends IWritableObject> list) {
		this.writeInt(list.size()); // send size first to retrieve number of elements to read.

		for (IWritableObject object : list) {
			object.write(this);
		}

		return this;
	}

	/**
	 * Read list from buffer.
	 *
	 * @param instanceCreator
	 * @param <T>             Accept many type of objects.
	 * @return A List of ReadableObject
	 */
	public <T extends IReadableObject> List<T> readList(Supplier<T> instanceCreator) { // Supplier to get the instance of object
		List<T> list = new ArrayList<>(); // https://openclassrooms.com/fr/courses/26832-apprenez-a-programmer-en-java/22404-apprehendez-la-genericite-en-java

		int listSize = this.readInt(); // read size at first to know how many element we have.
		for (int i = 0; i < listSize; i++) {
			T instance = instanceCreator.get();

			instance.read(this);

			list.add(instance);
		}

		return list;
	}

	/**
	 * Set the read head index to 0.
	 */
	public void rewind() {
		this.index = 0;
	}

	/**
	 * Write boolean value in buffer.
	 *
	 * @param b Boolean to write.
	 * @return DataBuffer.
	 */
	public DataBuffer write(boolean b) {
		return this.writeBoolean(b);
	}

	/**
	 * Write a byte value into buffer.
	 *
	 * @param b A byte.
	 * @return DataBuffer.
	 */
	public DataBuffer write(byte b) {
		return this.writeByte(b);
	}

	/**
	 * Write a short value in buffer.
	 *
	 * @param s A short value.
	 * @return DataBuffer.
	 */
	public DataBuffer write(short s) {
		return this.writeShort(s);
	}

	/**
	 * Write int value in buffer.
	 *
	 * @param i Int value.
	 * @return DataBuffer.
	 */
	public DataBuffer write(int i) {
		return this.writeInt(i);
	}

	/**
	 * Write long value in buffer.
	 *
	 * @param l A Long value.
	 * @return DataBuffer.
	 */
	public DataBuffer write(long l) {
		return this.writeLong(l);
	}

	/**
	 * Write String value in buffer.
	 *
	 * @param str String value.
	 * @return DataBuffer.
	 */
	public DataBuffer write(String str) {
		return this.writeString(str);
	}

	/**
	 * Write list of WritableObject
	 *
	 * @param list every object who extend of IWratableObject
	 * @return DataBuffer.
	 */
	public DataBuffer write(List<? extends IWritableObject> list) {
		return this.writeList(list);
	}

	/**
	 * Tool to visualize the buffer.
	 *
	 * @param printStream System.out
	 */
	public void dump(PrintStream printStream) {
		printStream.println(String.format("index at %s", this.index));

		for (int i = 0; i < this.bytes.size(); i++) {
			int unsigned = Byte.toUnsignedInt(this.bytes.get(i));

			printStream.println(String.format("[%3s] = %4s or %8s", i, unsigned, String.format("%8s", Integer.toBinaryString(unsigned)).replace(" ", "0")));
		}
	}

	/**
	 * Create an array of bytes from ArrayList.
	 *
	 * @return A byte array.
	 */
	public byte[] toByteArray() {
		// sync -> sert a eviter d'écrire et de copier en meme temps
		synchronized (this.bytes) {
			byte[] array = new byte[this.bytes.size()];

			for (int i = 0; i < array.length; i++) {
				array[i] = this.bytes.get(i);
			}

			return array;
		}
	}

	/**
	 * Get the size of buffer (number of bytes ).
	 *
	 * @return Int value.
	 */
	public int size() {
		return this.bytes.size();
	}

	/**
	 * Write a DataBuffer
	 *
	 * @param other DataBuffer value.
	 */
	public void writeBuffer(DataBuffer other) {
		this.bytes.addAll(other.bytes);
	}

	/**
	 * Clear the buffer.
	 */
	public void reset() {
		this.index = 0;
		this.bytes.clear();
	}

}