package ateroids.GameObjects;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;

public class GameObject {
    private Node view;
    private Point2D velocity;
    private double width;
    private double height;
    private boolean alive;
    private double rotation;

    public double getRotation() {
        return rotation;
    }

    public GameObject(Node view, double width, double height) {
        this.view = view;
        this.velocity = new Point2D(0, 0);
        this.width = width;
        this.height = height;
        this.alive = true;
        this.rotation = 0;
    }

    public void update() {
        this.move(velocity.getX(), velocity.getY());
    }

    public boolean isDead() { return !alive;}

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void rotateRight() {
        Rotate rotate = new Rotate(10, width/2, height/2);
        view.getTransforms().add(rotate);
        addRotation(10);
        this.setVelocity(new Point2D(Math.cos(Math.toRadians(this.getRotation())), Math.sin(Math.toRadians(this.getRotation()))));

    }
    public void rotateLeft() {
        Rotate rotate = new Rotate(-10, width/2, height/2 );
        view.getTransforms().add(rotate);
        addRotation(-10);
        this.setVelocity(new Point2D(Math.cos(Math.toRadians(this.getRotation())), Math.sin(Math.toRadians(this.getRotation()))));
    }
    public Bounds getBounds() {
        return this.view.getBoundsInParent();
    }
    public boolean isColliding(GameObject another) {
        return this.getBounds().intersects(another.getBounds());
    }
    public void move(double x, double y) {
        view.setTranslateX(view.getTranslateX() + x);
        view.setTranslateY(view.getTranslateY() + y);
    }

    public void addRotation(double value) {
        rotation += value;
        if(rotation > 360) {
            rotation -= 360;
        } else if (rotation < 0 ) {
            rotation += 360;
        }
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
}
