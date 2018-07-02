package ateroids;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class AssetLoader {
    private Image player;
    private Image enemy;
//    private Image bullet;
    private Image background;
    private Image health;

    private WritableImage writableBuffer;

    public AssetLoader() {
        player = new Image("player.png", 80, 80, true, true);

        health = new Image("healthbar.png", 30, 30, true, true);

        enemy = new Image("asteroids.png");
        writableBuffer = new WritableImage(enemy.getPixelReader(), 0, 0, 70, 70);
        enemy = writableBuffer;

//        bullet = new Image("player.png");

        background = new Image("background.jpg");

    }

    public Image getPlayer() {
        return player;
    }

    public Image getHealth() {
        return health;
    }

    public Image getEnemy() {

        return enemy;
    }

//    public Image getBullet() {
//        return bullet;
//    }

    public Image getBackground() {
        return background;
    }
}
