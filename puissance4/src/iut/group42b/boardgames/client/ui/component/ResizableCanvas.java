package iut.group42b.boardgames.client.ui.component;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class ResizableCanvas extends Canvas {


	/***
	 * Constructor ResizableCanvas
	 */
	public ResizableCanvas() {
		super();

		this.widthProperty().addListener(event -> this.redraw());
		this.heightProperty().addListener(event -> this.redraw());
	}

	/**
	 * Redraw the canvas
	 */
	public void redraw() {
		this.draw(this.getGraphicsContext2D());
	}


	public abstract void draw(GraphicsContext ctx);

	@Override
	public boolean isResizable() {
		return true;
	}

	@Override
	public double prefWidth(double height) {
		return this.getWidth();
	}

	@Override
	public double prefHeight(double width) {
		return this.getHeight();
	}
}