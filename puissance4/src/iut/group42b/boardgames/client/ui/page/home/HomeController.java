package iut.group42b.boardgames.client.ui.page.home;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.list.game.GameListViewCellController;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.admin.AdminDashBoardView;
import iut.group42b.boardgames.client.ui.page.invitation.InvitationView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.client.ui.page.profile.own.OwnView;
import iut.group42b.boardgames.client.ui.page.social.SocialView;
import iut.group42b.boardgames.game.GameRegistry;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.packet.PlayerJoinPacket;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.util.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class HomeController implements IController, INetworkHandler {

	/* Logger */
	private final static Logger LOGGER = new Logger(HomeController.class);

	/* Controllers */
	private GameListViewCellController gameListViewCellController;
	private FilteredList<IGame> gamesFilterList; //https://stackoverflow.com/questions/28448851/how-to-use-javafx-filteredlist-in-a-listview

	/* Variables */
	private HomeView view;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getLogoutButton()) {
			UserInterface.get().set(new LogoutView());
		} else if (event.getSource() == this.view.getToSocialButton()) {
			UserInterface.get().set(new SocialView());
		} else if (event.getSource() == this.view.getToInvitationButton()) {
			UserInterface.get().set(new InvitationView());
		} else if (event.getSource() == this.view.getAdminButton()) {
			UserInterface.get().set(new AdminDashBoardView());
		}
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof HomeView)) {
			throw new IllegalArgumentException();
		}

		this.view = (HomeView) view;

		ObservableList<IGame> gameObservableList = FXCollections.observableArrayList();
		gameObservableList.addAll(GameRegistry.get().all());

		this.gameListViewCellController = new GameListViewCellController();
		this.gamesFilterList = new FilteredList<>(gameObservableList, (item) -> true);

		this.view.getGamesListView().setItems(this.gamesFilterList);
		this.view.getLogoutButton().setOnAction(this);
		this.view.getToSocialButton().setOnAction(this);
		this.view.getToInvitationButton().setOnAction(this);
		this.view.getAdminButton().setOnAction(this);
		this.view.getGamesListView().setCellFactory(this.gameListViewCellController.cellFactory());
		this.view.getProfileImageView().setImage(new Image(NetworkInterface.get().getSocketHandler().getUserProfile().getImageUrl(), true));

		if(NetworkInterface.get().getSocketHandler().getUserProfile().isAdmin()){
			this.view.getAdminButton().setVisible(true);
			this.view.getAdminButton().setStyle("-fx-opacity: 1");
		}
		this.view.getSearchTextField().textProperty().addListener((observable) -> {
			String filter = this.view.getSearchTextField().getText();

			if (this.gamesFilterList == null) {
				return;
			}

			if (filter == null || filter.length() == 0) {
				this.gamesFilterList.setPredicate((game) -> true);
			} else {
				this.gamesFilterList.setPredicate((game) -> game.getName().toLowerCase().contains(filter.toLowerCase()));
			}
		});


		this.view.getProfileImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new OwnView());
		});

	}

	@Override
	public void onMount() {
		NetworkInterface.get().getSocketHandler().subscribe(this);
	}

	@Override
	public void onUnmount() {
		NetworkInterface.get().getSocketHandler().unsubscribe(this);
	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {
		if (packet instanceof PlayerJoinPacket) {
			PlayerJoinPacket joinPacket = (PlayerJoinPacket) packet;

			IGame game = GameRegistry.get().getById(joinPacket.getGameId());
			if (game == null) {
				LOGGER.warning("Unknown game ID: " + joinPacket.getGameId());
				return;
			}

			UserInterface.get().set(game.createClientView());
		}
	}

}