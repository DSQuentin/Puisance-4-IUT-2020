package iut.group42b.boardgames.game;

import iut.group42b.boardgames.client.ui.mvc.IView;

import java.util.List;

public interface IGame {

	public IGameHandler createHandler();

	public String getName();

	public List<String> getTags();

	public GameAvailability getAvailability();

	public String picturePath();

	public int getRequiredPlayer();

	public IView createClientView();

}