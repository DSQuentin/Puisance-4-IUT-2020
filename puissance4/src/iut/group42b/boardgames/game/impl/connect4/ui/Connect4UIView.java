package iut.group42b.boardgames.game.impl.connect4.ui;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;
import javafx.scene.control.Button;
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

	/**
	 * Constructor Connect4UIView
	 */
	public Connect4UIView() {
		super();

		this.gridContainerStackPane = (StackPane) this.findById("connect4-stackpane-container");
		this.surrenderButton = (Button) this.findById("connect4-button-surrender");
		this.opponentNameText = (Text) this.findById("connect4-opponent-name");
		this.whoTurnText = (Text) this.findById("connect4-who-turn");
		this.timerText = (Text) this.findById("connect4-timer");
		this.usernameText = (Text) this.findById("connect4-username");
		this.userTockensRemainingText = (Text) this.findById("connect4-tokens-remaining");
		this.opponentTockensRemainingText = (Text) this.findById("connect4-opponent-tokens-remaining");
		this.userImageView = (ImageView) this.findById("connect4-imageview-user-profile");
		this.opponentImageView = (ImageView) this.findById("connect4-imageview-opponent-profile");
	}

	@Override
	public String getViewPath() {
		return "connect4.fxml";
	}

	@Override
	public IController createController() {
		return new Connect4UIController();
	}

	/**
	 * @return StackPane.
	 */
	public StackPane getGridContainerStackPane() {
		return this.gridContainerStackPane;
	}

	/**
	 * @return Button.
	 */
	public Button getSurrenderButton() {
		return this.surrenderButton;
	}

	/**
	 * @return Text
	 */
	public Text getWhoTurnText() {
		return this.whoTurnText;
	}

	/**
	 * @return Text
	 */
	public Text getOpponentNameText() {
		return this.opponentNameText;
	}

	/**
	 * @return Text
	 */
	public Text getTimerText() {
		return this.timerText;
	}

	/**
	 * @return Text
	 */
	public Text getUsernameText() {
		return this.usernameText;
	}

	/**
	 * @return Text
	 */
	public Text getUserTockensRemainingText() {
		return this.userTockensRemainingText;
	}

	/**
	 * @return Text
	 */
	public Text getOpponentTockensRemainingText() {
		return this.opponentTockensRemainingText;
	}

	/**
	 * @return ImageView
	 */
	public ImageView getUserImageView() {
		return this.userImageView;
	}

	/**
	 * @return ImageView
	 */
	public ImageView getOpponentImageView() {
		return this.opponentImageView;
	}
}