package iut.group42b.boardgames.client.ui.page.user.settings;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.profile.own.OwnView;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserSettingsChangePacket;
import iut.group42b.boardgames.network.packet.impl.auth.UserSettingsChangedPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.Utils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class UserSettingsController implements IController, INetworkHandler {

	/* Variables */
	private UserSettingsView view;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getCancelButton()) {
			UserInterface.get().set(new OwnView());
		} else if (event.getSource() == this.view.getApplyButton()) {
			String username1 = this.view.getNewUsername().getText();
			String username2 = this.view.getNewUsernameConfirm().getText();
			String password1 = this.view.getNewPassword().getText();
			String password2 = this.view.getNewPasswordConfirm().getText();
			String email1 = this.view.getNewEmail().getText();
			String email2 = this.view.getNewEmailConfirm().getText();
			String profileUrl = this.view.getNewProfilePictureURLTextField().getText();

			boolean updateUsername = Utils.areEqualAndValid(username1, username2);
			boolean updatePassword = Utils.areEqualAndValid(password1, password2);
			boolean updateEmail = Utils.areEqualAndValid(email1, email2);
			boolean updateProfilePicture = Utils.areEqualAndValid(profileUrl, profileUrl);

			NetworkInterface.get().getSocketHandler().queue(new UserSettingsChangePacket(
					updateEmail ? email1 : null,
					updateUsername ? username1 : null,
					updatePassword ? password1 : null,
					updateProfilePicture ? profileUrl : null
			));

			System.out.println("updateEmail = " + updateEmail);
			System.out.println("username1 = " + username1);
			System.out.println("username2 = " + username2);
			System.out.println("updateUsername = " + updateUsername);
			System.out.println("updatePassword = " + updatePassword);
			System.out.println("updateProfilePicture = " + updateProfilePicture);
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

		this.view.getApplyButton().setOnAction(this);
		this.view.getCancelButton().setOnAction(this);
	}

	@Override
	public void onMount() {
		NetworkInterface.get().getSocketHandler().subscribe(this);
	}

	@Override
	public void onUnmount() {
		NetworkInterface.get().getSocketHandler().unsubscribe(this);
	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) { // TODO i18n
		if (packet instanceof UserSettingsChangedPacket) {
			UserSettingsChangedPacket changedPacket = (UserSettingsChangedPacket) packet;

			handler.setProfile(changedPacket.getNewUserProfile());

			StringBuilder builder = new StringBuilder();
			builder.append("Your: ");

			if (changedPacket.isUsernameChanged()) {
				builder.append("\n - Username has been changed");
			} else if (changedPacket.isUsernameError()) {
				builder.append("\n - Username has returned an error while trying to be changed");
			}

			Platform.runLater(() -> {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle(Messages.ALERT_LOGIN_TITLE.use());
				alert.setHeaderText(null);
				alert.setContentText(builder.toString());

				alert.showAndWait();

				UserInterface.get().set(this.view);
			});
		}
	}


}
