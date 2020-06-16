package iut.group42b.boardgames.client.ui.page.invitation;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.client.ui.page.profile.own.OwnView;
import iut.group42b.boardgames.social.model.UserProfile;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class InvitationController implements IController {

	private InvitationView view;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getLogoutButton()) {
			UserInterface.get().set(new LogoutView());
		}
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof InvitationView)) {
			throw new IllegalArgumentException();
		}

		this.view = (InvitationView) view;

		UserProfile userProfile = NetworkInterface.get().getSocketHandler().getUserProfile();

		this.view.getProfileImageView().setImage(new Image(userProfile.getImageUrl(), true));
		this.view.getLogoutButton().setOnAction(this);
		this.view.getLogo().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new HomeView());
		});
		this.view.getProfileImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new OwnView());
		});
	}

	@Override
	public void onMount() {

	}

	@Override
	public void onUnmount() {

	}


}
