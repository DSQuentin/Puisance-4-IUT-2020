package iut.group42b.boardgames.game.impl.connect4.ui;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.game.impl.connect4.Connect4GameArena;
import iut.group42b.boardgames.game.impl.connect4.Connect4Side;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4GameInfoPacket;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4GridUpdatePacket;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4PutTokenPacket;
import iut.group42b.boardgames.game.packet.PlayerLoosePacket;
import iut.group42b.boardgames.game.packet.PlayerSurrenderPacket;
import iut.group42b.boardgames.game.packet.PlayerWinPacket;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.Chronometer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;

import java.util.Optional;

public class Connect4UIController implements IController, INetworkHandler, Connect4GridCanvas.OnTokenClick {

	/* UI */
	private Connect4Side playerSide;
	private UserProfile opponentUserProfile;
	private Connect4UIView view;
	private Connect4GridCanvas canvas;
	private Chronometer chronometer;
	private static final Integer userNumberToken = Connect4GameArena.GRID_HEIGHT * Connect4GameArena.GRID_WIDTH;
	private static final Integer opponentNumberToken = Connect4GameArena.GRID_HEIGHT * Connect4GameArena.GRID_WIDTH;

	@Override
	public void attachView(IView view) {
		if (!(view instanceof Connect4UIView)) {
			throw new IllegalArgumentException();
		}

		this.view = (Connect4UIView) view;

		this.canvas = new Connect4GridCanvas();

		UserProfile currentUserProfile = NetworkInterface.get().getSocketHandler().getUserProfile();
		this.view.getUserImageView().setImage(new Image(currentUserProfile.getImageUrl(), true));
		this.view.getUsernameText().setText(currentUserProfile.getUsername());
		this.view.getSurrenderButton().setOnAction(this);
		this.canvas.setTokenClickCallback(this);
		this.canvas.widthProperty().bind(this.view.getGridContainerStackPane().widthProperty());
		this.canvas.heightProperty().bind(this.view.getGridContainerStackPane().heightProperty());

		this.view.getGridContainerStackPane().getChildren().add(this.canvas); // add canvas witch contain grid into stackpane

		UserInterface.get().closeCurrentDialog();
	}

	@Override
	public void onMount() {
		NetworkInterface.get().getSocketHandler().subscribe(this);
	}

	@Override
	public void onUnmount() {
		NetworkInterface.get().getSocketHandler().unsubscribe(this);
	}

	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {
		if (packet instanceof Connect4GridUpdatePacket) {
			Connect4GridUpdatePacket gridUpdatePacket = (Connect4GridUpdatePacket) packet;

			this.canvas.updateLocalGrid(gridUpdatePacket.toSideGrid());

			this.updateWhichTurnToPlayText(gridUpdatePacket.getNextSideToPlay());
			this.updateRemainingTokenTexts();
		} else if (packet instanceof Connect4GameInfoPacket) {
			Connect4GameInfoPacket gameInfoPacket = (Connect4GameInfoPacket) packet;

			this.playerSide = gameInfoPacket.getMySide();
			this.opponentUserProfile = gameInfoPacket.getOpponentProfile();

			this.updateUserProfile();

			this.stopChronometerIfRunning();

			this.chronometer = new Chronometer(this.view.getTimerText()::setText);
			this.chronometer.setRunning(true);
			this.chronometer.start();
		} else if (packet instanceof PlayerWinPacket) {
			PlayerWinPacket winPacket = (PlayerWinPacket) packet;

			this.stopChronometerIfRunning();

			String winReason;
			if (winPacket.isSurrender()) {
				winReason = Messages.ALERT_WINREASON_SURRENDER.use();
			} else if (winPacket.isConnectionLost()) {
				winReason = Messages.ALERT_WINREASON_CONNECTIONLOST.use();
			} else {
				winReason = Messages.ALERT_WINREASON_NORMAL.use();
			}

			Platform.runLater(() -> {


				Alert al = new Alert(Alert.AlertType.INFORMATION);
				al.setTitle(Messages.ALERT_VICTORY_TITLE.use());
				al.setHeaderText(Messages.ALERT_VICTORY_HEADER.use());
				al.setContentText(winReason);

				Optional<ButtonType> result = al.showAndWait();
				if (result.get() == ButtonType.OK || result.get() == ButtonType.CLOSE) {
					UserInterface.get().set(new HomeView());
				}
			});

		} else if (packet instanceof PlayerLoosePacket) {
			Platform.runLater(() -> {
				this.stopChronometerIfRunning();
				Alert al = new Alert(Alert.AlertType.INFORMATION);
				al.setTitle(Messages.ALERT_DEFEAT_TITLE.use());
				al.setHeaderText(Messages.ALERT_DEFEAT_HEADER.use());

				Optional<ButtonType> result = al.showAndWait();
				if (result.get() == ButtonType.OK || result.get() == ButtonType.CLOSE) {
					UserInterface.get().set(new HomeView());
				}
			});
		}
	}

	/**
	 * Stop Chronometer if running
	 */
	private void stopChronometerIfRunning() {
		if (this.chronometer != null) {
			this.chronometer.setRunning(false);
			this.chronometer = null;
		}
	}

	/**
	 * Update UserProfile view elements.
	 */
	private void updateUserProfile() {
		this.view.getOpponentNameText().setText(this.opponentUserProfile.getUsername());
		this.view.getOpponentImageView().setImage(new Image(this.opponentUserProfile.getImageUrl()));
	}

	/**
	 * Update which turn to play text
	 *
	 * @param nextSideToPlay Connect4Side
	 */
	private void updateWhichTurnToPlayText(Connect4Side nextSideToPlay) {
		String name = this.opponentUserProfile.getUsername();
		this.canvas.setHelperEnabled(false);
		if (this.playerSide == nextSideToPlay) {
			this.canvas.setHelperEnabled(true);

			name = NetworkInterface.get().getSocketHandler().getUserProfile().getUsername();
		}

		this.view.getWhoTurnText().setText(Messages.UI_CONNECT4_TURN.use(name));
	}

	/**
	 * Update Remaining Token Texts
	 */
	private void updateRemainingTokenTexts() {
		// TODO Compute remaining token for each side and display proper values
		this.view.getUserTockensRemainingText().setText(Messages.GAME_NUMBER_OF_TOKENS.use(14));
		this.view.getOpponentTockensRemainingText().setText(Messages.GAME_NUMBER_OF_TOKENS.use(14));
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getSurrenderButton()) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle(Messages.UI_ALERT_TITLE_SURRENDER.use());
			alert.setHeaderText(Messages.UI_ALERT_HEADER_SURRENDER.use());
			alert.setContentText(Messages.UI_ALERT_CONTEXT_SURRENDER.use());

			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				NetworkInterface.get().getSocketHandler().queue(new PlayerSurrenderPacket());
				UserInterface.get().set(new HomeView());
			} else {
				alert.close();
			}
		}
	}

	@Override
	public void onClick(Connect4GridCanvas canvas, int x, int y) {
		if (this.playerSide == null) {
			return;
		}

		NetworkInterface.get().getSocketHandler().queue(new Connect4PutTokenPacket(this.playerSide, x, y));
	}

}