package iut.group42b.boardgames.client.ui.page.login;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginView extends AbstractView {

	/* UI */
	private final TextField emailTextField;
	private final TextField passwordField;
	private final Button submitButton;
	private final Hyperlink registerText;

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
		this.emailTextField = (TextField) this.findById("login-textfield-email");
		this.passwordField = (PasswordField) this.findById("login-textfield-password");
		this.submitButton = (Button) this.findById("login-button-submit");
		this.registerText = (Hyperlink) this.findById("login-text-to-register");


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