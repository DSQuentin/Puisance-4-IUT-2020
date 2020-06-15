package iut.group42b.boardgames.client.ui.page.home;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.list.game.GameListViewCellController;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
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
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class HomeController implements IController, INetworkHandler {

	/* Logger */
	private final static Logger LOGGER = new Logger(HomeController.class);

	/* Controllers */
	private GameListViewCellController gameListViewCellController;

	/* Variables */
	private HomeView view;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == view.getLogoutButton()) {
			UserInterface.get().set(new LogoutView());
		} else if (event.getSource() == view.getToSocialButton()) {
			UserInterface.get().set(new SocialView());
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

		gameListViewCellController = new GameListViewCellController();

		this.view.getLogoutButton().setOnAction(this);
		this.view.getToSocialButton().setOnAction(this);
		this.view.getGamesListView().setItems(gameObservableList);
		this.view.getGamesListView().setCellFactory(gameListViewCellController.cellFactory());
		this.view.getProfileImageView().setImage(new Image(NetworkInterface.get().getSocketHandler().getUserProfile().getImageUrl(), true));

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