package iut.group42b.boardgames.client.ui.page.user.settings;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.client.ui.page.profile.own.OwnView;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;

public class UserSettingsController implements IController, INetworkHandler {

	/* Variables */
	private UserSettingsView view;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getCancelButton()) {
			UserInterface.get().set(new OwnView());
		}
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof UserSettingsView)) {
			throw new IllegalArgumentException();
		}

		this.view = (UserSettingsView) view;

		UserProfile userProfile = NetworkInterface.get().getSocketHandler().getUserProfile();

		this.view.getEmailText().setText(userProfile.getEmail());
		this.view.getProfileImageView().setImage(new Image(userProfile.getImageUrl(), true));
		this.view.getUsernameText().setText(userProfile.getUsername());
		this.view.getCreationDateText().setText(Messages.USER_ACCOUNT_CREATED_AT.use(userProfile.getCreationDate()));
		this.view.getCancelButton().setOnAction(this);
	}

	@Override
	public void onMount() {

	}

	@Override
	public void onUnmount() {

	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {

	}


}
