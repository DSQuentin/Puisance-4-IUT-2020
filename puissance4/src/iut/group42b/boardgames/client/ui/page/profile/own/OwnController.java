package iut.group42b.boardgames.client.ui.page.profile.own;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.component.circularprogressbar.RingProgressIndicator;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.client.ui.page.user.settings.UserSettingsView;
import iut.group42b.boardgames.social.model.UserProfile;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.Random;

public class OwnController implements IController {

	private OwnView view;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == view.getToLogOutButton()) {
			UserInterface.get().set(new LogoutView());
		} else if (event.getSource() == view.getToSettingsButton()) {
			UserInterface.get().set(new UserSettingsView());
		}

	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof OwnView)) {
			throw new IllegalArgumentException();
		}

		this.view = (OwnView) view;

		UserProfile userProfile = NetworkInterface.get().getSocketHandler().getUserProfile();

		this.view.getUsernameText().setText(userProfile.getUsername());
		this.view.getProfileImageView().setImage(new Image(userProfile.getImageUrl(), true));
		this.view.getProfileImageOnProfile().setImage(new Image(userProfile.getImageUrl(), true));
		this.view.getToLogOutButton().setOnAction(this);
		this.view.getToSettingsButton().setOnAction(this);

		this.view.getLogoImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			UserInterface.get().set(new HomeView());
		});

		// WTF It's work be I don't know why! Please do not touch this !!!
		this.view.getToSettingsButton().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			System.out.println("Button triggered dfsdfdsfsdfdsf");
		});

		Random rand=new Random();

		this.view.getWinCircle().setRingWidth(10);
		this.view.getDefeatCircle().setRingWidth(10);

		this.view.getWinCircle().setProgress((rand.nextInt(100-0)+1));
		this.view.getDefeatCircle().setProgress((rand.nextInt(100-0)+1));
	}

	@Override
	public void onMount() {

	}

	@Override
	public void onUnmount() {

	}


}
