package asteroidsFinal;

import javax.sound.sampled.Clip;
import java.awt.*;

import static asteroidsFinal.Constants.*;

public abstract class Object {
    public Vector2D position;
    public Vector2D velocity;
    public Vector2D dir;
    public double radius;
    public boolean status;
    public Clip deathSound = null;


    public Object(Vector2D position, Vector2D vel, double radius) {
        this.position = position;
        this.velocity = vel;
        this.radius = radius;
        this.status = false;
        this.dir = new Vector2D(2, 0);
    }
    public void update() {
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public boolean overlap(Object other) {
        return this.position.dist(other.position) < this.radius + other.radius;
    }

    public void collisionHandling(Object other) {
        if (!this.status && !other.status && this.canHit(other) && this.overlap(other)) {
            this.hit();
            other.hit();
        }
    }
    public abstract boolean canHit(Object other);
    public abstract void draw(Graphics2D g);

    public void hit() {
        status = true;
    }

}
