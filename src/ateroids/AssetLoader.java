package ateroids;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

//Class that loads game assets to memory
public class AssetLoader {
    private Image player;
    private Image enemy;
    private Image background;
    private Image health;

    public AssetLoader() {
        player = new Image("player.png", 0.1 * Defines.SCREEN_WIDTH, 0.1 * Defines.SCREEN_HEIGHT, true, true);
        health = new Image("healthbar.png", 0.05 * Defines.SCREEN_WIDTH, 0.05 * Defines.SCREEN_HEIGHT, true, true);
        enemy = new Image("asteroids.png");
        background = new Image("background.jpg", Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT, false, false);
    }

    public Image getPlayer() {
        return player;
    }

    public Image getHealth() {
        return health;
    }

    public Image getEnemy() {
        int column = (int) Math.round(Math.random() * 4);
        int row = (int) Math.round(Math.random() * 3);
        PixelReader pixelReader = enemy.getPixelReader();
        WritableImage writableBuffer = new WritableImage( pixelReader, column * 64, row * 64, 64, 64);
        return writableBuffer;
    }

    public Image getBackground() {
        return background;
    }
}
