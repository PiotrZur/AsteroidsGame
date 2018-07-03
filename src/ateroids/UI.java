package ateroids;

import ateroids.GameObjects.Player;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UI {
    private AssetLoader assetLoader;
    private Pane root;
    private Player player;
    private StackPane healthBar;
    private Text scoreMeter;
    private int score;

    public UI(AssetLoader assetLoader, Pane root, Player player) {
        this.assetLoader = assetLoader;
        this.root = root;
        this.player = player;
        this.score = 0;
        this.healthBar = new StackPane();

        ImageView background = new ImageView(assetLoader.getBackground());

        Text controls = new Text(15, Defines.SCREEN_HEIGHT - 15, "Rotate ship: ←→     Fire: SPACE     Exit: ESCAPE");
        controls.setFont(new Font("Comic Sans MS Bold", 20));
        controls.setFill(Color.YELLOW);

        this.scoreMeter = new Text(Defines.SCREEN_WIDTH - 150, 35, "Score: " + Integer.toString(score));
        scoreMeter.setFont(new Font("Comic Sans MS Bold", 20));
        scoreMeter.setFill(Color.YELLOW);

        update();

        root.getChildren().addAll(background, controls, healthBar, scoreMeter);
    }

    public void update() {
        healthBar.getChildren().clear();
        for(int i = 0; i < player.getHealth(); i++) {
            ImageView icon =  new ImageView(assetLoader.getHealth());
            icon.setTranslateX(15 + (10 + assetLoader.getHealth().getWidth()) * i);
            icon.setTranslateY(15);
            healthBar.getChildren().add(icon);
        }

        scoreMeter.setText("Score: " + Integer.toString(score));
    }

    public void addScore(int value) {
        this.score += value;
        update();
    }


}
