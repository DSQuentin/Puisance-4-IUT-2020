package iut.group42b.boardgames.client.ui.page.admin;

import iut.group42b.boardgames.client.manager.NetworkInterface;
import iut.group42b.boardgames.client.manager.UserInterface;
import iut.group42b.boardgames.client.ui.mvc.IController;
import iut.group42b.boardgames.client.ui.mvc.IView;
import iut.group42b.boardgames.client.ui.page.home.HomeView;
import iut.group42b.boardgames.client.ui.page.logout.LogoutView;
import iut.group42b.boardgames.social.model.UserProfile;
import iut.group42b.boardgames.util.CPU;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminDashBoardController implements IController {
	private AdminDashBoardView view;
	private boolean active;
	private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == this.view.getLogOut()) {
			this.active = false;
			UserInterface.get().set(new LogoutView());
		}

	}

	@Override
	public void attachView(IView view) {
		this.active = true;
		if (!(view instanceof AdminDashBoardView)) {
			this.active = false;
			throw new IllegalArgumentException();
		}
		List<Integer> load = new LinkedList<Integer>();
		this.view = (AdminDashBoardView) view;
		UserProfile userProfile = NetworkInterface.get().getSocketHandler().getUserProfile();
		this.view.getLogOut().setOnAction(this);
		this.view.getProfileImageView().setImage(new Image(NetworkInterface.get().getSocketHandler().getUserProfile().getImageUrl(), true));
		if (this.active) {
			this.view.getScheduledExecutorService().scheduleAtFixedRate(() -> {
				//Integer random = ThreadLocalRandom.current().nextInt(10);
				Platform.runLater(() -> {
					Date now = new Date();
					this.view.getSeries().getData().add(
							new XYChart.Data<>(this.simpleDateFormat.format(now), CPU.getLoadPercentageWindows()));
					if (this.view.getSeries().getData().size() > 10)
						this.view.getSeries().getData().remove(0);
				});
			}, 0, 5000, TimeUnit.MILLISECONDS);
		}
		this.view.getLogoImageView().addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			this.active = false;
			UserInterface.get().set(new HomeView());
		});
	}


	@Override
	public void onMount() {

	}

	@Override
	public void onUnmount() {

	}
}
