package iut.group42b.boardgames.client.ui.mvc;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface IController extends EventHandler<ActionEvent> {

	/**
	 * Controller actions
	 *
	 * @param view
	 */
	void attachView(IView view);

	/**
	 * when arriving on this page
	 */
	void onMount();

	/**
	 * When exiting this page
	 */
	void onUnmount();

}