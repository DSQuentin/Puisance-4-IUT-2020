package iut.group42b.boardgames.client.ui.mvc;

import javafx.scene.Parent;

public interface IView {

	String getViewPath();

	Parent getRoot();

	IController createController();

}