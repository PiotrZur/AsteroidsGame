package ateroids;

import ateroids.GameObjects.GameObject;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

public class AnimatedImage {
    private WritableImage[] frames;
    private double duration;
    private boolean running;
    private long lastFrameTime;
    private int frameCounter;
    private ImageView showedImage;
    private double destinedSize;

    public AnimatedImage (Image spriteImage, int framesNumber,
                          int frameSize, long duration, double frameRatio) {
        this.running = true;
        this.frames = new WritableImage[framesNumber];
        this.duration = duration;
        this.destinedSize = frameSize * frameRatio;

        int spriteImageRow = 0;
        int spritesInRow = (int) (spriteImage.getWidth() / frameSize);
        for(int i = 0; i < framesNumber; i ++) {
        if((i + 1 - spriteImageRow * spritesInRow) > spritesInRow) {
            spriteImageRow++;
        }
        frames[i] = new WritableImage(spriteImage.getPixelReader(),
                (i - spriteImageRow*spritesInRow)* frameSize,
                spriteImageRow*frameSize, frameSize, frameSize);
        }
        frameCounter = 0;
        showedImage = new ImageView(frames[frameCounter]);
        showedImage.setFitHeight(destinedSize);
        showedImage.setFitWidth(destinedSize);
        lastFrameTime = 0;

    }
    public void animate() {
        if (lastFrameTime == 0) {
            lastFrameTime = System.nanoTime();
        }
        if ((System.nanoTime() - lastFrameTime) >= (duration / frames.length)) {
            frameCounter++;
            if ((frameCounter + 1) > frames.length) {
                running = false;
            } else {
                showedImage.setImage(frames[frameCounter]);
            }

        }
    }

    public boolean isRunning() {
        return running;
    }

    public ImageView getShowedImage() {
        return showedImage;
    }
}
