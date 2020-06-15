package iut.group42b.boardgames.client.ui.page.home;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.game.IGame;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;


public class HomeView extends AbstractView {

	/* UI */
	private final ListView<IGame> gamesListView;
	private final ImageView profileImageView;
	private final Button logoutButton;
	private final Button toSocialButton;


	/* Constructor */
	public HomeView() {
		super();

		this.gamesListView = (ListView) findById("home-listview-games");
		this.profileImageView = (ImageView) findById("home-imageview-profile");
		this.logoutButton = (Button) findById("home-button-tologout");
		this.toSocialButton = (Button) findById("home-social-button");


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

	public ImageView getProfileImageView() {
		return profileImageView;
	}

	public Button getLogoutButton() {
		return logoutButton;
	}

	public Button getToSocialButton() {
		return toSocialButton;
	}
}