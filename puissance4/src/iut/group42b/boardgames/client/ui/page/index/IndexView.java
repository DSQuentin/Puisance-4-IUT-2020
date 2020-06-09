package iut.group42b.boardgames.client.ui.page.index;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;

public class IndexView extends AbstractView {

	/* UI */
	private final Button PlayNowButton;
	private final Button toRegisterButton;
	private final Button ToLoginButton;

	/* Constructor

	public IndexView() {
		this(null);
	}	*/

	/* Constructor */
	public IndexView() {
		super();
		this.PlayNowButton = (Button) findById("index-button-play-now");
		this.toRegisterButton = (Button) findById("index-button-register");
		this.ToLoginButton = (Button) findById("index-button-login");
	}

	@Override
	public String getViewPath() {
		return "index.fxml";
	}


	@Override
	public IController createController() {
		return new IndexController();
	}

	public Button getPlayNowButton() {
		return PlayNowButton;
	}

	public Button getToRegisterButton() {
		return toRegisterButton;
	}

	public Button getToLoginButton() {
		return ToLoginButton;
	}

}