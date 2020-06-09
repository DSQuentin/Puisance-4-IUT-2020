package iut.group42b.boardgames.client.ui.page.home;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.ui.list.game.GameListViewCellController;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.game.GameRegistry;
import iut.group42b.boardgames.game.IGame;
import iut.group42b.boardgames.network.SocketHandler;
import iut.group42b.boardgames.network.handler.INetworkHandler;
import iut.group42b.boardgames.network.packet.IPacket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class HomeController implements IController, INetworkHandler {

	/* Controllers */
	private GameListViewCellController gameListViewCellController;

	/* Variables */
	private HomeView view;

	@Override
	public void handle(ActionEvent event) {
		if (false) {

		} else {
			/*Parent source = (Parent) event.getSource();
			if (source.getUserData() instanceof HomeView.GameListViewCell) {
				HomeView.GameListViewCell cell = (HomeView.GameListViewCell) source.getUserData();

				System.out.println(cell.getCurrentItem());

				if (event.getSource() ==  cell.getActionButton()) {
					System.out.println("Button pressed");
				}

			}*/
		}


	}

	@Override
	public void attachView(IView view) {
		if (!(view instanceof HomeView)) {
			throw new IllegalArgumentException();
		}

		this.view = (HomeView) view;

		ObservableList<IGame> gameObservableList = FXCollections.observableArrayList();
		gameObservableList.addAll(GameRegistry.get().all());

		gameListViewCellController = new GameListViewCellController();

		this.view.getGamesListView().setItems(gameObservableList);
		this.view.getGamesListView().setCellFactory(gameListViewCellController.cellFactory());
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