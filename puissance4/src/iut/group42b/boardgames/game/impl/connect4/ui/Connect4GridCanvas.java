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

	/**
	 * Constructor Connect4GridCanvas
	 * <p>
	 * During the construction, a grid is created .
	 * It also add handler on canvas to detect mouse movement and click
	 * </p>
	 *
	 * @see Connect4GridCanvas#createGrid()
	 */
	public Connect4GridCanvas() {
		super();

		this.grid = this.createGrid();
		this.collisionsBox = new HashMap<>();

		this.addEventHandler(MouseEvent.MOUSE_MOVED, this::onMouseEvent); // for location selector
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, this::onMouseEvent); // for side

		/*
		new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				onMouseEvent(event);
			}
		}*/
	}

	/**
	 * @param event
	 */
	private void onMouseEvent(MouseEvent event) {
		this.lastMousePosition = new Point2D.Double(event.getX(), event.getY());

		if (MouseEvent.MOUSE_CLICKED.equals(event.getEventType())) {
			for (Map.Entry<Ellipse2D, TokenCoordinate> entry : this.collisionsBox.entrySet()) {
				Ellipse2D tokenCollisionBox = entry.getKey();
				TokenCoordinate tokenCoordinate = entry.getValue();

				if (tokenCollisionBox.contains(this.lastMousePosition)) {
					System.out.println(tokenCoordinate);

					for (int y = this.grid.length - 1; y >= 0; y--) {
						if (this.grid[y][tokenCoordinate.x] == Connect4Side.NONE) {
							if (this.tokenClickCallback != null) {
								this.tokenClickCallback.onClick(this, tokenCoordinate.x, y);
							}

							break;
						}
					}

					break;
				}
			}
		}

		this.redraw();
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(Color.BLUE.brighter());
		ctx.fillRect(0, 0, this.getWidth(), this.getHeight());

		double width = Math.min(this.getWidth(), this.getHeight());
		double height = width;

		double availableWidth = width / 7;
		double availableHeight = height / (6 + 1); // One row for the arrows

		double arrowZoneHeight = availableHeight;
		double gridZoneHeight = height - availableHeight;

		double spacingX = width * 0.01;
		double spacingY = gridZoneHeight * 0.01;

		double offsetX = 0;
		double offsetY = 0;

		if (width != this.getWidth()) {
			offsetX = Math.abs(this.getWidth() - width) / 2;
		}

		if (height != this.getHeight()) {
			offsetY = Math.abs(this.getHeight() - height) / 2;
		}

		this.collisionsBox.clear();

		int selectedGridX = -1;

		for (int y = 0; y < Connect4Game.NUMBER_OF_ROWS; y++) {
			Connect4Side[] line = this.grid[y];

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
				if (this.helper
						&& this.lastMousePosition != null
						&& this.lastMousePosition.getX() >= collisionBox.getMinX()
						&& this.lastMousePosition.getX() <= collisionBox.getMaxX()
						&& (y == this.grid.length - 1 || this.grid[y + 1][x] != Connect4Side.NONE)
						&& at == Connect4Side.NONE) {
					ctx.setFill(SELECTOR_COLOR);

					selectedGridX = x;
				}

				this.collisionsBox.put(collisionBox, new TokenCoordinate(x, y));

				ctx.fillOval(absoluteX, absoluteY, absoluteWidth, absoluteHeight);

				ctx.save();
				ctx.setStroke(BORDER_TOKEN);
				ctx.setLineWidth(3);
				ctx.strokeOval(absoluteX, absoluteY, absoluteWidth, absoluteHeight);
				ctx.restore();
			}
		}

		if (this.helper) {
			for (int x = 0; x < Connect4Game.NUMBER_OF_COLUMNS; x++) {
				double step = ((System.currentTimeMillis() % 1000) * 1.0d / 1000) - 0.5;

				ctx.setFill(BORDER_TOKEN);
				if (selectedGridX == x) {
					ctx.setFill(SELECTOR_COLOR);
				}

				double arrowCenterX = offsetX + (availableWidth * (x + 0.5));
				double arrowHalfWidth = availableWidth / 15;
				double arrowHalferWidth = arrowHalfWidth / 2;

				double stepOffset = arrowZoneHeight * 0.1 * step;

				double arrowTopY = offsetY + arrowZoneHeight * 0.2 + stepOffset;
				double arrowHeadY = offsetY + arrowZoneHeight * 0.55 + stepOffset;
				double arrowBottomY = offsetY + arrowZoneHeight * 0.8 + stepOffset;

				double[] xPoints = {
						arrowCenterX - arrowHalfWidth,
						arrowCenterX - arrowHalfWidth,
						arrowCenterX - arrowHalfWidth - arrowHalferWidth,
						arrowCenterX,
						arrowCenterX + arrowHalfWidth + arrowHalferWidth,
						arrowCenterX + arrowHalfWidth,
						arrowCenterX + arrowHalfWidth,
						arrowCenterX - arrowHalfWidth
				};

				double[] yPoints = {
						arrowTopY, // 1
						arrowHeadY,
						arrowHeadY,
						arrowBottomY,
						arrowHeadY,
						arrowHeadY,
						arrowTopY,
						arrowTopY, // 8
				};

				ctx.fillPolygon(xPoints, yPoints, xPoints.length);

				/*
				 *          8 TOP
				 *        1 + | + 7
				 *            |
				 *       3  2 |
				 *       +  + | +  +  HEAD
				 *            | 5   6
				 *          4 +
				 *          BOTTOM
				 */
			}
		}
	}


	/**
	 * Create the grid of connect 4. Each side is represented with '.' for None, 'X' for red side and 'O' for yellow.
	 *
	 * @return a 2 dimensional array, it look like matrice representation see in first semester in python.
	 */
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

	/**
	 * Replace the grid with the new grid and redraw the canvas
	 *
	 * @param grid a connect 4 game grid
	 * @see ResizableCanvas#redraw()
	 */
	public void updateLocalGrid(Connect4Side[][] grid) {
		this.grid = grid;

		this.redraw();
	}

	/**
	 * Enable or disable the location selector helper
	 *
	 * @param enabledState a boolean.
	 */
	public void setHelperEnabled(boolean enabledState) {
		boolean changed = this.helper != enabledState;

		this.helper = enabledState;

		if (changed) {
			this.redraw();
		}
	}

	/**
	 * Add handler on token
	 *
	 * @param tokenClickCallback
	 */
	public void setTokenClickCallback(OnTokenClick tokenClickCallback) {
		this.tokenClickCallback = tokenClickCallback;
	}

	/**
	 * Handler
	 */
	public interface OnTokenClick {

		void onClick(Connect4GridCanvas canvas, int x, int y);

	}

}