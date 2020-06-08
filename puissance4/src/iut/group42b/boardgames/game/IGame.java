package iut.group42b.boardgames.game;

import java.util.List;

public interface IGame {

	public IGameHandler createHandler();

	public String getName();

	public List<String> getTags();

	public GameAvailability getAvailability();

}
