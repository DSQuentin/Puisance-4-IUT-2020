package iut.group42b.boardgames.game.impl.connect4.ui;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.layout.StackPane;

public class Connect4UIView extends AbstractView {

	/* UI */
	private final StackPane gridContainerStackPane;

	/* Constructor */
	public Connect4UIView() {
		super();

		this.gridContainerStackPane = (StackPane) findById("connect4-stackpane-container");
	}

	@Override
	public String getViewPath() {
		return "connect4.fxml";
	}

	@Override
	public IController createController() {
		return new Connect4UIController();
	}

	public StackPane getGridContainerStackPane() {
		return gridContainerStackPane;
	}

}