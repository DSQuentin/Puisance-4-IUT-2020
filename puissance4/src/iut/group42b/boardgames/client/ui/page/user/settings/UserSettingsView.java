package iut.group42b.boardgames.client.ui.page.user.settings;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;

public class UserSettingsView extends AbstractView {

	@Override
	public String getViewPath() {
		return "user-settings.fxml";
	}

	@Override
	public IController createController() {
		return null;
	}
}