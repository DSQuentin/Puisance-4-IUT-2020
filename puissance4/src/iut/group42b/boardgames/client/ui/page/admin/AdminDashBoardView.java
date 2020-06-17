package iut.group42b.boardgames.client.ui.page.admin;

import iut.group42b.boardgames.client.ui.mvc.AbstractView;
import iut.group42b.boardgames.client.ui.mvc.IController;

import iut.group42b.boardgames.client.ui.page.home.HomeController;
import iut.group42b.boardgames.util.CPU;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


public class AdminDashBoardView extends AbstractView {

    private final ImageView logoImageView;
    private final ImageView profileImageView;
    private final Button logOut;
    private final Text gamesPlayedGame1;
    private final Text gamesPlayedGame2;
    private final Text gamesPlayedGame3;
    private final Text topUser;
    private final Text topUserScore;
    private final ImageView imageGraph;
    private final Button manageButton;
    private LineChart cpuLoad;
    private final ScheduledExecutorService scheduledExecutorService;
    private XYChart.Series series;
    private final CategoryAxis xaxis;
    private final NumberAxis yaxis;

    public AdminDashBoardView() {
        this.logoImageView = (ImageView) this.findById("logo");
        this.profileImageView =  this.findById("admin-dashboard-imageview-profile");
        this.gamesPlayedGame1 = (Text) this.findById("admin-dashboard-games-played-game1");
        this.gamesPlayedGame2 = (Text) this.findById("admin-dashboard-games-played-game2");
        this.gamesPlayedGame3 = (Text) this.findById("admin-dashboard-games-played-game3");
        this.topUser = (Text) this.findById("admin-dashboard-top-user");
        this.topUserScore = (Text) this.findById("admin-dashboard-top-user-score");
        this.imageGraph = (ImageView) this.findById("admin-dashboard-imagegraph");
        this.manageButton = (Button) this.findById("admin-dashboard-manage-users");
        this.logOut = (Button) this.findById("admin-dashboard-logout");
        this.cpuLoad = (LineChart) this.findById("admin-dashboard-linechart-cpu");
        this.xaxis = (CategoryAxis) this.findById("admin-dashboard-linechart-xaxis");
        this.yaxis = (NumberAxis) this.findById("admin-dashboard-linechart-yaxis");
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        linechartSetup();
    }

    public void linechartSetup(){
        xaxis.setLabel("Time");
        xaxis.setAnimated(false);
        yaxis.setLabel("Cpu Load %");
        yaxis.setAnimated(false);
        this.cpuLoad.setAnimated(false);
        this.series = new XYChart.Series<>();
        this.series.setName("Load");
        this.cpuLoad.getData().add(this.series);
    }

    /*public void chartUpdate(){

    }*/

    public ScheduledExecutorService getScheduledExecutorService() {
        return this.scheduledExecutorService;
    }

	public XYChart.Series getSeries() { return this.series;	}

	public LineChart getCpuLoad() { return this.cpuLoad; }

    public ImageView getProfileImageView() { return this.profileImageView; }

    public ImageView getLogoImageView() { return this.logoImageView; }

    public Button getLogOut() { return this.logOut; }

    public Button getManageButton() { return this.manageButton; }

    public Text getGamesPlayedGame1() { return this.gamesPlayedGame1; }

    public Text getGamesPlayedGame2() { return this.gamesPlayedGame2; }

    public Text getGamesPlayedGame3() { return this.gamesPlayedGame3; }

    public Text getTopUser() { return this.topUser; }

    public Text getTopUserScore() { return this.topUserScore; }

    public ImageView getImageGraph() { return this.imageGraph; }

    @Override
    public String getViewPath() { return "admin-dashboard.fxml"; }

    @Override
    public IController createController() { return new AdminDashBoardController(); }

}
