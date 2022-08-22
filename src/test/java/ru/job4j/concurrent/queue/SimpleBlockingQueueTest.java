package ru.job4j.concurrent.queue;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest<T> {
    private static final int LIMIT = 7;

    @Test
    public void whenQueueNotEmpty() throws InterruptedException {
        SimpleBlockingQueue queue = new SimpleBlockingQueue(LIMIT);
        Queue<T> result = new LinkedList<>();

        Thread thread1 = new Thread(
                () -> {
                    IntStream.range(0, LIMIT)
                        .forEach(index -> {
                            try {
                                queue.offer(index);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    IntStream.range(0, LIMIT)
                            .forEach(index -> {
                                try {
                                    result.add((T) queue.pool());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread1.join();
        assertThat(result).isEqualTo(new LinkedList<>(List.of(0, 1, 2, 3, 4, 5, 6)));
    }

    @Test
    public void whenQueueIsEmpty() throws InterruptedException {
        SimpleBlockingQueue queue = new SimpleBlockingQueue(LIMIT);
        Queue<T> consumer = new LinkedList<>();

        Thread thread1 = new Thread(
                () -> { System.out.println("Nothing was add"); }
        );
        Thread thread2 = new Thread(
                () -> {
                    try {
                        consumer.add((T) queue.pool());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread1.join();
        assertThat(consumer).isEmpty();
    }

}