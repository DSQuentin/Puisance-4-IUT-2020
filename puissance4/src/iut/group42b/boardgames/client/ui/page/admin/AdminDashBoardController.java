package iut.group42b.boardgames.client.ui.page.admin;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.social.model.UserProfile;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class AdminDashBoardController implements IController {
	private AdminDashBoardView view;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getLogOut()) {
			UserInterface.get().set(new LogoutView());
		}

	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof AdminDashBoardView)) {
			throw new IllegalArgumentException();
		}

		this.view = (AdminDashBoardView) view;

		UserProfile userProfile = NetworkInterface.get().getSocketHandler().getUserProfile();

		this.view.getLogOut().setOnAction(this);
		this.view.getProfileImageView().setImage(new Image(NetworkInterface.get().getSocketHandler().getUserProfile().getImageUrl(), true));


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
}
