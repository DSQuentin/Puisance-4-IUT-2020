package iut.group42b.boardgames.client.ui.page.profile.own;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.helper.NoSelectionModel;
import iut.group42b.boardgames.client.ui.list.gamehistory.GameHistoryListCellController;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.client.ui.page.user.settings.UserSettingsView;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.social.model.gamehistory.GameHistoryItem;
import iut.group42b.boardgames.social.packet.friendship.FriendNumberPacket;
import iut.group42b.boardgames.social.packet.friendship.FriendNumberRequestPacket;
import iut.group42b.boardgames.social.packet.history.GameListHistoryPacket;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

		this.gameHistoryListViewCellController = new GameHistoryListCellController(this);

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
		this.view.getGameHistory().setItems(this.gameHistoryItemsObservableList);
		this.view.getGameHistory().setSelectionModel(new NoSelectionModel<>());

		NetworkInterface.get().getSocketHandler().queue(new GameListHistoryPacket(this.view.getUserprofile().getId()));
		NetworkInterface.get().getSocketHandler().queue(new FriendNumberRequestPacket(this.view.getUserprofile().getId()));

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

		if (packet instanceof FriendNumberPacket){
			FriendNumberPacket numberOfFriend = (FriendNumberPacket) packet;

			this.view.getNumberFriendsText().setText(numberOfFriend.getNumber() +"");

		}

		if (packet instanceof GameListHistoryPacket) {
			GameListHistoryPacket gameListHistoryPacket = (GameListHistoryPacket) packet;

			if (gameListHistoryPacket.getUserId() == this.view.getUserprofile().getId()) {
				this.view.getNumberGamesPlayedText().setText(String.valueOf(gameListHistoryPacket.getTotalPlayed()));

				this.gameHistoryItemsObservableList.clear();
				this.gameHistoryItemsObservableList.addAll(gameListHistoryPacket.getGameListHistory());
			}

			int win = 0;
			int defeat = 0;
			int maxScore = 0;
			List<Date> durations = new ArrayList<>();


			List<GameHistoryItem> games = gameListHistoryPacket.getGameListHistory();

			if (games != null) {
				for (GameHistoryItem game : games) {

					if (game.getIdUserWinner() == this.view.getUserprofile().getId()) {

						try {
							Date date = new SimpleDateFormat("hh:mm:ss").parse(game.getDuration());
							durations.add(date);
						} catch (Exception e) {
							e.printStackTrace();
						}
						win++;
						if (maxScore < game.getWinnerScore()) {
							maxScore = game.getWinnerScore();
						}
					} else {
						defeat++;
					}

				}

				long totalSeconds = 0L;
				for (Date date : durations) {
					totalSeconds += date.getTime() / 1000L;
				}

				if (!durations.isEmpty()) { // Avoid division / 0
					long averageSeconds = totalSeconds / durations.size();
					Date averageDate = new Date(averageSeconds * 1000L);

					SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
					String time = localDateFormat.format(averageDate);

					this.view.getTimeText().setText(time);
				}


				int winRatio = 0;
				int defeatRatio = 0;

				if (!games.isEmpty()){

					winRatio = (win * 100) / games.size();
					defeatRatio = (defeat * 100) / games.size();
				}

				int finalDefeatRatio = defeatRatio;
				int finalWinRatio = winRatio;
				Platform.runLater(() -> {
					this.view.getDefeatCircle().getStyleClass().add("circleindicator-container-defeat");
					this.view.getDefeatCircle().setProgress(finalDefeatRatio);
					this.view.getWinCircle().setProgress(finalWinRatio);

				});
				this.view.getNumberWinText().setText(String.valueOf(win));
				this.view.getScoreText().setText(String.valueOf(maxScore));


			} else {
				System.out.println("WARNING : empty history");
			}

		}
	}

	public UserProfile getUserProfile() {
		return this.view.getUserprofile();
	}
}
