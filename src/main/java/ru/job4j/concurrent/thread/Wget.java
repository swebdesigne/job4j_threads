package ru.job4j.concurrent.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private static final String PREFIX = "tmp_";
    private final String url;
    private final int speed;
    private final String file;

    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)
        ) {
            int bytes;
            long downloadedBytes = 0;
            byte[] dataBuffer = new byte[1024];
            long start = System.currentTimeMillis();
            while ((bytes = in.read(dataBuffer, 0, 1024)) != -1) {
                downloadedBytes += bytes;
                if (downloadedBytes >= speed) {
                    long elapsed = System.currentTimeMillis() - start;
                    if (elapsed < 1000) {
                        Thread.sleep(1000 - elapsed);
                    }
                    start = System.currentTimeMillis();
                    downloadedBytes = 0;
                }
                fileOutputStream.write(dataBuffer, 0, speed);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Length of array must be equals two");
        }
        if (args[0].isEmpty() || args[1].isEmpty()) {
            throw new IllegalArgumentException("The element must be not empty");
        }
        try {
           Integer.parseInt(args[1]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A second argument must have a type a number");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Wget.validate(args);
        String url = args[0];
        String fileName = PREFIX.concat(url.substring(url.lastIndexOf('/') + 1));
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(
                new Wget(url, speed, fileName)
        );
        wget.start();
        wget.join();
    }
}
