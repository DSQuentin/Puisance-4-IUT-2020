package iut.group42b.boardgames.client.ui.list.game;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.client.ui.page.admin.AdminGameSettingsView;
import iut.group42b.boardgames.client.ui.page.home.WaitingView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.game.GameAvailability;
import iut.group42b.boardgames.game.GameRegistry;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.packet.matchmaking.MatchmakingJoinPacket;
import javafx.event.ActionEvent;
import javafx.scene.Parent;

import static iut.group42b.boardgames.game.GameAvailability.PLAYABLE;

public class GameListViewCellController implements IListViewCellController<IGame> {

	/* Variables */
	private GameListViewCellView view;

	@Override
	public void handle(ActionEvent event) {
		// Unused
	}

	@Override
	public void attachView(AbstractViewCell<IGame> view) {
		GameListViewCellView cellView = (GameListViewCellView) view;
		((GameListViewCellView) view).getSettingsButton().setDisable(true);

		cellView.getActionButton().setOnAction((event) -> {
			IGame game = (IGame) ((Parent) event.getSource()).getUserData();

			// openning the waiting box
			UserInterface.get().openDialog(new WaitingView(game));
			NetworkInterface.get().getSocketHandler().queue(new MatchmakingJoinPacket(GameRegistry.get().getIdFor(game)));
		});
		cellView.getSettingsButton().setOnAction((event) -> {
			IGame game = view.getCurrentItem();
			System.out.println("TEST TEST "+game);
			UserInterface.get().set(new AdminGameSettingsView(game));
		});
		if (NetworkInterface.get().getSocketHandler().getUserProfile().isAdmin()) {
			((GameListViewCellView) view).getSettingsButton().setDisable(false);
			cellView.getSettingsButton().setStyle("-fx-opacity: 1");
		}
	}

	@Override
	public void updateItem(AbstractViewCell<IGame> cellView, IGame item) {
		GameListViewCellView gameCellView = (GameListViewCellView) cellView;

		gameCellView.getGameTitleLabel().setText(item.getName());

		gameCellView.getTagsLabel().setText(String.join(", ", item.getTags()));

		gameCellView.getCoverImageView().setImage(Resource.loadImage(item.picturePath()));
		gameCellView.getCoverImageView().setFitWidth(70.0);
		gameCellView.getCoverImageView().setPreserveRatio(true);

		GameAvailability gameState = item.getAvailability();

		gameCellView.getActionButton().setText(Messages.GAME_AVAILABILIY_STATE.use(gameState));

		if (gameState != PLAYABLE) {
			gameCellView.getActionButton().setDisable(true);
		}

		gameCellView.getActionButton().setUserData(item);
	}

	// TODO : https://stackoverflow.com/questions/20621752/javafx-make-listview-not-selectable-via-mouse/46186195#46186195 fix this shit

	@Override
	public AbstractViewCell<IGame> createView() {
		return new GameListViewCellView(this);
	}


}