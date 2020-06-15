package iut.group42b.boardgames.game.impl.connect4.ui;

import iut.group42b.boardgames.client.ui.component.ResizableCanvas;
import iut.group42b.boardgames.game.impl.connect4.Connect4Game;
import iut.group42b.boardgames.game.impl.connect4.Connect4Side;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class Connect4GridCanvas extends ResizableCanvas {

	/* Constants */
	public static final Color SELECTOR_COLOR = Color.GREEN.brighter().brighter().brighter();
	public static final Color BORDER_TOKEN = Color.BLUE.darker();

	/* Variables */
	private final Map<Ellipse2D, TokenCoordinate> collisionsBox;
	private Connect4Side[][] grid;
	private Point2D lastMousePosition;
	private OnTokenClick tokenClickCallback;
	private boolean helper;

	private void onMouseEvent(MouseEvent event) {
		lastMousePosition = new Point2D.Double(event.getX(), event.getY());

		if (MouseEvent.MOUSE_CLICKED.equals(event.getEventType())) {
			for (Map.Entry<Ellipse2D, TokenCoordinate> entry : collisionsBox.entrySet()) {
				Ellipse2D tokenCollisionBox = entry.getKey();
				TokenCoordinate tokenCoordinate = entry.getValue();

				if (tokenCollisionBox.contains(lastMousePosition)) {
					System.out.println(tokenCoordinate);

					for (int y = grid.length - 1; y >= 0; y--) {
						if (grid[y][tokenCoordinate.x] == Connect4Side.NONE) {
							if (tokenClickCallback != null) {
								tokenClickCallback.onClick(this, tokenCoordinate.x, y);
							}

							break;
						}
					}

					break;
				}
			}
		}

		redraw();
	}

	public Connect4GridCanvas() {
		super();

		this.grid = createGrid();
		this.collisionsBox = new HashMap<>();

		addEventHandler(MouseEvent.MOUSE_MOVED, this::onMouseEvent);
		addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseEvent);

		/*new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				onMouseEvent(event);
			}
		}*/
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

		double offsetX = 0;
		double offsetY = 0;

		if (width != getWidth()) {
			offsetX = Math.abs(getWidth() - width) / 2;
		}

		if (height != getHeight()) {
			offsetY = Math.abs(getHeight() - height) / 2;
		}

		collisionsBox.clear();

		boolean arrowDrawn = false;

		for (int y = 0; y < Connect4Game.NUMBER_OF_ROWS; y++) {
			Connect4Side[] line = grid[y];

			for (int x = 0; x < Connect4Game.NUMBER_OF_COLUMNS; x++) {
				Connect4Side at = line[x];

				ctx.setFill(Color.WHITE);
				if (at == Connect4Side.RED) {
					ctx.setFill(Color.RED);
				} else if (at == Connect4Side.YELLOW) {
					ctx.setFill(Color.YELLOW);
				}

				double topLeftX = x * availableWidth;
				double topLeftY = y * availableHeight;

				double absoluteX = offsetX + topLeftX + spacingX;
				double absoluteY = offsetY + topLeftY + spacingY + arrowZoneHeight;
				double absoluteWidth = availableWidth - (spacingX * 2);
				double absoluteHeight = availableHeight - (spacingY * 2);

				Ellipse2D collisionBox = new Ellipse2D.Double(absoluteX, absoluteY, absoluteWidth, absoluteHeight);

				boolean canPlaceToken = false;
				if (helper
						&& lastMousePosition != null
						&& lastMousePosition.getX() >= collisionBox.getMinX()
						&& lastMousePosition.getX() <= collisionBox.getMaxX()
						&& (y + 1 != grid.length && grid[y + 1][x] != Connect4Side.NONE) // TODO Make it work for column that don't have any token in the lower row
						&& at == Connect4Side.NONE) {
					ctx.setFill(SELECTOR_COLOR);

					canPlaceToken = true;
				}

				collisionsBox.put(collisionBox, new TokenCoordinate(x, y));

				ctx.fillOval(absoluteX, absoluteY, absoluteWidth, absoluteHeight);

				if (!arrowDrawn) {
					double arrowX = offsetX + (availableWidth * (x + 0.5));

					ctx.setStroke(Color.BLACK);
					if (canPlaceToken) { // TODO Make it work for every lines
						ctx.setStroke(Color.WHITE);
					}

					ctx.strokeLine(arrowX, 0, arrowX, arrowZoneHeight);
				}

				ctx.save();
				ctx.setStroke(BORDER_TOKEN);
				ctx.setLineWidth(3);
				ctx.strokeOval(absoluteX, absoluteY, absoluteWidth, absoluteHeight);
				ctx.restore();
			}

			arrowDrawn = true;
		}
	}


	public Connect4Side[][] createGrid() {
		Connect4Side x = Connect4Side.NONE;

		return new Connect4Side[][]{
				{x, x, x, x, x, x, x},
				{x, x, x, x, x, x, x},
				{x, x, x, x, x, x, x},
				{x, x, x, x, x, x, x},
				{x, x, x, x, x, x, x},
				{x, x, x, x, x, x, x}
		};
	}

	public void updateLocalGrid(Connect4Side[][] grid) {
		this.grid = grid;

		redraw();
	}

	public void setHelperEnabled(boolean enabledState) {
		this.helper = enabledState;
	}

	public void setTokenClickCallback(OnTokenClick tokenClickCallback) {
		this.tokenClickCallback = tokenClickCallback;
	}

	public interface OnTokenClick {

		void onClick(Connect4GridCanvas canvas, int x, int y);

	}

}