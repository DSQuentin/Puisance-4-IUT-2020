package iut.group42b.boardgames.client.ui.page.login;

import iut.group42b.boardgames.client.ui.component.Carousel;
import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginView extends AbstractView {

	/* UI */
	private final TextField emailTextField;
	private final TextField passwordField;
	private final Button submitButton;
	private final Hyperlink registerText;
	private final AnchorPane carousel;

	/* Variables */
	private final String forcedEmail, forcedPassword;

	/* Constructor */
	public LoginView() {
		this(null, null);
	}

	/* Constructor */
	public LoginView(String forcedEmail, String forcedPassword) {
		super();

		this.forcedEmail = forcedEmail;
		this.forcedPassword = forcedPassword;

		/* "fichiersource-class-usage" */
		this.emailTextField = this.findById("login-textfield-email");
		this.passwordField = (PasswordField) this.findById("login-textfield-password");
		this.submitButton = this.findById("login-button-submit");
		this.registerText = this.findById("login-text-to-register");
		this.carousel = this.findById("auth-container-hbox");
		this.carousel.getChildren().add(new Carousel());


		if (forcedEmail != null) {
			this.emailTextField.setText(forcedEmail);
		}

		if (forcedPassword != null) {
			this.passwordField.setText(forcedPassword);
		}
	}

	@Override
	public String getViewPath() {
		return "login.fxml";
	}

	@Override
	public IController createController() {
		return new LoginController();
	}

	public Hyperlink getRegisterText() {
		return this.registerText;
	}

	public AnchorPane getCarousel() {
		return this.carousel;
	}

	public String getForcedEmail() {
		return this.forcedEmail;
	}

	public String getForcedPassword() {
		return this.forcedPassword;
	}

	public TextField getEmailTextField() {
		return this.emailTextField;
	}

	public TextField getPasswordField() {
		return this.passwordField;
	}

	public Button getSubmitButton() {
		return this.submitButton;
	}

	public Hyperlink getRegisterHyperlink() {
		return this.registerText;
	}

}