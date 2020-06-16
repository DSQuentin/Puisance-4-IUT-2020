package iut.group42b.boardgames.client.ui.page.user.settings;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class UserSettingsView extends AbstractView {

	private final Text creationDateText;
	private final Button cancelButton;
	private final Button applyButton;
	private final TextField newProfilePictureURLTextField;
	private final TextField newUsername;
	private final TextField newUsernameConfirm;
	private final TextField newPassword;
	private final TextField newPasswordConfirm;
	private final TextField newEmail;
	private final TextField newEmailConfirm;
	/* UI */
	private ImageView profileImageView;
	private Text emailText;
	private Text usernameText;


	public UserSettingsView() {
		super();
		this.profileImageView = this.findById("usersettings-imageview-profile");
		this.emailText = this.findById("user-settings-text-email");
		this.usernameText = this.findById("user-settings-text-username");
		this.creationDateText = this.findById("user-settings-text-date");
		this.cancelButton = this.findById("user-settings-button-cancel");
		this.applyButton = this.findById("user-settings-button-apply");
		this.newProfilePictureURLTextField = this.findById("user-settings-textfield-url-profile");
		this.newUsername = this.findById("user-setting-textfield-new-username");
		this.newUsernameConfirm = this.findById("user-setting-textfield-new-username-confirm");
		this.newPassword = this.findById("user-setting-textfield-password");
		this.newPasswordConfirm = this.findById("user-setting-textfield-password-confirm");
		this.newEmail = this.findById("user-setting-textfield-email");
		this.newEmailConfirm = this.findById("user-setting-textfield-email-confirm");
	}

	@Override
	public String getViewPath() {
		return "user-settings.fxml";
	}

	@Override
	public IController createController() {
		return new UserSettingsController();
	}

	public ImageView getProfileImageView() {
		return this.profileImageView;
	}

	public void setProfileImageView(ImageView profileImageView) {
		this.profileImageView = profileImageView;
	}

	public Text getEmailText() {
		return this.emailText;
	}

	public void setEmailText(Text emailText) {
		this.emailText = emailText;
	}

	public Text getUsernameText() {
		return this.usernameText;
	}

	public void setUsernameText(Text usernameText) {
		this.usernameText = usernameText;
	}

	public Text getCreationDateText() {
		return this.creationDateText;
	}

	public Button getCancelButton() {
		return this.cancelButton;
	}

	public Button getApplyButton() {
		return this.applyButton;
	}

	public TextField getNewProfilePictureURLTextField() {
		return this.newProfilePictureURLTextField;
	}

	public TextField getNewUsername() {
		return this.newUsername;
	}

	public TextField getNewUsernameConfirm() {
		return this.newUsernameConfirm;
	}

	public TextField getNewPassword() {
		return this.newPassword;
	}

	public TextField getNewPasswordConfirm() {
		return this.newPasswordConfirm;
	}

	public TextField getNewEmail() {
		return this.newEmail;
	}

	public TextField getNewEmailConfirm() {
		return this.newEmailConfirm;
	}
}