package ru.job4j.concurrent;

public class ThreadState {
    private static void checkThread(Thread th1, Thread th2) {
        while (th1.getState() != Thread.State.TERMINATED || th2.getState() != Thread.State.TERMINATED) {
            System.out.printf("Thread %s has status %s\n", th1.getName(), th1.getState());
        }
    }

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        checkThread(first, second);
        System.out.printf("Thread %s has status %s\n"
                        + "Thread %s has status %s\n",
                first.getName(), first.getState(),
                second.getName(), second.getState()
        );
    }
}
