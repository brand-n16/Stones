import becker.robots.icons.*;
import java.awt.*;
import java.awt.geom.*;

public class BlueCircleIcon extends Icon {
    public BlueCircleIcon(double relativeSize) {
        super(relativeSize);
    }

    protected void renderImage(Graphics2D g2, int width, int height) {
        g2.setColor(Color.BLUE);
        g2.fill(new Ellipse2D.Double(0, 0, 1.0, 1.0)); 
    }
}