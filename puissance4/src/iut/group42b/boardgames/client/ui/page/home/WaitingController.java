package iut.group42b.boardgames.client.ui.page.home;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.resources.Resource;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.game.packet.matchmaking.MatchmakingLeavePacket;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import javafx.event.ActionEvent;


public class WaitingController implements IController, INetworkHandler {


	/* Variables */
	private WaitingView view;


	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getCancelButton()) {
			this.onCancel();
		}


	}

	private void onCancel() {
		NetworkInterface.get().getSocketHandler().queue(new MatchmakingLeavePacket());

		UserInterface.get().closeCurrentDialog();
	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof WaitingView)) {
			throw new IllegalArgumentException();
		}

		this.view = (WaitingView) view;

		this.view.getCancelButton().setOnAction(this);
		this.view.getLogoGameImageView().setImage(Resource.loadImage(this.view.getGame().picturePath()));
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

}