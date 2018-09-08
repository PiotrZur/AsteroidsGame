package ateroids;

import ateroids.GameObjects.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Pane root;
    private List<GameObject> bullets = new ArrayList<>();
    private List<GameObject> enemies = new ArrayList<>();
    private AnimatedImage explosion;
    private UI ui;
    private  GameObject player;
    private GameObjectFactory gameObjectFactory;
    AssetLoader assetLoader;

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT);
        assetLoader = new AssetLoader();
        gameObjectFactory = new GameObjectFactory(assetLoader);
        player = gameObjectFactory.createPlayer();

        ui = new UI(root, player, assetLoader);

        addGameObject(player, Defines.SCREEN_WIDTH / 2, Defines.SCREEN_HEIGHT / 2);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        return root;
    }

    private void addGameObject(GameObject object, double x, double y) {
        object.move(x, y);
        root.getChildren().add(object.getView());
    }

    private void addBullet(GameObject bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    private void addEnemy(GameObject enemy, double x, double y) {
        if (x > player.getView().getTranslateX() + player.getWidth() * 2 ||
                x < player.getView().getTranslateX() - player.getWidth()) {
            if (y > player.getView().getTranslateY() + player.getHeight() * 2 ||
                    y < player.getView().getTranslateX() - player.getHeight()) {
                enemies.add(enemy);
                addGameObject(enemy, x, y);
            }
        }

    }

    private void onUpdate() {
        if (player.isDead()) {
            ui.showGameOverText();
            root.getChildren().removeAll(player.getView());
            ui.hide();

        } else {
            for (GameObject bullet : bullets) {
                for (GameObject enemy : enemies) {
                    if (bullet.isColliding(enemy)) {
                        bullet.hit();
                        enemy.hit();
                        root.getChildren().removeAll(bullet.getView(), enemy.getView());
                        ui.addScore(Defines.SCORE_PER_KILL);
                    }
                }
                if (bullet.isOutOfScreen()) {
                    bullet.hit();
                    root.getChildren().removeAll(bullet.getView());
                }
            }
            for (GameObject enemy : enemies) {
                enemy.isOutOfScreen();
                if (player.isColliding(enemy)) {
                    player.hit();
                    explosion = new AnimatedImage(new Image("explosion.png"), 19, 64, 1000000000, 2);
                    explosion.getShowedImage().setTranslateX(player.getView().getTranslateX());
                    explosion.getShowedImage().setTranslateY(player.getView().getTranslateY());
                    root.getChildren().addAll(explosion.getShowedImage());

                    for (GameObject enemyToRemowe : enemies) {
                        enemyToRemowe.hit();
                        root.getChildren().removeAll(enemyToRemowe.getView());
                    }
                    enemies.clear();
                    break;
                }
            }


            bullets.removeIf(GameObject::isDead);
            enemies.removeIf(GameObject::isDead);

            bullets.forEach(GameObject::update);
            enemies.forEach(GameObject::update);
            player.update();
            player.isOutOfScreen();


            if (Math.random() < Defines.ENEMY_SPAWN_RATE) {
                GameObject newEnemy = gameObjectFactory.createEnemy();
                addEnemy(newEnemy, Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
            }
            ui.update();
        }
        if (explosion != null && explosion.isRunning()) {
            explosion.animate();
        } else if (explosion != null) {
            root.getChildren().removeAll(explosion.getShowedImage());
            explosion = null;
        }
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Asteroid Game");
        stage.setScene(new Scene(createContent()));
        stage.setFullScreen(Defines.FULLSCREEN);
        stage.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                player.rotate(-10);
            } else if (event.getCode() == KeyCode.RIGHT) {
                player.rotate(10);
            } else if (event.getCode() == KeyCode.SPACE) {
                GameObject bullet = gameObjectFactory.createBullet(player.getVelocity().normalize().multiply(15.0));
                addBullet(bullet, player.getView().getTranslateX() + player.getWidth() / 2 + Math.cos(Math.toRadians(player.getRotation())) * player.getWidth() / 2,
                        player.getView().getTranslateY() + player.getHeight() / 2 + Math.sin(Math.toRadians(player.getRotation())) * player.getWidth() / 2);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            } else if (event.getCode() == KeyCode.ENTER) {
                if (player.getHealth() <= 0) {
                    player.revive(Defines.INITIAL_PLAYER_HEALTH);
                    player.getView().setTranslateX(Defines.SCREEN_WIDTH / 2);
                    player.getView().setTranslateX(Defines.SCREEN_HEIGHT / 2);
                    root.getChildren().add(player.getView());
                    ui.show();
                    ui.setScore(0);
                    ui.hideGameOverText();

                }
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
