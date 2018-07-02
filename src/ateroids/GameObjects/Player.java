package ateroids.GameObjects;

import ateroids.AssetLoader;
import javafx.scene.image.ImageView;


public class Player extends GameObject {
    private int health;
    public Player(AssetLoader assetLoader) {
        super(new ImageView(assetLoader.getPlayer()), 70, 70);
        this.health = 5;
    }

    public int getHealth() {
        return health;
    }

    public void hit() {
        this.health -= 1;
    }
}
