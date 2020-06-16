package iut.group42b.boardgames.client.ui.list.friend;

import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.client.ui.page.social.SocialController;
import iut.group42b.boardgames.social.model.UserProfile;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class MessageFriendListViewCellController implements IListViewCellController<UserProfile> {

	/* Variables */
	private final SocialController socialController;

	/* Constructor */
	public MessageFriendListViewCellController(SocialController socialController) {
		this.socialController = socialController;
	}

	@Override
	public void attachView(AbstractViewCell<UserProfile> view) {
		view.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			UserProfile targetUserProfile = (UserProfile) view.getUserData();

			if (targetUserProfile != null) {
				this.socialController.requestMessageList(targetUserProfile);
			}
		});
	}

	@Override
	public void updateItem(AbstractViewCell<UserProfile> cellView, UserProfile item) {
		MessageFriendListViewCellView friendCellView = (MessageFriendListViewCellView) cellView;

		friendCellView.getPictureImageView().setImage(new Image(item.getImageUrl(), true));
		friendCellView.getNameText().setText(item.getUsername());
	}

	@Override
	public AbstractViewCell<UserProfile> createView() {
		return new MessageFriendListViewCellView(this);
	}

	@Override
	public void handle(ActionEvent event) {
		// Unused
	}

}