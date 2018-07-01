package ateroids.GameObjects;

import ateroids.AssetLoader;
import javafx.scene.image.ImageView;


public class Enemy extends GameObject {
    public Enemy(AssetLoader assetLoader) {
        super(new ImageView(assetLoader.getEnemy()), 70, 70);
    }
}
