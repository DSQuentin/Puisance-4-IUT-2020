package iut.group42b.boardgames.game;

import iut.group42b.boardgames.client.ui.mvc.IView;

import java.util.List;

public interface IGame {

	IGameHandler createHandler();

	String getName();

	List<String> getTags();

	GameAvailability getAvailability();

	String picturePath();

	int getRequiredPlayer();

	IView createClientView();

}