package iut.group42b.boardgames.client.ui.page.invitation;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

public class InvitationView  extends AbstractView {

	/* UI */
	private final ImageView logo;
	private final ImageView profileImageView;
	private final Button logoutButton;
	private final ListView receivedListView;
	private final ListView sendListView;

	/* Constructor */

	public InvitationView() {
		super();
		this.logo = (ImageView) this.findById("logo");
		this.profileImageView = (ImageView) this.findById("home-imageview-profile");
		this.logoutButton = (Button) this.findById("home-button-tologout");
		this.receivedListView = (ListView) this.findById("received-listview");
		this.sendListView = (ListView) this.findById("send-listview");

	}

	@Override
	public String getViewPath() {
		return "invitation.fxml";
	}

	@Override
	public IController createController() {
		return new InvitationController();
	}

	public ImageView getLogo() {
		return this.logo;
	}

	public ImageView getProfileImageView() {
		return this.profileImageView;
	}

	public Button getLogoutButton() {
		return this.logoutButton;
	}

	public ListView getReceivedListView() {
		return this.receivedListView;
	}

	public ListView getSendListView() {
		return this.sendListView;
	}
}
