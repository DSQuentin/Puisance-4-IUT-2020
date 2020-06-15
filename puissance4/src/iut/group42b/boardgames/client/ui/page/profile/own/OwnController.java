package iut.group42b.boardgames.client.ui.page.profile.own;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.login.LoginView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.client.ui.page.register.RegisterView;
import iut.group42b.boardgames.client.ui.page.user.settings.UserSettingsView;
import iut.group42b.boardgames.social.model.UserProfile;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class OwnController implements IController {

    private OwnView view;

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == view.getToLogOutButton()) {
            UserInterface.get().set(new LogoutView());
        } else if (event.getSource() == view.getToParametersButton()) {
            System.out.println("lol");
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
        this.view.getToParametersButton().setOnAction(this);
        System.out.println(this.view.getToParametersButton());
        this.view.getLogo().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
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
