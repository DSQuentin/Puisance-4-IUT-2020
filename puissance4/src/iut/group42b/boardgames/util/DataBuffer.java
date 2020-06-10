package iut.group42b.boardgames.util;

import iut.group42b.boardgames.network.rw.IReadableObject;
import iut.group42b.boardgames.network.rw.IWritableObject;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DataBuffer {

	/* Variables */
	private int index;
	private List<Byte> bytes;

	/* Constructor */
	public DataBuffer() {
		this.index = 0;
		this.bytes = new ArrayList<>();
	}

	public boolean readBoolean() {
		return readByte() != 0;
	}

	public DataBuffer writeBoolean(boolean b) {
		return writeByte(b ? (byte) 1 : (byte) 0);
	}

	public byte readByte() {
		return bytes.get(index++);
	}

	public DataBuffer writeByte(byte b) {
		bytes.add(b);

		return this;
	}

	public DataBuffer writeBytes(byte[] array) {
		for (byte b : array) {
			bytes.add(b);
		}

		return this;
	}

	public DataBuffer writeString(String s) {
		byte[] stringBytes;

		try {
			stringBytes = s.getBytes("US-ASCII");
		} catch (Exception e) {
			throw new RuntimeException(e);

		}
		for (byte b : stringBytes) {
			writeByte(b);
		}

		writeByte((byte) 0);

		return this;
	}

	public String readString() {
		StringBuilder res = new StringBuilder();

		for (int i = index; i < bytes.size(); i++) {
			if (bytes.get(i) == 0) {
				break;
			}

			res.append((char) bytes.get(i).shortValue());
		}

		index += res.length() + 1;

		return res.toString();
	}

	// AAAAAAAA BBBBBBB CCCCCCC DDDDDDD |
	public DataBuffer writeInt(int i) {
		bytes.add((byte) (i >> 24 & 0xFF));
		bytes.add((byte) (i >> 16 & 0xFF));
		bytes.add((byte) (i >> 8 & 0xFF));
		bytes.add((byte) (i >> 0 & 0xFF));

		return this;
	}

	public int readInt() {
		return ((((int) readByte()) << 24) & 0xFF000000)
				+ ((((int) readByte()) << 16) & 0x00FF0000)
				+ ((((int) readByte()) << 8) & 0x0000FF00)
				+ ((((int) readByte()) << 0) & 0x000000FF);
	}

	// AAAAAAAA BBBBBBB CCCCCCC DDDDDDD |
	public DataBuffer writeShort(short s) {
		bytes.add((byte) (s >> 8 & 0xFF));
		bytes.add((byte) (s >> 0 & 0xFF));

		return this;
	}

	public short readShort() {
		return (short) (((((short) readByte()) << 8) & 0xFF00)
				+ ((((short) readByte()) << 0) & 0x00FF));
	}

	/**
	 * AAAAAAAA BBBBBBBB CCCCCCCC DDDDDDDDEEEEEEEEFFFFFFFFGGGGGGGGHHHHHHHH
	 * http://bitwisecmd.com/
	 *
	 * @param l
	 */
	public DataBuffer writeLong(long l) {
		bytes.add((byte) (l >> 56 & 0xFF));
		bytes.add((byte) (l >> 48 & 0xFF));
		bytes.add((byte) (l >> 40 & 0xFF));
		bytes.add((byte) (l >> 32 & 0xFF));
		bytes.add((byte) (l >> 24 & 0xFF));
		bytes.add((byte) (l >> 16 & 0xFF));
		bytes.add((byte) (l >> 8 & 0xFF));
		bytes.add((byte) (l >> 0 & 0xFF));

		return this;
	}

	public long readLong() {
		return ((((long) readByte()) << 56) & 0xFF00000000000000l)
				+ ((((long) readByte()) << 48) & 0x00FF000000000000l)
				+ ((((long) readByte()) << 40) & 0x0000FF0000000000l)
				+ ((((long) readByte()) << 32) & 0x000000FF00000000l)
				+ ((((long) readByte()) << 24) & 0x00000000FF000000l)
				+ ((((long) readByte()) << 16) & 0x0000000000FF0000l)
				+ ((((long) readByte()) << 8) & 0x000000000000FF00l)
				+ ((((long) readByte()) << 0) & 0x00000000000000FFl);
	}

	public DataBuffer writeList(List<? extends IWritableObject> list) {
		writeInt(list.size());

		for (IWritableObject object : list) {
			object.write(this);
		}

		return this;
	}

	public <T extends IReadableObject> List<T> readList(Supplier<T> instanceCreator) {
		List<T> list = new ArrayList<>();

		int listSize = readInt();
		for (int i = 0; i < listSize; i++) {
			T instance = instanceCreator.get();

			instance.read(this);

			list.add(instance);
		}

		return list;
	}

	public void rewind() {
		index = 0;
	}

	public DataBuffer write(boolean b) {
		return writeBoolean(b);
	}

	public DataBuffer write(byte b) {
		return writeByte(b);
	}

	public DataBuffer write(short s) {
		return writeShort(s);
	}

	public DataBuffer write(int i) {
		return writeInt(i);
	}

	public DataBuffer write(long l) {
		return writeLong(l);
	}

	public DataBuffer write(String str) {
		return writeString(str);
	}

	public DataBuffer write(List<? extends IWritableObject> list) {
		return writeList(list);
	}

	public void dump(PrintStream printStream) {
		printStream.println(String.format("index at %s", index));

		for (int i = 0; i < bytes.size(); i++) {
			int unsigned = Byte.toUnsignedInt(bytes.get(i));

			printStream.println(String.format("[%3s] = %4s or %8s", i, unsigned, String.format("%8s", Integer.toBinaryString(unsigned)).replace(" ", "0")));
		}
	}

	public byte[] toByteArray() {
		// sync -> sert a eviter d'écrire et de copier en meme temps
		synchronized (bytes) {
			byte[] array = new byte[bytes.size()];

			for (int i = 0; i < array.length; i++) {
				array[i] = bytes.get(i);
			}

			return array;
		}
	}

	public int size() {
		return bytes.size();
	}

	public void writeBuffer(DataBuffer other) {
		bytes.addAll(other.bytes);
	}

	public void reset() {
		index = 0;
		bytes.clear();
	}

}