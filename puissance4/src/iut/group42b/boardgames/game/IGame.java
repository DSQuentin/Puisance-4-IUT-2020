package iut.group42b.boardgames.game;

import iut.group42b.boardgames.client.ui.mvc.IView;

import java.util.List;

public interface IGame {

	/**
	 * Create Handler for game.
	 *
	 * @return IGameHandler
	 */
	IGameHandler createHandler();

	/**
	 * Get the name of the game.
	 *
	 * @return A string.
	 */
	String getName();

	/**
	 * Get all tags of a game.
	 *
	 * @return A List of String.
	 */
	List<String> getTags();

	/**
	 * Get Availability state of game -> PLAYABLE, NOT_PLAYABLE, MAINTENANCE
	 *
	 * @return A GameAvailability.
	 */
	GameAvailability getAvailability();

	/**
	 * Get the picture path of game.
	 *
	 * @return A String.
	 */
	String picturePath();

	/**
	 * Get the number of minimum required players to play at this game.
	 *
	 * @return An int.
	 */
	int getRequiredPlayer();

	/**
	 * Jump to game view
	 *
	 * @return IView
	 */
	IView createClientView();

}