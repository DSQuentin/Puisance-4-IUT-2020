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
	private final Button adminButton;


	/* Constructor */
	public HomeView() {
		super();

		this.gamesListView = this.findById("home-listview-games");
		this.profileImageView =  this.findById("home-imageview-profile");
		this.logoutButton =  this.findById("home-button-tologout");
		this.toSocialButton =  this.findById("home-button-social");
		this.searchTextField =  this.findById("home-search-game");
		this.toInvitationButton =  this.findById("home-invitation-button");
		this.adminButton = this.findById("home-admin-button");


	}

	@Override
	public String getViewPath() {
		return "home.fxml";
	}

	@Override
	public IController createController() {
		return new HomeController();
	}

	public Button getAdminButton() {return this.adminButton;}

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