package ateroids.GameObjects;

import ateroids.AssetLoader;
import ateroids.Defines;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends GameObject {
    private static final double SIZE = 0.003 * Defines.SCREEN_WIDTH;
    public Bullet() {
        super(new Circle(0, 0, SIZE, Color.YELLOW), SIZE * 2, SIZE * 2);
    }

}
