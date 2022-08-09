package ru.job4j.concurrent.ball;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * Frame with component of the ball and buttons
 */
public class BounceFrame extends JFrame {
    private static BallComponent comp;
    public static final int DELAY = 3;
    private static boolean status = true;

    /**
     * Constructing a frame wit component being displayid the bouncing ball, and buttons Start and Close
     */
    public BounceFrame() {
        setTitle("Bounce");

        comp = new BallComponent();
        add(comp, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Start", event -> {
                    status = true;
                    addBall();
                }
        );
        addButton(buttonPanel, "Stop", event -> status = false);
        addButton(buttonPanel, "Clear", event -> {
                    comp.clear();
                    comp.updateUI();
                }
        );
        addButton(buttonPanel, "Close", event -> System.exit(0));
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }

    /**
     * Input a button to container
     *
     * @param c        Container
     * @param title    Title on the button
     * @param listener Receiver of the actions of the button
     */
    public void addButton(Container c, String title, ActionListener listener) {
        JButton button = new JButton(title);
        c.add(button);
        button.addActionListener(listener);
    }

    /**
     * Input the bouncing ball to panel and produces 1000 his remains
     */
    public void addBall() {
        Thread thread = new Thread(
                () -> {

                    try {
                        Ball ball = new Ball();
                        comp.add(ball);
                        while (status) {
                            ball.move(comp.getBounds());
                            comp.paint(comp.getGraphics());
                            TimeUnit.MICROSECONDS.sleep(DELAY);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                }
        );
        thread.start();
    }
}
