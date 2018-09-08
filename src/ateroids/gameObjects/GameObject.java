package ateroids.gameObjects;

import ateroids.Defines;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;

// class for all objects in game, controls movement, rotation and collision.
public class GameObject {
    private Node view;
    private Point2D velocity;
    private double width;
    private double height;
    private boolean alive;
    private double rotation;
    private int health;

    public GameObject(Node view, Point2D velocity, int health) {
        this.view = view;
        this.velocity = velocity;
        this.width = view.getBoundsInParent().getWidth();
        this.height = view.getBoundsInParent().getHeight();
        this.alive = true;
        this.rotation = 0;
        this.health = health;
    }

    public void update() {
        this.move(velocity.getX(), velocity.getY());
    }

    public void move(double x, double y) {
        view.setTranslateX(view.getTranslateX() + x);
        view.setTranslateY(view.getTranslateY() + y);
    }

    public void rotate(double value) {
        Rotate rotate = new Rotate(value, width / 2, height / 2);
        view.getTransforms().add(rotate);
        addRotation(value);
        this.setVelocity(new Point2D(Math.cos(Math.toRadians(this.getRotation())), Math.sin(Math.toRadians(this.getRotation()))));
    }

    private void addRotation(double value) {
        rotation += value;
        if (rotation > 360) {
            rotation -= 360;
        } else if (rotation < 0) {
            rotation += 360;
        }
    }

    public void revive(int health) {
        this.health += health;
        alive = true;
    }

    public void hit() {
        this.health -= 1;
        if (health <= 0) {
            alive = false;
        }
    }

    public boolean isColliding(GameObject another) {
        return this.getBounds().intersects(another.getBounds());
    }

    public Bounds getBounds() {
        return this.view.getBoundsInParent();
    }

    public boolean isOutOfScreen() {
        if (view.getTranslateX() > Defines.SCREEN_WIDTH) {
            move(-1 * Defines.SCREEN_WIDTH, 0);
            return true;
        } else if (view.getTranslateX() < 0) {
            move(Defines.SCREEN_WIDTH, 0);
            return true;
        } else if (view.getTranslateY() > Defines.SCREEN_HEIGHT) {
            move(0, -1 * Defines.SCREEN_HEIGHT);
            return true;
        } else if (view.getTranslateY() < 0) {
            move(0, Defines.SCREEN_HEIGHT);
            return true;
        }
        return false;
    }

    public boolean isDead() {
        return !alive;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Node getView() {
        return view;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getRotation() {
        return rotation;
    }

    public int getHealth() {
        return health;
    }
}
