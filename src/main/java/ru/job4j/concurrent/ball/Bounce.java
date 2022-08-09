package ru.job4j.concurrent.ball;

import javax.swing.*;
import java.awt.*;

/**
 * In this program executing the animation bouncing ball
 *
 * @author Sivolobov Igor
 * @version 1.0
 */
public class Bounce {
    private static JFrame frame;

    public static void run() {
        EventQueue.invokeLater(
                () -> {
                    frame = new BounceFrame();
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setVisible(true);
                }
        );
    }

    public static void main(String[] args) {
        Bounce.run();
    }
}
