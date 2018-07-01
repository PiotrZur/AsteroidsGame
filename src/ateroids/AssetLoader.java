package ateroids;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class AssetLoader {
    private Image player;
    private Image enemy;
    private Image bullet;

    private WritableImage writableBuffer;

    public AssetLoader() {
        player = new Image("player.png", 80, 80, true, true);

        enemy = new Image("asteroids.png");
        writableBuffer = new WritableImage(enemy.getPixelReader(), 0, 0, 70, 70);
        enemy = writableBuffer;

        bullet = new Image("player.png");
    }

    public Image getPlayer() {
        return player;
    }

    public Image getEnemy() {

        return enemy;
    }

    public Image getBullet() {
        return bullet;
    }
}
