package iut.group42b.boardgames.client.ui.page.profile.own;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.client.ui.page.user.settings.UserSettingsView;
import iut.group42b.boardgames.social.model.UserProfile;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Random;

public class OwnController implements IController {

	private OwnView view;


	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getToLogOutButton()) {
			UserInterface.get().set(new LogoutView());
		} else if (event.getSource() == this.view.getToSettingsButton()) {
			UserInterface.get().set(new UserSettingsView());
		} else if (event.getSource() == this.view.getProfileImageOnProfile()) {
			UserInterface.get().set(new OwnView(NetworkInterface.get().getSocketHandler().getUserProfile()));
		}
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof OwnView)) {
			throw new IllegalArgumentException();
		}

		this.view = (OwnView) view;

		UserProfile userProfile = NetworkInterface.get().getSocketHandler().getUserProfile();

		UserProfile targetUserProfile = this.view.getUserprofile();

		this.view.getUsernameText().setText(targetUserProfile.getUsername());
		this.view.getProfileImageView().setImage(new Image(userProfile.getImageUrl(), true));
		this.view.getProfileImageOnProfile().setImage(new Image(targetUserProfile.getImageUrl(), true));
		this.view.getToLogOutButton().setOnAction(this);

		if (targetUserProfile.getId()==userProfile.getId()){
			this.view.getToSettingsButton().setVisible(true);
		}
		else{
			this.view.getToSettingsButton().setVisible(false);
		}

		this.view.getToSettingsButton().setOnAction(this);

		this.view.getLogoImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new HomeView());
		});



		Random rand = new Random();

		this.view.getWinCircle().setRingWidth(10);
		this.view.getDefeatCircle().setRingWidth(10);

	}

	@Override
	public void onMount() {

	}

	@Override
	public void onUnmount() {

	}


}
