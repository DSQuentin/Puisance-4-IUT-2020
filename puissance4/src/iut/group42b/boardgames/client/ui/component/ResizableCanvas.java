package iut.group42b.boardgames.client.ui.component;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class ResizableCanvas extends Canvas {

	/* Constructor */
	public ResizableCanvas() {
		super();

		widthProperty().addListener(event -> redraw());
		heightProperty().addListener(event -> redraw());
	}

	public void redraw() {
		draw(getGraphicsContext2D());
	}

	public abstract void draw(GraphicsContext ctx);

	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double prefWidth(double height) {
		return getWidth();
	}

	@Override
	public double prefHeight(double width) {
		return getHeight();
	}
}