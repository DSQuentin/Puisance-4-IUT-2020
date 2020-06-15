package iut.group42b.boardgames.client.ui.page.user.settings;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class UserSettingsView extends AbstractView {

	/* UI */
	private ImageView profileImageView;
	private Text emailText;
	private Text usernameText;
	private final Text creationDateText;


	public UserSettingsView() {
		super();
		this.profileImageView = (ImageView) findById("usersettings-imageview-profile");
		this.emailText = (Text) findById("user-settings-text-email");
		this.usernameText = (Text) findById("user-settings-text-username");
		this.creationDateText = (Text) findById("user-settings-text-date");
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
		return profileImageView;
	}

	public Text getEmailText() {
		return emailText;
	}

	public Text getUsernameText() {
		return usernameText;
	}

	public Text getCreationDateText() {
		return creationDateText;
	}

	public void setProfileImageView(ImageView profileImageView) {
		this.profileImageView = profileImageView;
	}

	public void setEmailText(Text emailText) {
		this.emailText = emailText;
	}

	public void setUsernameText(Text usernameText) {
		this.usernameText = usernameText;
	}
}