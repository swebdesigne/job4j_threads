package ru.job4j.concurrent;

public class ThreadSleep {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Start loading ...");
                        Thread.sleep(300);
                        System.out.println("Loaded");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        thread.start();
        System.out.println("Main");
    }
}
