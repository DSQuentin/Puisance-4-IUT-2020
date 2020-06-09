package iut.group42b.boardgames.game.impl.connect4;

public enum Connect4Side {

	NONE('.'),
	RED('X'),
	YELLOW('O');

	private final char letter;

	private Connect4Side(char letter) {
		this.letter = letter;
	}

}