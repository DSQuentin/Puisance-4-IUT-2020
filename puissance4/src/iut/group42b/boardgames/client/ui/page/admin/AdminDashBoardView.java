package iut.group42b.boardgames.client.ui.page.admin;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class AdminDashBoardView extends AbstractView {
	private final ImageView logoImageView;
	private final ImageView profileImageView;
	private final Button logOut;
	private final Text gamesPlayedGame1;
	private final Text gamesPlayedGame2;
	private final Text gamesPlayedGame3;
	private final Text topUser;
	private final Text topUserScore;
	private final ImageView imageGraph;
	private final Button manageButton;

	public AdminDashBoardView() {
		this.logoImageView = this.findById("logo");
		this.profileImageView = this.findById("admin-dashboard-imageview-profile");
		this.gamesPlayedGame1 = this.findById("admin-dashboard-games-played-game1");
		this.gamesPlayedGame2 = this.findById("admin-dashboard-games-played-game2");
		this.gamesPlayedGame3 = this.findById("admin-dashboard-games-played-game3");
		this.topUser = this.findById("admin-dashboard-top-user");
		this.topUserScore = this.findById("admin-dashboard-top-user-score");
		this.imageGraph = this.findById("admin-dashboard-imagegraph");
		this.manageButton = this.findById("admin-dashboard-manage-users");
		this.logOut = this.findById("admin-dashboard-logout");
	}

	public ImageView getProfileImageView() {
		return this.profileImageView;
	}

	public ImageView getLogoImageView() {
		return this.logoImageView;
	}

	public Button getLogOut() {
		return this.logOut;
	}

	public Button getManageButton() {
		return this.manageButton;
	}

	public Text getGamesPlayedGame1() {
		return this.gamesPlayedGame1;
	}

	public Text getGamesPlayedGame2() {
		return this.gamesPlayedGame2;
	}

	public Text getGamesPlayedGame3() {
		return this.gamesPlayedGame3;
	}

	public Text getTopUser() {
		return this.topUser;
	}

	public Text getTopUserScore() {
		return this.topUserScore;
	}

	public ImageView getImageGraph() {
		return this.imageGraph;
	}

	@Override
	public String getViewPath() {
		return "admin-dashboard.fxml";
	}

	@Override
	public IController createController() {
		return new AdminDashBoardController();
	}
}
