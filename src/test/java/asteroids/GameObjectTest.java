package asteroids;


import asteroids.gameObjects.GameObject;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import org.junit.Before;

import static org.junit.Assert.*;

public class GameObjectTest {
    Point2D velocity;
    Rectangle view;
    GameObject gameObject;

    @Before
    public void setUp() throws Exception {
        velocity = new Point2D(2, 3);
        view = new Rectangle(10, 20, 50, 50);
        gameObject = new GameObject(view, velocity, 2);
    }

    @org.junit.Test
    public void updateTest() {
        gameObject.update();
        assertEquals(2, gameObject.getView().getTranslateX(), 0.1);
        assertEquals(3, gameObject.getView().getTranslateY(), 0.1);
    }

    @org.junit.Test
    public void rotateTest() {
        double objectWidth = gameObject.getWidth();
        gameObject.rotate(10.0);
        assertEquals(10, gameObject.getRotation(), 0.1);
        assertEquals(10, ((Rotate) gameObject.getView().getTransforms().get(0)).getAngle(), 0.1);
    }

    @org.junit.Test
    public void rotateOver360Test() {
        gameObject.rotate(390.0);
        assertEquals(30, gameObject.getRotation(), 0.1);
    }

    @org.junit.Test
    public void hitTest() {
        gameObject.hit();
        assertFalse(gameObject.isDead());
        assertEquals(1, gameObject.getHealth(), 0.1);
        gameObject.hit();
        assertTrue(gameObject.isDead());
        assertEquals(0, gameObject.getHealth(), 0.1);
    }

    @org.junit.Test
    public void reviveTest() {
        gameObject.hit();
        gameObject.hit();
        assertTrue(gameObject.isDead());
        gameObject.revive(5);
        assertEquals(5, gameObject.getHealth(), 0.1);
    }

    @org.junit.Test
    public void isCollidingTest() {
        Rectangle anotherRectangle = new Rectangle(10, 20, 50, 50);
        GameObject anotherGameObject = new GameObject(anotherRectangle, null, 5);
        assertTrue(gameObject.isColliding(anotherGameObject));
    }

    @org.junit.Test
    public void outOfScreenLeftBorderTest() {
        gameObject.move(-10, 0);
        gameObject.isOutOfScreen();
        assertEquals(Defines.SCREEN_WIDTH - 10.0, gameObject.getView().getTranslateX(), 1);
    }

    @org.junit.Test
    public void outOfScreenRightBorderTest() {
        gameObject.move(10 + Defines.SCREEN_WIDTH, 0);
        gameObject.isOutOfScreen();
        assertEquals(10.0, gameObject.getView().getTranslateX(), 1);
    }

    @org.junit.Test
    public void outOfScreenUpperBorderTest() {
        gameObject.move(0, -10);
        gameObject.isOutOfScreen();
        assertEquals(Defines.SCREEN_HEIGHT - 10.0, gameObject.getView().getTranslateY(), 1);
    }

    @org.junit.Test
    public void outOfScreenBottenBorderTest() {
        gameObject.move(0, Defines.SCREEN_HEIGHT + 10);
        gameObject.isOutOfScreen();
        assertEquals(10.0, gameObject.getView().getTranslateY(), 1);
    }
}
