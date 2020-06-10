package iut.group42b.boardgames.game.impl.connect4.ui;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import javafx.event.ActionEvent;

public class Connect4UIController implements IController, INetworkHandler {

	/* UI */
	private Connect4UIView view;
	private Connect4GridCanvas canvas;

	@Override
	public void attachView(IView view) {
		if (!(view instanceof Connect4UIView)) {
			throw new IllegalArgumentException();
		}

		this.view = (Connect4UIView) view;

		this.canvas = new Connect4GridCanvas();
		this.canvas.widthProperty().bind(this.view.getGridContainerStackPane().widthProperty());
		this.canvas.heightProperty().bind(this.view.getGridContainerStackPane().heightProperty());

		// this.canvas.widthProperty().bind(this.view.getGridContainerStackPane().heightProperty().multiply(2));
		// this.canvas.heightProperty().bind(this.view.getGridContainerStackPane().widthProperty().divide(2));

		this.view.getGridContainerStackPane().getChildren().add(canvas);
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

	}

	@Override
	public void handle(ActionEvent event) {

	}

}