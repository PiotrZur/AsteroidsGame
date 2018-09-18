package asteroids;


import asteroids.animation.Explosion;
import asteroids.gameObjects.GameObject;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private GameObject player;
    private List<GameObject> bullets;
    private List<GameObject> enemies;
    private Pane root;
    private ArrayList<Explosion> explosions;
    private int score;

    public GameState(GameObject player, Pane root) {
        this.root = root;
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        this.player = player;
        this.score = 0;
        explosions = new ArrayList<>();
        addGameObject(player, Defines.SCREEN_WIDTH / 2, Defines.SCREEN_HEIGHT / 2);
    }

    public void addGameObject(GameObject object, double x, double y) {
        object.move(x, y);
        root.getChildren().add(object.getView());
    }

    public void addBullet(GameObject bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    public void addEnemy(GameObject enemy, double x, double y) {
        if (x > player.getView().getTranslateX() + player.getWidth() * 2 ||
                x < player.getView().getTranslateX() - player.getWidth()) {
            if (y > player.getView().getTranslateY() + player.getHeight() * 2 ||
                    y < player.getView().getTranslateX() - player.getHeight()) {
                enemies.add(enemy);
                addGameObject(enemy, x, y);
            }
        }

    }

    public void fire(GameObject bullet) {
        addBullet(bullet, player.getView().getTranslateX() + player.getWidth() / 2 + Math.cos(Math.toRadians(player.getRotation())) * player.getWidth() / 2,
                player.getView().getTranslateY() + player.getHeight() / 2 + Math.sin(Math.toRadians(player.getRotation())) * player.getWidth() / 2);
    }

    public GameObject getPlayer() {
        return player;
    }

    public void restartGame() {
            player.revive(Defines.INITIAL_PLAYER_HEALTH);
            player.getView().setTranslateX(Defines.SCREEN_WIDTH / 2);
            player.getView().setTranslateX(Defines.SCREEN_HEIGHT / 2);
            root.getChildren().add(player.getView());
            score = 0;
    }

    public boolean checkIfPlayerIsAlive() {
        if (player.isDead()) {
            root.getChildren().removeAll(player.getView());
            return false;
        }
        return true;
    }

    public void checkBulletsAreOutOfScreen() {
        for (GameObject bullet : bullets) {
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
                Explosion explosion = new Explosion(root);
                explosion.start(player.getView().getTranslateX(), player.getView().getTranslateY());
                explosions.add(explosion);
                for (GameObject enemyToRemove : enemies) {
                    enemyToRemove.hit();
                    root.getChildren().removeAll(enemyToRemove.getView());
                }
                enemies.clear();
                break;
            }
            for (GameObject bullet : bullets) {
                if (bullet.isColliding(enemy)) {
                    bullet.hit();
                    enemy.hit();
                    Explosion explosion = new Explosion(root);
                    explosion.start(enemy.getView().getTranslateX(), enemy.getView().getTranslateY());
                    explosions.add(explosion);
                    root.getChildren().removeAll(bullet.getView(), enemy.getView());
                    score += Defines.SCORE_PER_KILL;
                }
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
            for(Explosion explosion : explosions) {
                explosion.update();
            }
            explosions.removeIf(Explosion::isFinished);
    }

    public List<GameObject> getBullets() {
        return bullets;
    }

    public List<GameObject> getEnemies() {
        return enemies;
    }

    public int getScore() {
        return score;
    }
}
