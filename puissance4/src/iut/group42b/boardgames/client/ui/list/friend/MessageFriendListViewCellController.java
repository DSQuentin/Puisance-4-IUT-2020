package iut.group42b.boardgames.client.ui.list.friend;

import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.client.ui.page.social.SocialController;
import iut.group42b.boardgames.social.model.aware.ReadAwareUserProfile;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class MessageFriendListViewCellController implements IListViewCellController<ReadAwareUserProfile> {

	/* Variables */
	private final SocialController socialController;

	/* Constructor */
	public MessageFriendListViewCellController(SocialController socialController) {
		this.socialController = socialController;
	}

	@Override
	public void attachView(AbstractViewCell<ReadAwareUserProfile> view) {
		view.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
			ReadAwareUserProfile targetUserProfile = (ReadAwareUserProfile) view.getUserData();

			if (targetUserProfile != null) {
				this.socialController.requestMessageList(targetUserProfile.getUserProfile());
			}
		});
	}

	@Override
	public void updateItem(AbstractViewCell<ReadAwareUserProfile> cellView, ReadAwareUserProfile item) {
		MessageFriendListViewCellView friendCellView = (MessageFriendListViewCellView) cellView;

		friendCellView.getPictureImageView().setImage(new Image(item.getUserProfile().getImageUrl(), true));
		friendCellView.getNameText().setText(item.getUserProfile().getUsername() + " -- " + item.notReadProperty().get() + " not read");
	}

	@Override
	public AbstractViewCell<ReadAwareUserProfile> createView() {
		return new MessageFriendListViewCellView(this);
	}

	@Override
	public void handle(ActionEvent event) {
		// Unused
	}

}