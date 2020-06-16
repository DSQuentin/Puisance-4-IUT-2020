package iut.group42b.boardgames.util;

import java.io.Closeable;
import java.io.IOException;

public class Utils {

	/**
	 * Close a socket while ignoring the potential IOException.
	 *
	 * @param closeable
	 */
	public static void slientClose(Closeable closeable) {
		try {
			closeable.close();
		} catch (IOException ignored) {
		}
	}

	public static boolean areEqualAndValid(String a, String b) {
		return a != null && !a.isEmpty() && a.equals(b);
	}

}