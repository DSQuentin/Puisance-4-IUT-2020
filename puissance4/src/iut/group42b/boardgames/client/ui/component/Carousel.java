package iut.group42b.boardgames.client.ui.component;

import iut.group42b.boardgames.client.resources.Resource;
import javafx.animation.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;


public class Carousel extends AnchorPane{


    private List<ImageView> slides;

    public Carousel(){
        super();
        this.slides=new ArrayList<>();
        this.initialize(3);
        this.start();
    }

    public void initialize(Integer nbImages){

        for (int i = 1; i < nbImages + 1; i++) {
            ImageView imageView = new ImageView(Resource.loadImage(i+".jpg"));
            imageView.setFitWidth(300);
            imageView.setPreserveRatio(true);
            this.slides.add(imageView);
        }
    }

    public void start() {

        SequentialTransition slideshow = new SequentialTransition();

        for (ImageView slide : slides) {

            SequentialTransition sequentialTransition = new SequentialTransition();

            FadeTransition fadeIn = getFadeTransition(slide, 0.0, 1.0, 1000);
            PauseTransition stayOn = new PauseTransition(Duration.millis(4000));
            FadeTransition fadeOut = getFadeTransition(slide, 1.0, 0.0, 1000);

            sequentialTransition.getChildren().addAll(fadeIn, stayOn, fadeOut);
            slide.setOpacity(0);

            this.getChildren().add(slide);
            slideshow.getChildren().add(sequentialTransition);
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            slideshow.play();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }

    public FadeTransition getFadeTransition(ImageView imageView, double fromValue, double toValue, int durationInMilliseconds) {

        FadeTransition ft = new FadeTransition(Duration.millis(durationInMilliseconds), imageView);
        ft.setFromValue(fromValue);
        ft.setToValue(toValue);

        return ft;
    }
}
