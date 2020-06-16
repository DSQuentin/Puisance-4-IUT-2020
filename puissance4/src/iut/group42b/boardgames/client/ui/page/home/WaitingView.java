package iut.group42b.boardgames.client.ui.page.home;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.game.IGame;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


public class WaitingView extends AbstractView {

	/* UI */

	private final Button cancelButton;
	private final ImageView logoGameImageView;
	// private Text textButton;

	/* Variables */
	private final IGame game;

	/* Constructor */
	public WaitingView(IGame game) {
		super();
		this.game = game;
		this.cancelButton = (Button) this.findById("waiting-player-button-cancel");
		this.logoGameImageView = (ImageView) this.findById("waiting-player-imageview-logo");

	}


	@Override
	public String getViewPath() {
		return "waiting-player.fxml";
	}

	@Override
	public IController createController() {
		return new WaitingController();
	}

	public IGame getGame() {
		return this.game;
	}

	public Button getCancelButton() {
		return this.cancelButton;
	}

	public ImageView getLogoGameImageView() {
		return this.logoGameImageView;
	}
}
