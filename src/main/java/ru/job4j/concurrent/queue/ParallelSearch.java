package ru.job4j.concurrent.queue;

public class ParallelSearch {
    private final static int LIMIT = 5;

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(LIMIT);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(queue.pool());
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != LIMIT; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    consumer.interrupt();
                }
        ).start();
    }
}
