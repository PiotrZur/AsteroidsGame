package ateroids;

import ateroids.GameObjects.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AsteroidsApp extends Application {

    private Pane root;
    private List<GameObject> bullets = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private Player player;
    private UI ui;

    public AssetLoader assetLoader = new AssetLoader();

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(Defines.SCREEN_WIDTH, Defines.SCREEN_HEIGHT);


        player = new Player(assetLoader);
        player.setVelocity(new Point2D(1, 0));

        ui = new UI(assetLoader, root, player);

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

    private void addBullet(Bullet bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    private void addEnemy(Enemy enemy, double x, double y) {
        enemy.setVelocity(new Point2D(1, 0));
        enemies.add(enemy);
        addGameObject(enemy, x, y);
    }

    private void onUpdate() {
        for (GameObject bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (bullet.isColliding(enemy)) {
                    bullet.setAlive(false);
                    enemy.setAlive(false);
                    root.getChildren().removeAll(bullet.getView(), enemy.getView());
                    ui.addScore(Defines.SCORE);
                }
            }
            if (bullet.isOutOfScreen()) {
                bullet.setAlive(false);
                root.getChildren().removeAll(bullet.getView());
            }
        }
        for (Enemy enemy : enemies) {
            enemy.isOutOfScreen();
            if (player.isColliding(enemy)) {
                player.hit();
            }
        }


        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);
        player.update();
        player.isOutOfScreen();

        if (Math.random() < Defines.ENEMY_SPAWN_RATE) {
            addEnemy(new Enemy(assetLoader), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
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
                Bullet bullet = new Bullet(assetLoader);
                bullet.setVelocity(player.getVelocity().normalize().multiply(15.0));
                addBullet(bullet, player.getView().getTranslateX() + player.getWidth() / 2 + Math.cos(Math.toRadians(player.getRotation())) * player.getWidth() / 2, player.getView().getTranslateY() + player.getHeight() / 2 + Math.sin(Math.toRadians(player.getRotation())) * player.getWidth() / 2);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }
        });
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
