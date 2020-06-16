package iut.group42b.boardgames.game.impl.connect4.ui;

class TokenCoordinate {

	/* Variables */
	int x, y;

	/**
	 * Constructor TokenCoordinate, is x,y coordinate of token in the grid.
	 *
	 * @param x An int.
	 * @param y An int.
	 */
	public TokenCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "TokenBox{" +
				"x=" + this.x +
				", y=" + this.y +
				'}';
	}
}