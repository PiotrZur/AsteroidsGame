package ateroids.GameObjects;

import ateroids.AssetLoader;
import ateroids.Defines;
import javafx.scene.image.ImageView;


public class Player extends GameObject {
    private int health;
    public Player(AssetLoader assetLoader) {
        super(new ImageView(assetLoader.getPlayer()), 70, 70);
        this.health = Defines.INITIAL_HEALTH;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void hit() {
        this.health -= 1;
    }
}
