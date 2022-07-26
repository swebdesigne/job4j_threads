package ru.job4j;

public class ThreadState {
    private static void checkThread(Thread th) {
        while (th.getState() != Thread.State.TERMINATED) {
            System.out.printf("Thread %s has status %s\n", th.getName(), th.getState());
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
        checkThread(first);
        checkThread(second);
        System.out.printf("Thread %s has status %s\n"
                        + "Thread %s has status %s\n",
                first.getName(), first.getState(),
                second.getName(), second.getState()
        );
    }
}
