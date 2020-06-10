package iut.group42b.boardgames.game.impl.connect4.ui;

import iut.group42b.boardgames.client.ui.component.ResizableCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class Connect4GridCanvas extends ResizableCanvas {

	public static final int RED = 1;
	public static final int YELLOW = 2;
	private Point2D lastMousePosition;

	// TODO create dedicated function
	private int[][] grid = {
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0},
			{0, 0, 1, 0, 0, 0, 0},
			{0, 0, 2, 0, 0, 0, 0},
			{0, 1, 1, 2, 0, 0, 0},
			{1, 2, 1, 1, 2, 0, 0}
	};
	private Map<Ellipse2D, TokenBox> collisionsBox = new HashMap<>();

	public Connect4GridCanvas() {
		addEventHandler(MouseEvent.MOUSE_MOVED, (event) -> {
			double mouseX = event.getX();
			double mouseY = event.getY();

			lastMousePosition = new Point2D.Double(mouseX, mouseY);

			redraw();
		});

		addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			double mouseX = event.getX();
			double mouseY = event.getY();

			System.out.println(Connect4GridCanvas.this.getWidth() + " " + Connect4GridCanvas.this.getHeight());

			lastMousePosition = new Point2D.Double(mouseX, mouseY);

			for (Map.Entry<Ellipse2D, TokenBox> entry : collisionsBox.entrySet()) {
				Ellipse2D tokenCollisionBox = entry.getKey();
				TokenBox tokenBox = entry.getValue();

				if (tokenCollisionBox.contains(lastMousePosition)) {
					System.out.println(tokenBox);

					for (int y = grid.length - 1; y >= 0; y--) {
						if (grid[y][tokenBox.x] == 0) {
							grid[y][tokenBox.x] = 2;
							break;
						}
					}

					break;
				}
			}

			redraw();
		});
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(Color.BLUE.brighter());
		ctx.fillRect(0, 0, getWidth(), getHeight());

		double width = Math.min(getWidth(), getHeight());
		double height = width;


		double availableWidth = width / 7;
		double availableHeight = height / (6 + 1); // One row for the arrows

		double arrowZoneHeight = availableHeight;
		double gridZoneHeight = height - availableHeight;

		double spacingX = width * 0.01;
		double spacingY = gridZoneHeight * 0.01;

		double topX = 0; // TODO Refactor in 'offsetX'
		double topY = 0;

		if (width != getWidth()) {
			topX = Math.abs(getWidth() - width) / 2;
		}

		if (height != getHeight()) {
			topY = Math.abs(getHeight() - height) / 2;
		}

		collisionsBox.clear();

		boolean arrowDrawn = false;

		for (int y = 0; y < 6; y++) { // TODO Use constants
			int[] line = grid[y];

			for (int x = 0; x < 7; x++) {
				int at = line[x];

				ctx.setFill(Color.WHITE);
				if (at == RED) {         // TODO Use enums (side)
					ctx.setFill(Color.RED);
				} else if (at == YELLOW) {
					ctx.setFill(Color.YELLOW);
				}

				double topLeftX = x * availableWidth;
				double topLeftY = y * availableHeight;

				double absoluteX = topX + topLeftX + spacingX;
				double absoluteY = topY + topLeftY + spacingY + arrowZoneHeight;
				double absoluteWidth = availableWidth - (spacingX * 2);
				double absoluteHeight = availableHeight - (spacingY * 2);

				Ellipse2D collisionBox = new Ellipse2D.Double(absoluteX, absoluteY, absoluteWidth, absoluteHeight);

				boolean canPlaceToken = false;
				if (lastMousePosition != null
						&& lastMousePosition.getX() >= collisionBox.getMinX()
						&& lastMousePosition.getX() <= collisionBox.getMaxX()
						&& (y + 1 != grid.length && grid[y + 1][x] != 0) // TODO Make it work for column that don't have any token in the lower row
						&& at == 0) {
					ctx.setFill(Color.GREEN.brighter().brighter().brighter()); // TODO Make colors as constants

					canPlaceToken = true;
				}

				collisionsBox.put(collisionBox, new TokenBox(x, y));

				ctx.fillOval(absoluteX, absoluteY, absoluteWidth, absoluteHeight);

				if (!arrowDrawn) {
					double arrowX = topX + (availableWidth * (x + 0.5));

					ctx.setStroke(Color.BLACK);
					if (canPlaceToken) { // TODO Make it work for every lines
						ctx.setStroke(Color.WHITE);
					}

					ctx.strokeLine(arrowX, 0, arrowX, arrowZoneHeight);
				}

				ctx.save();
				ctx.setStroke(Color.BLUE.darker());
				ctx.setLineWidth(3);
				ctx.strokeOval(absoluteX, absoluteY, absoluteWidth, absoluteHeight);
				ctx.restore();
			}

			arrowDrawn = true;
		}
	}


	class TokenBox {

		int x, y;

		public TokenBox(int x, int y) {
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

}