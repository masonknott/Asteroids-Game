package asteroidsFinal;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static asteroidsFinal.Constants.*;

public class Ship extends Object {
    public static final int RADIUS = 8;
    public static final double STEER_RATE = 2 * Math.PI;
    public static final double MAG_ACC = 200;
    public static final double DRAG = 0.01;
    public static final double DRAWING_SCALE = 1.5;
    public static final int MUZZLE_VELOCITY = 100;
    public static final Color COLOR = Color.cyan;
    public Vector2D dir;
    public boolean thrusting;
    public Bolt bolt;

    public int XP[] = { -6, 0, 6, 0 }, YP[] = { 8, 4, 8, -8 };
    public int XPTHRUST[] = { -5, 0, 5, 0 }, YPTHRUST[] = { 7, 3, 7, -7 };

    private long timeLastShot;
    public static final long SHOT_DELAY=200;

    private Controller ctrl;

    public Ship(Controller ctrl) {
        super(new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2), new Vector2D(0, -1), RADIUS);
        this.ctrl = ctrl;
        dir = new Vector2D(0,-1);
        thrusting = false;
        bolt = null;
        timeLastShot = System.currentTimeMillis();
    }

    private void Bolt(){
        bolt = new Bolt(new Vector2D(position), new Vector2D(velocity), true);
        // ensure bullet is clear of ship to avoid collision
        bolt.position.addScaled(dir, (radius+ bolt.radius)*1.1);
        bolt.velocity.addScaled(dir, MUZZLE_VELOCITY);

    }

    public void restart(){
        position.set(new Vector2D(FRAME_WIDTH/2, FRAME_HEIGHT/2));
        velocity.set(new Vector2D(0,0));
        dir.set(new Vector2D(0, -1));
        status = false;

    }


    @Override
    public void update() {
        super.update();
        Action action = ctrl.action();
        dir.rotate(action.turn * STEER_RATE * DT);
        velocity = new Vector2D(dir).mult(velocity.mag());
        velocity.addScaled(dir, MAG_ACC * DT * action.thrust);
        velocity.addScaled(velocity, -DRAG);
        thrusting = action.thrust > 0;
        if (action.shoot) {
            long time = System.currentTimeMillis();
            if (time-timeLastShot>SHOT_DELAY) {

                Bolt();
                //System.out.println("made bullet");
                action.shoot = false;
                timeLastShot = time;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = dir.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);
        if (thrusting) {
            g.setColor(Color.blue);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at);
    }

    @Override
    public boolean canHit(Object other) {
        return true;
    }

    @Override
    public void hit() {
        this.status = true;
    }
}
