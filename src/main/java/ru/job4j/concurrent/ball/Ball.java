package ru.job4j.concurrent.ball;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * The animation of moving and bounce of the ball from edges of the Rectangle
 *
 * @author Sivolobov Igor
 * @version 1.0
 */
public class Ball {
    private static final int X_SIZE = 15;
    private static final int Y_SIZE = 15;
    private double x = 0;
    private double y = 0;
    private double dx = 1;
    private double dy = 1;

    /**
     * The ball rearranging in next position, changing the direction his moving,
     * as soon as he is reached the one of edge of the rectangle
     */
    public void move(Rectangle2D bounds) {
        x += dx;
        y += dy;
        if (x < bounds.getMinX()) {
            dx = -dx;
        }
        if (x + X_SIZE >= bounds.getMaxX()) {
            x = bounds.getMaxX() - X_SIZE;
            dx = - dx;
        }
        if (y < bounds.getMinY()) {
            y = bounds.getMinY();
            dy = - dy;
        }
        if (y + Y_SIZE >= bounds.getMaxY()) {
            y = bounds.getMaxY() - Y_SIZE;
            dy = -dy;
        }
    }

    /**
     * Obtaining the form of the ball in his current position
     */
    public Ellipse2D getShape() {
        return new Ellipse2D.Double(x, y, X_SIZE, Y_SIZE);
    }
}
