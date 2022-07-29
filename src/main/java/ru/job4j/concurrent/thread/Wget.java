package ru.job4j.concurrent.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")
        ) {
            int bytes;
            long downloadedBytes = 0;
            byte[] dataBuffer = new byte[speed];
            long start = System.currentTimeMillis();
            while ((bytes = in.read(dataBuffer, 0, speed)) != -1) {
                downloadedBytes += bytes;
                if (downloadedBytes >= speed) {
                    Thread.sleep(1000 - (System.currentTimeMillis() - start));
                    start = System.currentTimeMillis();
                    downloadedBytes = 0;
                }
                fileOutputStream.write(dataBuffer, 0, speed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean validate(String[] args) {
        if (args.length < 2) {
            System.out.println("The array should be have the two arguments");
            return false;
        }
        try {
            int speed = Integer.parseInt(args[1]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A second argument must have a type a number");
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        Wget.validate(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(
                new Wget(url, speed)
        );
        wget.start();
        wget.join();
    }
}
