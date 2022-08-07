package ru.job4j.concurrent;

import java.util.concurrent.TimeUnit;

public class Flag {
    private static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    while (flag) {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            TimeUnit.MILLISECONDS.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
        thread.start();
        TimeUnit.MILLISECONDS.sleep(1000);
        flag = !flag;
        thread.join();
    }
}
