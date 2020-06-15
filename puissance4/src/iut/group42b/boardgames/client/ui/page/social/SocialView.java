package iut.group42b.boardgames.client.ui.page.social;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class SocialView extends AbstractView {

	/* UI */

	private final TextField messageInputTextFeild;
	private final Button sendMessageButton;
	private final Button fightButton;
	private final ListView gamesListView;



	/* Variables */

	/* Constructor */
	public SocialView() {
		super();
		this.messageInputTextFeild = (TextField) findById("home-textfield-message-input");
		this.sendMessageButton = (Button) findById("home-button-send-message");
		this.fightButton = (Button) findById("home-button-fight");
		this.gamesListView = (ListView) findById("home-listview-games");


	}

	@Override
	public String getViewPath() {
		return "social.fxml";
	}

	@Override
	public IController createController() {
		return new SocialController();
	}
}
