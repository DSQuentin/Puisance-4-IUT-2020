package iut.group42b.boardgames.client.ui.mvc;

import javafx.scene.Parent;

public interface IView {

	public String getViewPath();

	public Parent getRoot();

	public IController createController();

}