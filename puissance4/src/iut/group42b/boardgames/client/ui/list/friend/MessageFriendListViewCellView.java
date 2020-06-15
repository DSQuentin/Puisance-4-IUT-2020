package iut.group42b.boardgames.client.ui.list.friend;

import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.social.model.UserProfile;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MessageFriendListViewCellView extends AbstractViewCell<UserProfile> {

	/* UI */
	private Button actionButton;
	private Label tagsLabel;
	private Label gameTitleLabel;
	private ImageView coverImageView;

	/* Constructor */
	protected MessageFriendListViewCellView(IListViewCellController<UserProfile> partController) {
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
