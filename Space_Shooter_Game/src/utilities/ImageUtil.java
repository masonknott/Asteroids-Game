package utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageUtil {
    public final static String path = "images/";
    public final static String ext = ".png";
    public static Map<String, Image> images = new HashMap<String, Image>();
    public static Image getImage(String s) {
        return images.get(s);
    }

    public static Image loadImage(String fname) throws IOException {
        BufferedImage image = null;
        image = ImageIO.read(new File(path + fname + ext));
        images.put(fname, image);
        return image;
    }


}
