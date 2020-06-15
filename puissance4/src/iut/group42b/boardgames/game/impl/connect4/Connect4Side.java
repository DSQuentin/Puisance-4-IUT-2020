package iut.group42b.boardgames.game.impl.connect4;

public enum Connect4Side {

	NONE('.'),
	RED('X'),
	YELLOW('O');

	private final char letter;

	private Connect4Side(char letter) {
		this.letter = letter;
	}

	public char getLetter() {
		return letter;
	}

	public static Connect4Side fromLetter(char letter) {
		for(Connect4Side side : values()) {
			if (side.getLetter() == letter) {
				return side;
			}
		}

		throw new IllegalArgumentException("no connect 4 side for letter: " + letter);
	}

}