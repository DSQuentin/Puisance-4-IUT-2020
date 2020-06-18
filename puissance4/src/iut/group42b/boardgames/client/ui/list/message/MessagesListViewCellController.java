package iut.group42b.boardgames.client.ui.list.message;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.social.model.ExchangedMessage;
import iut.group42b.boardgames.social.model.UserProfile;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class MessagesListViewCellController implements IListViewCellController<ExchangedMessage> {

	/* Variables */
	private MessagesListViewCellView view;
	private UserProfile currentlyTalkingToUserProfile;

	public MessagesListViewCellController() {

	}

	@Override
	public void handle(ActionEvent event) {
		// Unused
	}

	@Override
	public void attachView(AbstractViewCell<ExchangedMessage> view) {
		MessagesListViewCellView cellView = (MessagesListViewCellView) view;


	}

	@Override
	public void updateItem(AbstractViewCell<ExchangedMessage> cellView, ExchangedMessage item) {
		MessagesListViewCellView gameCellView = (MessagesListViewCellView) cellView;

		if (item.getUserId() == NetworkInterface.get().getSocketHandler().getUserProfile().getId()) {
			//gameCellView.getSenderDateText().setText(item.getDate());
			gameCellView.getContentText().setFill(Color.WHITE);
			gameCellView.getMessageBox().setStyle("-fx-background-color: #0183ff;-fx-background-radius: 5;"); //blue
			gameCellView.getMessageBox().setAlignment(Pos.CENTER_LEFT);
			gameCellView.getMessageBox().setPadding(new Insets(5, 5, 5, 5));

			gameCellView.getReceiverDateText().setVisible(false);
			gameCellView.getSenderDateText().setVisible(true);

			gameCellView.getSenderProfileImageView().setVisible(false);
			gameCellView.getReceiverProfileImageView().setVisible(true);
			gameCellView.getReceiverProfileImageView().setImage(NetworkInterface.get().getSocketHandler().getUserProfile().picture());
			gameCellView.getSenderProfileImageView().setImage(null);
		} else {
			//gameCellView.getReceiverDateText().setText(item.getDate());
			gameCellView.getContentText().setFill(Color.BLACK);
			gameCellView.getMessageBox().setStyle("-fx-background-color: #eaeaea;-fx-background-radius: 10;"); //grey
			gameCellView.getMessageBox().setAlignment(Pos.CENTER_LEFT);
			gameCellView.getMessageBox().setPadding(new Insets(5, 5, 5, 5));

			gameCellView.getReceiverDateText().setVisible(true);
			gameCellView.getSenderDateText().setVisible(false);

			gameCellView.getSenderProfileImageView().setVisible(true);
			gameCellView.getReceiverProfileImageView().setVisible(false);
			gameCellView.getSenderProfileImageView().setImage(null);
			gameCellView.getReceiverProfileImageView().setImage(null);

			if (this.currentlyTalkingToUserProfile != null) {
				gameCellView.getSenderProfileImageView().setImage(this.currentlyTalkingToUserProfile.picture());
			}
		}

		gameCellView.getContentText().setText(item.getContent());
	}

	@Override
	public AbstractViewCell<ExchangedMessage> createView() {
		return new MessagesListViewCellView(this);
	}

	public void setCurrentlyTalkingToUserProfile(UserProfile currentlyTalkingToUserProfile) {
		this.currentlyTalkingToUserProfile = currentlyTalkingToUserProfile;
	}

}