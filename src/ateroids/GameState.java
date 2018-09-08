package ateroids;

import ateroids.animation.Explosion;
import ateroids.gameObjects.GameObject;
import ateroids.gameObjects.GameObjectFactory;
import ateroids.ui.UIController;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private GameObject player;
    private GameObjectFactory gameObjectFactory;
    private List<GameObject> bullets;
    private List<GameObject> enemies;
    private Pane root;
    private UIController uiController;
    private Explosion explosion;

    public GameState(GameObjectFactory gameObjectFactory, Pane root, UIController uiController) {
        this.gameObjectFactory = gameObjectFactory;
        this.root = root;
        this.uiController = uiController;
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        player = gameObjectFactory.createPlayer();
        addGameObject(player, Defines.SCREEN_WIDTH / 2, Defines.SCREEN_HEIGHT / 2);
        explosion = new Explosion(root);
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

    public void fire() {
        GameObject bullet = gameObjectFactory.createBullet(player.getVelocity().normalize().multiply(15.0));
        addBullet(bullet, player.getView().getTranslateX() + player.getWidth() / 2 + Math.cos(Math.toRadians(player.getRotation())) * player.getWidth() / 2,
                player.getView().getTranslateY() + player.getHeight() / 2 + Math.sin(Math.toRadians(player.getRotation())) * player.getWidth() / 2);
    }

    public GameObject getPlayer() {
        return player;
    }

    public void restartGame() {
        if (player.getHealth() <= 0) {
            player.revive(Defines.INITIAL_PLAYER_HEALTH);
            player.getView().setTranslateX(Defines.SCREEN_WIDTH / 2);
            player.getView().setTranslateX(Defines.SCREEN_HEIGHT / 2);
            root.getChildren().add(player.getView());
            uiController.show();
            uiController.setScore(0);
            uiController.hideGameOverText();
        }
    }

    public void checkIfPlayerIsAlive() {
        if (player.isDead()) {
            uiController.showGameOverText();
            root.getChildren().removeAll(player.getView());
            uiController.hide();
        }
    }

    public void checkBulletsCollisions() {
        for (GameObject bullet : bullets) {
            for (GameObject enemy : enemies) {
                if (bullet.isColliding(enemy)) {
                    bullet.hit();
                    enemy.hit();
                    root.getChildren().removeAll(bullet.getView(), enemy.getView());
                    uiController.addScore(Defines.SCORE_PER_KILL);
                }
            }
            if (bullet.isOutOfScreen()) {
                bullet.hit();
                root.getChildren().removeAll(bullet.getView());
            }
        }
    }

    public void checkEnemyCollisions() {
        for (GameObject enemy : enemies) {
            enemy.isOutOfScreen();
            if (player.isColliding(enemy)) {
                player.hit();
                uiController.updateHealth(player.getHealth());
                explosion.start(player.getView().getTranslateX(), player.getView().getTranslateY());
                for (GameObject enemyToRemowe : enemies) {
                    enemyToRemowe.hit();
                    root.getChildren().removeAll(enemyToRemowe.getView());
                }
                enemies.clear();
                break;
            }
        }
    }

    public void updateObjects() {
        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);
        player.update();
        player.isOutOfScreen();
    }

    public void updateAnimations() {
        explosion.update();
    }

    public void spawnEnemy() {
        if (Math.random() < Defines.ENEMY_SPAWN_RATE) {
            GameObject newEnemy = gameObjectFactory.createEnemy();
            addEnemy(newEnemy, Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
        }
    }
}
