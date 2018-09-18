package asteroids;

import asteroids.gameObjects.GameObject;
import javaFXTester.JFXClassRunner;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JFXClassRunner.class)
public class GameStateTest {
    GameState gamestate;
    GameObject player;
    Pane root;


    @Before
    public void setUp() {
        player = new GameObject(new Rectangle(0 ,0, 20, 40), new Point2D(0,0), 1);
        root = new Pane();
        gamestate = new GameState(player, root);

    }

    @org.junit.Test
    public void addEnemyFarFromPlayerTest() {
        GameObject enemy = new GameObject(new Rectangle(0 ,0, 10, 10), new Point2D(0,0), 1);
        gamestate.addEnemy(enemy, 0, 0);
        assertTrue(gamestate.getEnemies().contains(enemy));
        assertTrue(root.getChildren().contains(enemy.getView()));
    }

    @org.junit.Test
    public void addEnemyNearPlayerTest() {
        GameObject enemy = new GameObject(new Rectangle(0 ,0, 10, 10), new Point2D(0,0), 1);
        gamestate.addEnemy(enemy, Defines.SCREEN_WIDTH/2, Defines.SCREEN_HEIGHT/2);
        assertFalse(gamestate.getEnemies().contains(enemy));
        assertFalse(root.getChildren().contains(enemy.getView()));
    }

    @org.junit.Test
    public void bulletEnemyCollidingTest() {
        GameObject bullet = new GameObject(new Rectangle(0, 0, 10, 10), new Point2D(0,0), 1);
        GameObject enemy = new GameObject(new Rectangle(0, 0, 10, 10), new Point2D(0,0), 1);
        gamestate.addEnemy(enemy, 10, 10);
        gamestate.addBullet(bullet, 10, 10);
        gamestate.checkEnemyCollisions();
        gamestate.updateObjects();
        assertTrue(enemy.isDead());
        assertTrue(bullet.isDead());
        assertFalse(root.getChildren().contains(enemy.getView()));
        assertFalse(root.getChildren().contains(bullet.getView()));
        assertFalse(gamestate.getEnemies().contains(enemy));
        assertFalse(gamestate.getBullets().contains(bullet));
    }

    @org.junit.Test
    public void bulletEnemyNotCollidingTest() {
        GameObject bullet = new GameObject(new Rectangle(0, 0, 10, 10), new Point2D(0,0), 1);
        GameObject enemy = new GameObject(new Rectangle(0, 0, 10, 10), new Point2D(0,0), 1);
        gamestate.addEnemy(enemy, 10, 10);
        gamestate.addBullet(bullet, 25, 25);
        gamestate.checkEnemyCollisions();
        gamestate.updateObjects();
        assertFalse(enemy.isDead());
        assertFalse(bullet.isDead());
        assertTrue(root.getChildren().contains(enemy.getView()));
        assertTrue(root.getChildren().contains(bullet.getView()));
        assertTrue(gamestate.getEnemies().contains(enemy));
        assertTrue(gamestate.getBullets().contains(bullet));
    }

    @org.junit.Test
    public void bulletOutOfScreenTest() {
        GameObject bullet = new GameObject(new Rectangle(0, 0, 10, 10), new Point2D(0,0), 1);
        gamestate.addBullet(bullet, -10, -10);
        gamestate.checkBulletsAreOutOfScreen();
        gamestate.updateObjects();
        assertTrue(bullet.isDead());
        assertFalse(root.getChildren().contains(bullet.getView()));
        assertFalse(gamestate.getBullets().contains(bullet));
    }

    @org.junit.Test
    public void playerCollidingEnemyTest() {
        GameObject enemy = new GameObject(new Rectangle(0, 0, 10, 10), new Point2D(0,0), 1);
        gamestate.addEnemy(enemy, 0, 0 );
        enemy.move(Defines.SCREEN_WIDTH/2, Defines.SCREEN_HEIGHT/2);
        gamestate.checkEnemyCollisions();
        gamestate.updateObjects();
        assertTrue(enemy.isDead());
        assertFalse(root.getChildren().contains(enemy.getView()));
        assertFalse(gamestate.getEnemies().contains(enemy));
        assertTrue(player.isDead());
    }
}

