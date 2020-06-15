package iut.group42b.boardgames.client.ui.page.social;

import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import javafx.event.ActionEvent;

public class SocialController implements IController, INetworkHandler {


	@Override
	public void handle(ActionEvent event) {

	}

	@Override
	public void attachView(IView view) {

	}


	@Override
	public void handlePacket(SocketHandler handler, IPacket packet) {

	}

	@Override
	public void onMount() {

	}

	@Override
	public void onUnmount() {

	}



}
