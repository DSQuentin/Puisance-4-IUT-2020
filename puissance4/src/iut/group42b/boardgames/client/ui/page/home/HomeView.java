package iut.group42b.boardgames.client.ui.page.home;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.game.IGame;
import javafx.scene.control.ListView;


public class HomeView extends AbstractView {

	/* UI */
	private final ListView<IGame> gamesListView;


	public HomeView() {
		super();

		this.gamesListView = (ListView) findById("home-listview-games");

	}

	@Override
	public String getViewPath() {
		return "home.fxml";
	}

	@Override
	public IController createController() {
		return new HomeController();
	}

	public ListView<IGame> getGamesListView() {
		return gamesListView;
	}

}