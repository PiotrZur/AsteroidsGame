package ateroids;

import ateroids.GameObjects.Bullet;
import ateroids.GameObjects.Enemy;
import ateroids.GameObjects.GameObject;
import ateroids.GameObjects.Player;
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
    private List<GameObject> enemies = new ArrayList<>();
    private GameObject player;

    public AssetLoader assetLoader = new AssetLoader();

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(600.0, 600.0);

        player = new Player(assetLoader);
        player.setVelocity(new Point2D(1, 0));

        addGameObject(player, 300, 300);
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
        enemies.add(enemy);
        addGameObject(enemy, x, y);
    }
    private void onUpdate() {
        for(GameObject bullet : bullets) {
            for(GameObject enemy : enemies) {
               if(bullet.isColliding(enemy)) {
                   bullet.setAlive(false);
                   enemy.setAlive(false);

                   root.getChildren().removeAll(bullet.getView(), enemy.getView());
               }
            }
        }
        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);
        player.update();

        if(Math.random() < 0.01) {
           addEnemy(new Enemy(assetLoader), Math.random() * root.getPrefWidth(), Math.random()*root.getPrefHeight());
        }
    }
    @Override
    public void start(Stage stage) {
        stage.setTitle("Asteroid Game");
        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(event ->  {
            if(event.getCode() == KeyCode.LEFT) {
                player.rotateLeft();
            } else if (event.getCode() == KeyCode.RIGHT) {
                player.rotateRight();
            } else if (event.getCode() == KeyCode.SPACE) {
                Bullet bullet = new Bullet(assetLoader);
                bullet.setVelocity(player.getVelocity().normalize().multiply(5.0));
                addBullet(bullet, player.getView().getTranslateX() + player.getWidth()/2 + Math.cos(Math.toRadians(player.getRotation()))*player.getWidth()/2, player.getView().getTranslateY() + player.getHeight()/2 + Math.sin(Math.toRadians(player.getRotation()))*player.getWidth()/2);
            }
        });
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
