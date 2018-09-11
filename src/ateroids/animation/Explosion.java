package ateroids.animation;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Explosion {
    private AnimatedImage animatedImage;
    private Pane root;

    public Explosion(Pane root) {
        this.root = root;
    }
    public void start(double x, double y) {
        this.animatedImage = new AnimatedImage(new Image("explosion.png"), 19, 64, 500000000L, 2);
        animatedImage.getShowedImage().setTranslateX(x);
        animatedImage.getShowedImage().setTranslateY(y);
        root.getChildren().add(animatedImage.getShowedImage());
    }
    public void update() {
        if (animatedImage != null && animatedImage.isRunning()) {
            animatedImage.animate();
        } else if (animatedImage != null) {
            root.getChildren().remove(animatedImage.getShowedImage());
            animatedImage = null;
        }
    }

    public boolean isFinished() {
        if(animatedImage!= null) {
            return false;
        }
        return true;
    }
}
