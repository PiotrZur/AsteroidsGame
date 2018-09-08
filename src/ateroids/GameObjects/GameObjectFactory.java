package ateroids.GameObjects;

import ateroids.AssetLoader;
import ateroids.Defines;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

//class used to construct GameObjects
public class GameObjectFactory {
    private AssetLoader assetLoader;

    public GameObjectFactory(AssetLoader assetLoader) {
        this.assetLoader = assetLoader;
    }

    public GameObject createPlayer() {
        return new GameObject(new ImageView(assetLoader.getPlayer()), new Point2D(1, 0), Defines.INITIAL_PLAYER_HEALTH);
    }

    public GameObject createEnemy() {
        return new GameObject(new ImageView(assetLoader.getEnemy()), new Point2D(Math.random(), Math.random()), Defines.INITIAL_ENEMY_HEALTH);
    }

    public GameObject createBullet(Point2D velocity) {
        double size = 0.003 * Defines.SCREEN_WIDTH;
        return new GameObject(new Circle(0,0, size, Color.YELLOW), velocity, 1);
    }
}
