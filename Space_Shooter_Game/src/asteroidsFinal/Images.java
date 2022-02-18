package asteroidsFinal;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import utilities.ImageUtil;

public class Images {
    public static Image ASTEROID1, spaceBackground;
    static {
        try {
            ASTEROID1 = ImageUtil.loadImage("asteroid1");
            spaceBackground = ImageUtil.loadImage("galaxy");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Image image;
    public Vector2D position;
    public Vector2D direction;
    public double width;
    public double height;

    public Images(Image image, Vector2D s, Vector2D direction, double width,
                  double height) {
        this.image = image;
        this.position = s;
        this.direction = direction;
        this.width = width;
        this.height = height;
    }

    public double getRadius() {
        return (width + height) / 4.0;
    }



    public void draw(Graphics2D g) {
        double imW = image.getWidth(null);
        double imH = image.getHeight(null);
        AffineTransform t = new AffineTransform();
        t.rotate(direction.angle(), 0, 0);
        t.scale(width / imW, height / imH);
        t.translate(-imW / 2.0, -imH / 2.0);
        AffineTransform t0 = g.getTransform();
        g.translate(position.x, position.y);
        g.drawImage(image, t, null);
        g.setTransform(t0);
        g.setColor(Color.blue);

    }

}

