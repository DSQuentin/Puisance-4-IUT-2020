package iut.group42b.boardgames.client.ui.list.message;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.client.ui.page.home.WaitingView;
import iut.group42b.boardgames.game.GameAvailability;
import iut.group42b.boardgames.game.GameRegistry;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.game.packet.matchmaking.MatchmakingJoinPacket;
import iut.group42b.boardgames.social.model.ExchangedMessage;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.Debug;
import iut.group42b.boardgames.util.Utils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import static iut.group42b.boardgames.game.GameAvailability.PLAYABLE;

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
			gameCellView.getSenderDateText().setText(item.getDate());
			gameCellView.getContentText().setFill(Color.WHITE);
			gameCellView.getMessageBox().setStyle("-fx-background-color: #0275B1;");
			gameCellView.getMessageBox().setAlignment(Pos.CENTER_LEFT);

			gameCellView.getReceiverDateText().setVisible(false);
			gameCellView.getSenderDateText().setVisible(true);

			gameCellView.getSenderProfileImageView().setVisible(false);
			gameCellView.getReceiverProfileImageView().setVisible(true);
			gameCellView.getReceiverProfileImageView().setImage(new Image(NetworkInterface.get().getSocketHandler().getUserProfile().getImageUrl(), true));
			gameCellView.getSenderProfileImageView().setImage(null);
		} else { // TODO
			gameCellView.getReceiverDateText().setText(item.getDate());
			gameCellView.getContentText().setFill(Color.WHITE);
			gameCellView.getMessageBox().setStyle("-fx-background-color: #E9F0F8;");
			gameCellView.getMessageBox().setAlignment(Pos.CENTER_LEFT);

			gameCellView.getReceiverDateText().setVisible(true);
			gameCellView.getSenderDateText().setVisible(false);

			gameCellView.getSenderProfileImageView().setVisible(true);
			gameCellView.getReceiverProfileImageView().setVisible(false);
			gameCellView.getSenderProfileImageView().setImage(null);
			gameCellView.getReceiverProfileImageView().setImage(null);

			if (currentlyTalkingToUserProfile != null) {
				gameCellView.getSenderProfileImageView().setImage(new Image(currentlyTalkingToUserProfile.getImageUrl(), true));
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