package ru.job4j.concurrent.ball;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The component which draw a bouncing ball
 */
public class BallComponent extends JPanel {
    private static final int DEFAULT_WIDTH = 650;
    private static final int DEFAULT_HEIGHT = 650;

    private final List<Ball> balls = new ArrayList<>();

    /**
     * Input a ball into component
     *
     * @param b The injected ball
     */
    public void add(Ball b) {
        balls.add(b);
    }

    public void clear() {
        balls.clear();
    }

    public void paintComponent(Graphics g) {
        // clear fon
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (Ball b : balls) {
            g2.fill(b.getShape());
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}
