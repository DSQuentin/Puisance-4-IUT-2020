package iut.group42b.boardgames.client.ui.page.home;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.game.IGame;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


public class HomeView extends AbstractView {

	/* UI */
	private final ListView<IGame> gamesListView;
	private final ImageView profileImageView;
	private final Button logoutButton;
	private final Button toSocialButton;
	private final TextField searchTextField;
	private final Button toInvitationButton;


	/* Constructor */
	public HomeView() {
		super();

		this.gamesListView = (ListView) this.findById("home-listview-games");
		this.profileImageView = (ImageView) this.findById("home-imageview-profile");
		this.logoutButton = (Button) this.findById("home-button-tologout");
		this.toSocialButton = (Button) this.findById("home-button-social");
		this.searchTextField = (TextField) this.findById("home-search-game");
		this.toInvitationButton = (Button) this.findById("home-invitation-button");


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
		return this.gamesListView;
	}

	public TextField getSearchTextField() {
		return this.searchTextField;
	}

	public ImageView getProfileImageView() {
		return this.profileImageView;
	}

	public Button getLogoutButton() {
		return this.logoutButton;
	}

	public Button getToSocialButton() {
		return this.toSocialButton;
	}

	public Button getToInvitationButton() {
		return this.toInvitationButton;
	}
}