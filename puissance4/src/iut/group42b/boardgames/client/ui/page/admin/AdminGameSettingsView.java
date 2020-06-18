package iut.group42b.boardgames.client.ui.page.admin;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AdminGameSettingsView extends AbstractView {

    private final ImageView profileImageView;
    private final Button logoutButton;
    private final ImageView gameCoverImageView;
    private final TextField descriptionTextField;
    private final Button playButton;
    private final Button unavailableButton;
    private final Button maintenanceButton;
    private final Button cancelButton;
    private final Button applyButton;

    public AdminGameSettingsView() {
        this.profileImageView = this.findById("admin-game-settings-imageview-profile");
        this.logoutButton = this.findById("admin-game-settings-logout");
        this.gameCoverImageView = this.findById("admin-game-settings-game-cover");
        this.descriptionTextField = this.findById("admin-game-settings-description");
        this.playButton = this.findById("admin-game-settings-play");
        this.unavailableButton = this.findById("admin-game-settings-unavailable");
        this.maintenanceButton = this.findById("admin-game-settings-maintenance");
        this.applyButton = this.findById("admin-game-settings-apply");
        this.cancelButton = this.findById("admin-game-settings-cancel");

    }

    public ImageView getProfileImageView() {
        return this.profileImageView;
    }

    public Button getLogoutButton() {
        return this.logoutButton;
    }

    public ImageView getGameCoverImageView() {
        return this.gameCoverImageView;
    }

    public TextField getDescriptionTextField() {
        return this.descriptionTextField;
    }

    public Button getPlayButton() {
        return this.playButton;
    }

    public Button getUnavailableButton() {
        return this.unavailableButton;
    }

    public Button getMaintenanceButton() {
        return this.maintenanceButton;
    }

    public Button getCancelButton() {
        return this.cancelButton;
    }

    public Button getApplyButton() {
        return this.applyButton;
    }

    @Override
    public String getViewPath() {
        return null;
    }

    @Override
    public IController createController() {
        return null;
    }
}
