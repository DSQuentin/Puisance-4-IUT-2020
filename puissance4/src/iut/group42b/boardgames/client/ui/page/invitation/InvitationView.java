package iut.group42b.boardgames.client.ui.page.invitation;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;


public class InvitationView  extends AbstractView {

	/* UI */


	/* Constructor */

	public InvitationView() {
		super();

	}

	@Override
	public String getViewPath() {
		return "invitation.fxml";
	}

	@Override
	public IController createController() {
		return new InvitationController();
	}
}
