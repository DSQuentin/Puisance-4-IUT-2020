package iut.group42b.boardgames.game.impl.connect4.ui;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class Connect4UIView extends AbstractView {

	/* UI */
	private final StackPane gridContainerStackPane;
	private final Button surrenderButton;
	private final Text whoTurnText;
	private final Text opponentNameText;
	private final Text timerText;
	private final Text usernameText;
	private final Text userTockensRemainingText;
	private final Text opponentTockensRemainingText;
	private final ImageView userImageView;
	private final ImageView opponentImageView;

	/* Constructor */
	public Connect4UIView() {
		super();

		this.gridContainerStackPane = (StackPane) findById("connect4-stackpane-container");
		this.surrenderButton = (Button) findById("connect4-button-surrender");
		this.opponentNameText = (Text) findById("connect4-opponent-name");
		this.whoTurnText = (Text) findById("connect4-who-turn");
		this.timerText = (Text) findById("connect4-timer");
		this.usernameText = (Text) findById("connect4-username");
		this.userTockensRemainingText = (Text) findById("connect4-tokens-remaining");
		this.opponentTockensRemainingText = (Text) findById("connect4-opponent-tokens-remaining");
		this.userImageView = (ImageView) findById("connect4-imageview-user-profile");
		this.opponentImageView = (ImageView) findById("connect4-imageview-opponent-profile");
	}

	@Override
	public String getViewPath() {
		return "connect4.fxml";
	}

	@Override
	public IController createController() {
		return new Connect4UIController();
	}

	public StackPane getGridContainerStackPane() {
		return gridContainerStackPane;
	}

	public Button getSurrenderButton() {
		return surrenderButton;
	}

	public Text getWhoTurnText() {
		return whoTurnText;
	}

	public Text getOpponentNameText() {
		return opponentNameText;
	}

	public Text getTimerText() {
		return timerText;
	}

	public Text getUsernameText() {
		return usernameText;
	}

	public Text getUserTockensRemainingText() {
		return userTockensRemainingText;
	}

	public Text getOpponentTockensRemainingText() {
		return opponentTockensRemainingText;
	}

	public ImageView getUserImageView() {
		return userImageView;
	}

	public ImageView getOpponentImageView() {
		return opponentImageView;
	}
}