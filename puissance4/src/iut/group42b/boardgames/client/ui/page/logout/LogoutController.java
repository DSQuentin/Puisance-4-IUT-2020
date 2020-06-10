package iut.group42b.boardgames.client.ui.page.logout;

import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.index.IndexView;
import iut.group42b.boardgames.client.ui.page.login.LoginView;
import iut.group42b.boardgames.client.ui.page.register.RegisterView;
import javafx.event.ActionEvent;

public class LogoutController implements IController {

    private LogoutView view;


    @Override
    public void handle(ActionEvent event) {

        if (event.getSource() == view.getToLoginButton()) {
            UserInterface.get().set(new LoginView());
        } else if (event.getSource() == view.getToRegisterButton()) {
            UserInterface.get().set(new RegisterView());
        } else if (event.getSource() == view.getBackHome()){
            UserInterface.get().set(new IndexView());
        }
    }

    @Override
    public void attachView(IView view) {
        if (!(view instanceof LogoutView)) {
            throw new IllegalArgumentException();
        }

        this.view = (LogoutView) view;

        this.view.getToLoginButton().setOnAction(this);
        this.view.getToRegisterButton().setOnAction(this);
        this.view.getBackHome().setOnAction(this);
    }

    @Override
    public void onMount() {

    }

    @Override
    public void onUnmount() {

    }


}
