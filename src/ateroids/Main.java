package ateroids;

import ateroids.gameObjects.*;
import ateroids.graphics.AssetLoader;
import ateroids.ui.UIController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    private Pane root;
    private UIController uiController;
    private GameObjectFactory gameObjectFactory;
    private AssetLoader assetLoader;
    private GameState gameState;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Asteroid Game");
        root = new Pane();
        root.setPrefSize(Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT);
        stage.setScene(new Scene(root));
        stage.setFullScreen(Defines.FULLSCREEN);
        stage.show();
        initializeVariables();
        initializeKeyHandler();
    }

    private void initializeVariables() {
        assetLoader = new AssetLoader();
        gameObjectFactory = new GameObjectFactory(assetLoader);
        uiController = new UIController(root, assetLoader);
        gameState = new GameState(gameObjectFactory, root, uiController);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
    }

    private void initializeKeyHandler() {
        root.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                gameState.getPlayer().rotate(-10);
            } else if (event.getCode() == KeyCode.RIGHT) {
                gameState.getPlayer().rotate(10);
            } else if (event.getCode() == KeyCode.SPACE) {
                gameState.fire();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            } else if (event.getCode() == KeyCode.ENTER) {
                gameState.restartGame();
            }
        });
    }

    private void onUpdate() {
        gameState.checkIfPlayerIsAlive();
        gameState.checkBulletsCollisions();
        gameState.checkEnemyCollisions();
        gameState.updateObjects();
        gameState.spawnEnemy();
    }
}

