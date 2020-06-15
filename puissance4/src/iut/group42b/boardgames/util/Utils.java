package iut.group42b.boardgames.util;

import java.io.Closeable;
import java.io.IOException;

public class Utils {

	public static void slientClose(Closeable closeable) {
		try {
			closeable.close();
		} catch (IOException ignored) {
		}
	}

}