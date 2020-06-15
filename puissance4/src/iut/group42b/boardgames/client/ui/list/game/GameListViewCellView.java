package iut.group42b.boardgames.client.ui.list.game;

import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.game.IGame;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class GameListViewCellView extends AbstractViewCell<IGame> {

	/* UI */
	private final Button actionButton;
	private final Label tagsLabel;
	private final Label gameTitleLabel;
	private final ImageView coverImageView;

	/* Constructor */
	protected GameListViewCellView(IListViewCellController<IGame> partController) {
		super(partController);

		this.actionButton = (Button) findById("home-button-action");
		this.tagsLabel = (Label) findById("home-label-tags");
		this.gameTitleLabel = (Label) findById("home-label-game-title");
		this.coverImageView = (ImageView) findById("home-imageview-game-logo");
	}

	@Override
	public String getViewPath() {
		return "home-game-cell.fxml";
	}

	public Button getActionButton() {
		return actionButton;
	}

	public Label getTagsLabel() {
		return tagsLabel;
	}

	public ImageView getCoverImageView() {
		return coverImageView;
	}

	public Label getGameTitleLabel() {
		return gameTitleLabel;
	}

}