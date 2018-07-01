package ateroids.GameObjects;

import ateroids.AssetLoader;
import javafx.scene.image.ImageView;


public class Player extends GameObject {
    public Player(AssetLoader assetLoader) {
        super(new ImageView(assetLoader.getPlayer()), 70, 70);
    }
}
