package iut.group42b.boardgames.client.ui.list.friend;

import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.social.model.UserProfile;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class MessageFriendListViewCellView extends AbstractViewCell<UserProfile> {

	/* UI */
	private final ImageView pictureImageView;
	private final Text nameText;

	/* Constructor */
	protected MessageFriendListViewCellView(IListViewCellController<UserProfile> partController) {
		super(partController);

		this.pictureImageView = (ImageView) this.findById("social-friend-cell-image-picture");
		this.nameText = (Text) this.findById("social-friend-cell-text-name");
	}

	@Override
	public String getViewPath() {
		return "social-friends-cell.fxml";
	}

	public ImageView getPictureImageView() {
		return this.pictureImageView;
	}

	public Text getNameText() {
		return this.nameText;
	}

}