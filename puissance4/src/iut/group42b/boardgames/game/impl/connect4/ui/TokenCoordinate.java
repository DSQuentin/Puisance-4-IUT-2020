package iut.group42b.boardgames.game.impl.connect4.ui;

class TokenCoordinate {

	int x, y;

	public TokenCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "TokenBox{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}