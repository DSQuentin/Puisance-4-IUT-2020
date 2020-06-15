package iut.group42b.boardgames.client.ui.mvc;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface IController extends EventHandler<ActionEvent> {

	void attachView(IView view);

	void onMount();

	void onUnmount();

}