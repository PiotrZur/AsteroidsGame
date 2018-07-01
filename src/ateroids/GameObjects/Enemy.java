package ateroids.GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Enemy extends GameObject {
    public Enemy() {
        super(new Circle(20, 20, 20, Color.RED));
    }
}
