package iut.group42b.boardgames.client.ui.page.register;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterView extends AbstractView {

	/* UI */
	private final TextField usernameTextField;
	private final TextField emailTextField;
	private final TextField passwordPasswordField;
	private final Button submitButton;
	private final Hyperlink toLoginHyperlink;

	/* Constructor */
	public RegisterView() {
		super();

		/* "fichiersource-class-usage" */
		this.usernameTextField = (TextField) this.findById("register-textfield-username");
		this.emailTextField = (TextField) this.findById("register-textfield-email");
		this.passwordPasswordField = (PasswordField) this.findById("register-passwordfield-password");
		this.submitButton = (Button) this.findById("register-button-submit");
		this.toLoginHyperlink = (Hyperlink) this.findById("register-hyperlink-login");
	}

	@Override
	public String getViewPath() {
		return "register.fxml";
	}

	@Override
	public IController createController() {
		return new RegisterController();
	}

	public TextField getUsernameTextField() {
		return this.usernameTextField;
	}

	public TextField getEmailTextField() {
		return this.emailTextField;
	}

	public TextField getPasswordPasswordField() {
		return this.passwordPasswordField;
	}

	public Button getSubmitButton() {
		return this.submitButton;
	}

	public Hyperlink getToLoginHyperlink() {
		return this.toLoginHyperlink;
	}


}