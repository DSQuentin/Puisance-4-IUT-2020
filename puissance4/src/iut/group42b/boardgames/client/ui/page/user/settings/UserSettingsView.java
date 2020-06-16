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
		this.profileImageView = (ImageView) this.findById("usersettings-imageview-profile");
		this.emailText = (Text) this.findById("user-settings-text-email");
		this.usernameText = (Text) this.findById("user-settings-text-username");
		this.creationDateText = (Text) this.findById("user-settings-text-date");
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

	public Text getEmailText() {
		return this.emailText;
	}

	public Text getUsernameText() {
		return this.usernameText;
	}

	public Text getCreationDateText() {
		return this.creationDateText;
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