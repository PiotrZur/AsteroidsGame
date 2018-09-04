package ateroids.GameObjects;

import ateroids.AssetLoader;
import ateroids.Defines;
import javafx.scene.image.ImageView;


public class Player extends GameObject {
    private static final double SIZE = Defines.SCREEN_WIDTH / 11.5;
    private static Player instance = new Player();

    private int health;

    private Player() {
        super(new ImageView(AssetLoader.getInstance().getPlayer()), SIZE, SIZE);
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

    public static Player getInstance() {
        return instance;
    }
}
