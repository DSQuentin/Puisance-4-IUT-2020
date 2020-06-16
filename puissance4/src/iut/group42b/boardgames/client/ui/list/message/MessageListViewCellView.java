package iut.group42b.boardgames.client.ui.list.message;

import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.game.IGame;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MessageListViewCellView extends AbstractViewCell<IGame> {

	/* UI */
	private final Button actionButton;
	private final Label tagsLabel;
	private final Label gameTitleLabel;
	private final ImageView coverImageView;

	/* Constructor */
	protected MessageListViewCellView(IListViewCellController<IGame> partController) {
		super(partController);

		this.actionButton = (Button) this.findById("home-button-action");
		this.tagsLabel = (Label) this.findById("home-label-tags");
		this.gameTitleLabel = (Label) this.findById("home-label-game-title");
		this.coverImageView = (ImageView) this.findById("home-imageview-game-logo");
	}

	@Override
	public String getViewPath() {
		return "home-game-cell.fxml";
	}

	public Button getActionButton() {
		return this.actionButton;
	}

	public Label getTagsLabel() {
		return this.tagsLabel;
	}

	public ImageView getCoverImageView() {
		return this.coverImageView;
	}

	public Label getGameTitleLabel() {
		return this.gameTitleLabel;
	}

}