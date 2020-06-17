package iut.group42b.boardgames.client.ui.page.profile.own;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.helper.NoSelectionModel;
import iut.group42b.boardgames.client.ui.list.game.GameListViewCellController;
import iut.group42b.boardgames.client.ui.list.gamehistory.GameHistoryListCellController;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.client.ui.page.user.settings.UserSettingsView;
import iut.group42b.boardgames.game.GameRegistry;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.packet.PlayerJoinPacket;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.social.model.gamehistory.GameHistoryItem;
import iut.group42b.boardgames.social.packet.history.GameListHistoryPacket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class OwnController implements IController, INetworkHandler {

	/* Controllers */
	private GameHistoryListCellController gameHistoryListViewCellController;

	private OwnView view;

	private ObservableList<GameHistoryItem> gameHistoryItemsObservableList;


	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getToLogOutButton()) {
			UserInterface.get().set(new LogoutView());
		} else if (event.getSource() == this.view.getToSettingsButton()) {
			UserInterface.get().set(new UserSettingsView());
		}
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof OwnView)) {
			throw new IllegalArgumentException();
		}

		this.view = (OwnView) view;

		this.gameHistoryListViewCellController = new GameHistoryListCellController();

		UserProfile userProfile = NetworkInterface.get().getSocketHandler().getUserProfile();

		UserProfile targetUserProfile = this.view.getUserprofile();


		 this.gameHistoryItemsObservableList = FXCollections.observableArrayList();


		this.view.getUsernameText().setText(targetUserProfile.getUsername());
		this.view.getProfileImageView().setImage(new Image(userProfile.getImageUrl(), true));
		this.view.getProfileImageOnProfile().setImage(new Image(targetUserProfile.getImageUrl(), true));
		this.view.getToLogOutButton().setOnAction(this);

		this.view.getToSettingsButton().setVisible(targetUserProfile.getId() == userProfile.getId());

		this.view.getToSettingsButton().setOnAction(this);

		this.view.getLogoImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new HomeView());
		});
		this.view.getProfileImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new OwnView(NetworkInterface.get().getSocketHandler().getUserProfile()));
		});

		this.view.getWinCircle().setRingWidth(10);
		this.view.getDefeatCircle().setRingWidth(10);

		this.view.getGameHistory().setCellFactory(this.gameHistoryListViewCellController.cellFactory());
		this.view.getGameHistory().setItems(gameHistoryItemsObservableList);
		this.view.getGameHistory().setSelectionModel(new NoSelectionModel<>());

		NetworkInterface.get().getSocketHandler().queue(new GameListHistoryPacket(this.view.getUserprofile().getId()));

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
		if (packet instanceof GameListHistoryPacket) {
			GameListHistoryPacket gameListHistoryPacket = (GameListHistoryPacket) packet;

			if (gameListHistoryPacket.getUserId() == this.view.getUserprofile().getId()) {
				this.gameHistoryItemsObservableList.clear();
				this.gameHistoryItemsObservableList.addAll(gameListHistoryPacket.getGameListHistory());
			}
		}
	}
}
