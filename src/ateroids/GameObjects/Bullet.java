package ateroids.GameObjects;

import ateroids.AssetLoader;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends GameObject {
    public Bullet(AssetLoader assetLoader) {
        super(new Circle(0, 0, 5, Color.YELLOW), 5, 5);
    }
}
