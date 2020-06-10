package iut.group42b.boardgames.client.ui.page.home;

import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import javafx.event.ActionEvent;


public class WaitingController implements IController {


	/* Variables */
	private WaitingView view;

	@Override
	public void handle(ActionEvent event) {

		if (event.getSource() == view.getCancelButton()) {
			System.out.println("cancel btn called");
		}

	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof WaitingView)) {
			throw new IllegalArgumentException();
		}

		this.view.getCancelButton().setOnAction(this);

		this.view = (WaitingView) view;

		this.view.
				getLogoGameImageView()
				.setImage(Resource.loadImage(this.view.getGame().picturePath()));

	}

	@Override
	public void onMount() {

	}

	@Override
	public void onUnmount() {

	}


}
