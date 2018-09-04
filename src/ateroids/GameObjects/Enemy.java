package ateroids.GameObjects;

import ateroids.AssetLoader;
import ateroids.Defines;
import javafx.scene.image.ImageView;


public class Enemy extends GameObject {
    private static final double SIZE = Defines.SCREEN_WIDTH / 11.5;
    public Enemy() {
        super(new ImageView(AssetLoader.getInstance().getEnemy()), SIZE, SIZE);
    }
}
