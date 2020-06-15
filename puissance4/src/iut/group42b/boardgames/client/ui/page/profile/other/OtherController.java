package iut.group42b.boardgames.client.ui.page.profile.other;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;


public class OtherController implements IController, INetworkHandler {

	/* UI */
	private OtherView view;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == view.getLogoutButton()) {
			UserInterface.get().set(new LogoutView());
		}
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof OtherView)) {
			throw new IllegalArgumentException();
		}
		this.view = (OtherView) view;

		UserProfile userProfile = NetworkInterface.get().getSocketHandler().getUserProfile();

		this.view.getUsernameText().setText(userProfile.getUsername());
		this.view.getOwnAvatarImageView().setImage(new Image(userProfile.getImageUrl(), true));

		this.view.getLogoImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new HomeView());
		});
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
