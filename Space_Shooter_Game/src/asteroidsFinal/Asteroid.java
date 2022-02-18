package asteroidsFinal;

import java.awt.*;
import java.util.Random;

import static asteroidsFinal.Constants.*;


public class Asteroid extends Object {
    public static final double VMIN = 100, VMAX = 150;
    public Images images;
    public double rotationPerFrame;


    public Asteroid(double x, double y, double vx, double vy, Images images) {
        super(new Vector2D(x, y),
                new Vector2D(vx, vy), images.getRadius());
    }

    public Asteroid() {
        super(new Vector2D(WORLD_WIDTH*Math.random(), WORLD_HEIGHT+Math.random()), new Vector2D(0,0), 0);
        double speed = VMIN+(VMAX-VMIN)*Math.random();
        double angle = Math.random() * 2 * Math.PI;
        velocity.set(new Vector2D(speed*Math.cos(angle), speed*Math.sin(angle)));
        rotationPerFrame = Math.random()*0.1;
        double width = Math.min(Math.max(20+new Random().nextGaussian()*30, 30), 50);

        Image image = Images.ASTEROID1;
        double height = width * image.getHeight(null)/image.getWidth(null);
        double direction = Math.random() * 2 * Math.PI;
        dir = new Vector2D(Math.cos(direction), Math.sin(direction));
        images = new Images(image, position, dir, width, height);
        radius = images.getRadius();
    }

    public void draw(Graphics2D g) {
        //g.setColor(Color.RED);
        //g.fillOval((int) pos.x - radius, (int) pos.y - radius, 2 * radius, 2 * radius);
        images.draw(g);
    }

    @Override
    public void update() {
        super.update();
        dir.rotate(rotationPerFrame);
    }

    @Override
    public boolean canHit(Object other) {
        return other.getClass() != Asteroid.class;
    }
}
