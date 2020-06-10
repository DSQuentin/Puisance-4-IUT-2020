package iut.group42b.boardgames.client.ui.list.game;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.client.ui.page.home.WaitingView;
import iut.group42b.boardgames.client.ui.page.index.IndexView;
import iut.group42b.boardgames.game.GameAvailability;
import iut.group42b.boardgames.game.IGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static iut.group42b.boardgames.game.GameAvailability.*;

public class GameListViewCellController implements IListViewCellController<IGame> {

	/* Variables */
	private GameListViewCellView view;

	@Override
	public void handle(ActionEvent event) {
	}

	@Override
	public void attachView(AbstractViewCell<IGame> view) {
		GameListViewCellView cellView = (GameListViewCellView) view;

		cellView.getActionButton().setOnAction((event) -> {
			IGame game = (IGame) ((Parent) event.getSource()).getUserData();

			System.out.println(game);
			handle(event);
			UserInterface.get().openDialog(new WaitingView(game));
		});
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