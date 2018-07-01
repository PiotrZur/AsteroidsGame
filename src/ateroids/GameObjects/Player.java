package ateroids.GameObjects;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends GameObject {
    public Player() {
        super(new Rectangle(30, 30, Color.PURPLE));
    }
}
