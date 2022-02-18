package asteroidsFinal;

import java.awt.*;

public class Bolt extends Object {
    private double lifetime;
    public static final int RADIUS = 2;
    public static final int BULLET_LIFE = 50;

    boolean firedByShip;

    public Bolt(Vector2D pos, Vector2D vel, boolean byShip) {
        super(pos, vel, RADIUS);
        this.lifetime = BULLET_LIFE;
        firedByShip = byShip;
    }

    @Override
    public void update() {
        super.update();
        lifetime -= 1;
        if (lifetime <= 0) status = true;
    }

    @Override
    public void draw(Graphics2D g)
    {g.setColor(Color.WHITE);
        g.fillOval((int) position.x-RADIUS, (int) position.y-RADIUS, 2*RADIUS, 2*RADIUS);


    }

    @Override
    public boolean canHit(Object other) {
        return other.getClass() == Ship.class || other.getClass() == Bolt.class;
    }

    @Override
    public void hit() {
        status = true;

    }
}
