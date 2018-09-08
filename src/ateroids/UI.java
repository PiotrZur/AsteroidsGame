package ateroids;

import ateroids.GameObjects.GameObject;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class UI {
    private Pane root;
    private StackPane healthBar;
    private Text scoreMeter;
    private Text controls;
    private int score;
    private Group gameOverUI;
    private GameObject player;
    private AssetLoader assetLoader;

    public UI(Pane root, GameObject player, AssetLoader assetLoader) {
        this.root = root;
        this.score = 0;
        this.healthBar = new StackPane();
        this.player = player;
        this.assetLoader = assetLoader;

        ImageView background = new ImageView(assetLoader.getBackground());

        this.controls = new Text(15, Defines.SCREEN_HEIGHT - 15, "Rotate ship: ←→     Fire: SPACE     Exit: ESCAPE");
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
        for (int i = 0; i < player.getHealth(); i++) {
            ImageView icon = new ImageView(assetLoader.getHealth());
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

    public void hide() {
        root.getChildren().removeAll(healthBar, scoreMeter, controls);
    }

    public void show() {
        root.getChildren().addAll(healthBar, scoreMeter, controls);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void showGameOverText() {
        if (gameOverUI == null) {
            gameOverUI = new Group();

            Text gameOver = new Text(0, 0, "GAME OVER");
            gameOver.setFont(new Font("Comic Sans MS Bold", 80));
            gameOver.setX((Defines.SCREEN_WIDTH - gameOver.getLayoutBounds().getWidth()) / 2);
            gameOver.setY(Defines.SCREEN_HEIGHT * 0.3);
            gameOver.setFill(Color.YELLOW);

            Text yourScore = new Text(0, 0, "Your Score: " + score);
            yourScore.setFont(new Font("Comic Sans MS Bold", 40));
            yourScore.setX((Defines.SCREEN_WIDTH - yourScore.getLayoutBounds().getWidth()) / 2);
            yourScore.setY(gameOver.getLayoutBounds().getHeight() + gameOver.getY());
            yourScore.setFill(Color.YELLOW);

            Text gameOverControls = new Text(0, 0, "Press ENTER to play again press ESC to exit");
            gameOverControls.setX((Defines.SCREEN_WIDTH - gameOverControls.getLayoutBounds().getWidth()) / 2);
            gameOverControls.setY(yourScore.getLayoutBounds().getHeight() + yourScore.getY());
            gameOverControls.setFont(new Font("Comic Sans MS Bold", 20));
            gameOverControls.setFill(Color.YELLOW);

            gameOverUI.getChildren().addAll(gameOver, yourScore, gameOverControls);
            root.getChildren().add(gameOverUI);
        } else {
            if (!root.getChildren().contains(gameOverUI))
                root.getChildren().add(gameOverUI);
        }
    }

    public void hideGameOverText() {
        root.getChildren().remove(gameOverUI);
    }
}
