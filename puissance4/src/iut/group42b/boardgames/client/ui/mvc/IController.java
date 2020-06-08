package iut.group42b.boardgames.client.ui.mvc;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface IController extends EventHandler<ActionEvent> {

	public void attachVue(IView vue);

	public void onMount();

	public void onUnmount();

}