package iut.group42b.boardgames.client.ui.page.admin;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.client.ui.page.profile.own.OwnView;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class AdminGameSettingsController implements IController {
    private AdminGameSettingsView view;

    @Override
    public void attachView(IView view) {
        if (!(view instanceof AdminGameSettingsView)) {
            throw new IllegalArgumentException();
        }
        this.view = (AdminGameSettingsView) view;
        this.view.getProfileImageView().setImage(new Image(NetworkInterface.get().getSocketHandler().getUserProfile().getImageUrl(), true));
        System.out.println(this.view.getGame());
        this.view.getGameCoverImageView().setImage(Resource.loadImage(this.view.getGame().picturePath()));
        StringBuilder description= new StringBuilder();
        /*for (String elem:this.view.getGame().getTags()){
            description.append(elem).append(",");
        }*/
        this.view.getApplyButton().setOnAction(this);
        this.view.getCancelButton().setOnAction(this);
        for (int i = 0; i < this.view.getGame().getTags().size(); i++) {
            description.append(this.view.getGame().getTags().get(i));
            if(i<this.view.getGame().getTags().size()-1){
                description.append(",");
            }
        this.view.getDescriptionTextField().setText(description.toString());
        }
        this.view.getLogo().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            UserInterface.get().set(new HomeView());
        });
        this.view.getProfileImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            UserInterface.get().set(new OwnView(NetworkInterface.get().getSocketHandler().getUserProfile()));
        });

    }

    @Override
    public void onMount() {

    }

    @Override
    public void onUnmount() {

    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == this.view.getLogoutButton()) {
            UserInterface.get().set(new LogoutView());
        }
        else if (event.getSource() == this.view.getApplyButton() || event.getSource() == this.view.getCancelButton() ) {
            UserInterface.get().set(new HomeView());
        }
    }

}
