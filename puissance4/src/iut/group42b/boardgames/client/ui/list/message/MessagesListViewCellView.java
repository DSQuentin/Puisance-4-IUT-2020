package iut.group42b.boardgames.client.ui.list.message;

import iut.group42b.boardgames.client.ui.mvc.list.AbstractViewCell;
import iut.group42b.boardgames.client.ui.mvc.list.IListViewCellController;
import iut.group42b.boardgames.social.model.ExchangedMessage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MessagesListViewCellView extends AbstractViewCell<ExchangedMessage> {

	/* UI */
	private final ImageView senderProfileImageView;
	private final Text senderDateText;

	private final ImageView receiverProfileImageView;
	private final Text receiverDateText;

	private final Text contentText;
	private final HBox messageBox;

	/* Constructor */
	protected MessagesListViewCellView(IListViewCellController<ExchangedMessage> partController) {
		super(partController);

		this.senderProfileImageView = (ImageView) this.findById("sender-imageview-profile");
		this.senderDateText = (Text) this.findById("sender-text-date");

		this.contentText = (Text) this.findById("message-text-content");

		this.receiverProfileImageView = (ImageView) this.findById("receiver-imageview-profile");
		this.receiverDateText = (Text) this.findById("receiver-text-date");

		this.messageBox = (HBox) this.findById("content-message");


	}

	@Override
	public String getViewPath() {
		return "message-cell.fxml";
	}

	public ImageView getSenderProfileImageView() {
		return this.senderProfileImageView;
	}

	public Text getSenderDateText() {
		return this.senderDateText;
	}

	public ImageView getReceiverProfileImageView() {
		return this.receiverProfileImageView;
	}

	public HBox getMessageBox() {
		return this.messageBox;
	}

	public Text getReceiverDateText() {
		return this.receiverDateText;
	}

	public Text getContentText() {
		return this.contentText;
	}
}