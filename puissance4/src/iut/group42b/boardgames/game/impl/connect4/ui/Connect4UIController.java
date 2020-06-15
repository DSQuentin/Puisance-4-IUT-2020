package iut.group42b.boardgames.game.impl.connect4.ui;

import iut.group42b.boardgames.client.i18n.Messages;
import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.game.impl.connect4.Connect4Game;
import iut.group42b.boardgames.game.impl.connect4.Connect4Side;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4GameInfoPacket;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4GridUpdatePacket;
import iut.group42b.boardgames.game.impl.connect4.packet.Connect4PutTokenPacket;
import iut.group42b.boardgames.game.packet.PlayerSurrenderPacket;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.Chronometer;
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

	@Override
	public void attachView(IView view) {
		if (!(view instanceof Connect4UIView)) {
			throw new IllegalArgumentException();
		}

		this.view = (Connect4UIView) view;

		this.canvas = new Connect4GridCanvas();

		UserProfile currentUserProfile = NetworkInterface.get().getSocketHandler().getUserProfile();
		this.view.getUserImageView().setImage(new Image(currentUserProfile.getImageUrl(),true));
		this.view.getUsernameText().setText(currentUserProfile.getUsername());
		this.view.getUserTockensRemainingText().setText(Messages.GAME_NUMBER_OF_TOKENS.use(0));
		this.view.getSurrenderButton().setOnAction(this);
		this.canvas.setTokenClickCallback(this);
		this.canvas.widthProperty().bind(this.view.getGridContainerStackPane().widthProperty());
		this.canvas.heightProperty().bind(this.view.getGridContainerStackPane().heightProperty());

		this.view.getGridContainerStackPane().getChildren().add(canvas); // add canvas witch contain grid into stackpane

		// Close the waiting dialog
		UserInterface.get().getCurrentDialog().close();
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

			canvas.updateLocalGrid(gridUpdatePacket.toSideGrid());

			updateWhichTurnToPlayText(gridUpdatePacket.getNextSideToPlay());
			updateRemainingTokenTexts();
		} else if (packet instanceof Connect4GameInfoPacket) {
			Connect4GameInfoPacket gameInfoPacket = (Connect4GameInfoPacket) packet;

			playerSide = gameInfoPacket.getMySide();
			opponentUserProfile = gameInfoPacket.getOpponentProfile();

			updateUserProfile();

			stopChronometerIfRunning();

			chronometer = new Chronometer(view.getTimerText()::setText);
			chronometer.setRunning(true);
			chronometer.start();
		}
	}

	private void stopChronometerIfRunning() {
		if (chronometer != null) {
			chronometer.setRunning(false);
			chronometer = null;
		}
	}

	private void updateUserProfile() {
		this.view.getOpponentNameText().setText(opponentUserProfile.getUsername());
		this.view.getOpponentImageView().setImage(new Image(opponentUserProfile.getImageUrl()));
	}

	private void updateWhichTurnToPlayText(Connect4Side nextSideToPlay) {
		String name = opponentUserProfile.getUsername();
		canvas.setHelperEnabled(false);
		if (playerSide == nextSideToPlay) {
			canvas.setHelperEnabled(true);

			name = NetworkInterface.get().getSocketHandler().getUserProfile().getUsername();
		}

		view.getWhoTurnText().setText(Messages.UI_CONNECT4_TURN.use(name));
	}

	private void updateRemainingTokenTexts() {
		// TODO Compute remaining token for each side and display proper values
		this.view.getOpponentTockensRemainingText().setText(Messages.GAME_NUMBER_OF_TOKENS.use(2));
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == view.getSurrenderButton()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle(Messages.UI_ALERT_TITLE_SURRENDER.use());
			alert.setHeaderText(Messages.UI_ALERT_HEADER_SURRENDER.use());
			alert.setContentText(Messages.UI_ALERT_CONTEXT_SURRENDER.use());

			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK){
				NetworkInterface.get().getSocketHandler().queue(new PlayerSurrenderPacket());
				UserInterface.get().set(new HomeView());
			} else {
				alert.close();
			}
		}
	}

	@Override
	public void onClick(Connect4GridCanvas canvas, int x, int y) {
		if (playerSide == null) {
			return;
		}

		NetworkInterface.get().getSocketHandler().queue(new Connect4PutTokenPacket(playerSide, x, y));
	}

}