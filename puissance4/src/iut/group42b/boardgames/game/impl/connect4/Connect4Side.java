package iut.group42b.boardgames.game.impl.connect4;

public enum Connect4Side {

	NONE('.'),
	RED('X'),
	YELLOW('O');

	private final char letter;

	/**
	 * Constructor Connect4Side
	 *
	 * @param letter '.', 'O','X'
	 */
	Connect4Side(char letter) {
		this.letter = letter;
	}

	/**
	 * Get the letter.
	 *
	 * @return '.', 'O','X'
	 */
	public char getLetter() {
		return this.letter;
	}

	/**
	 * Get the correct instance from enum.
	 *
	 * @param letter
	 * @return Connect4Side.
	 */
	public static Connect4Side fromLetter(char letter) {
		for (Connect4Side side : values()) {
			if (side.getLetter() == letter) {
				return side;
			}
		}

		throw new IllegalArgumentException("no connect 4 side for letter: " + letter);
	}

}