package iut.group42b.boardgames.client.ui.page.index;

import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.login.LoginView;
import iut.group42b.boardgames.client.ui.page.register.RegisterView;
import javafx.event.ActionEvent;


public class IndexController implements IController {

	/* Variables */
	private IndexView view;

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getToLoginButton() || event.getSource() == this.view.getPlayNowButton()) {

			// UserInterface.get().openDialog(new LoginView());
			UserInterface.get().set(new LoginView());
		} else if (event.getSource() == this.view.getToRegisterButton()) {
			UserInterface.get().set(new RegisterView());
		}
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof IndexView)) {
			throw new IllegalArgumentException();
		}

		this.view = (IndexView) view;

		this.view.getToLoginButton().setOnAction(this);
		this.view.getToRegisterButton().setOnAction(this);
		this.view.getPlayNowButton().setOnAction(this);
	}

	@Override
	public void onMount() {

	}

	@Override
	public void onUnmount() {

	}

}