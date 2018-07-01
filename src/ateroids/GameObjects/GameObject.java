package ateroids.GameObjects;

import javafx.geometry.Point2D;
import javafx.scene.Node;

public class GameObject {
    private Node view;
    private Point2D velocity;
    private boolean alive;

    public GameObject(Node view) {
        this.view = view;
        this.velocity = new Point2D(0, 0);
        this.alive = true;
    }

    public void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }

    public boolean isAlive() {
        return alive;
    }
    public boolean isDead() { return !alive;}

    public Node getView() {
        return view;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getRotate() {
        return view.getRotate();
    }

    public void rotateRight() {
        view.setRotate(view.getRotate() + 10);
        this.setVelocity(new Point2D(Math.cos(Math.toRadians(this.getRotate())), Math.sin(Math.toRadians(this.getRotate()))));

    }
    public void rotateLeft() {
        view.setRotate(view.getRotate() - 10);
        this.setVelocity(new Point2D(Math.cos(Math.toRadians(this.getRotate())), Math.sin(Math.toRadians(this.getRotate()))));
    }

    public boolean isColliding(GameObject another) {
        return this.getView().getBoundsInParent().intersects(another.getView().getBoundsInParent());
    }
}
