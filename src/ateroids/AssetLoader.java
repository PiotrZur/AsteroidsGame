package ateroids;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

//Singleton that loads game assets to memory
public class AssetLoader {
    private static AssetLoader instance = new AssetLoader();

    private Image player;
    private Image enemy;
    private Image background;
    private Image health;

    private WritableImage writableBuffer;

    public AssetLoader() {
        player = new Image("player.png", 0.1 * Defines.SCREEN_WIDTH, 0.1 * Defines.SCREEN_HEIGHT, true, true);

        health = new Image("healthbar.png", 0.05 * Defines.SCREEN_WIDTH, 0.05 * Defines.SCREEN_HEIGHT, true, true);

        enemy = new Image("asteroids.png");
        writableBuffer = new WritableImage(enemy.getPixelReader(), 0, 0, 70, 70);
        enemy = writableBuffer;

        background = new Image("background.jpg", Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT, false, false);

    }

    public static AssetLoader getInstance() {
        return instance;
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

    public Image getBackground() {
        return background;
    }
}
