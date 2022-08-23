package ru.job4j.concurrent.queue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SimpleBlockingQueueTest<T> {
    private static final int LIMIT = 7;

    @Test
    public void whenQueueNotEmpty() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(LIMIT);
        Queue<Integer> result = new LinkedList<>();

        Thread thread1 = new Thread(
                () -> IntStream.range(0, LIMIT)
                    .forEach(index -> {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    })
        );
        Thread thread2 = new Thread(
                () -> IntStream.range(0, LIMIT)
                        .forEach(index -> {
                            try {
                                result.add(queue.pool());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        })
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread1.join();
        assertThat(result).isEqualTo(new LinkedList<>(List.of(0, 1, 2, 3, 4, 5, 6)));
    }

    @Test
    public void whenQueueIsEmpty() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(LIMIT);
        Queue<Integer> consumer = new LinkedList<>();

        Thread thread1 = new Thread(
                () -> System.out.println("Nothing was add")
        );
        Thread thread2 = new Thread(
                () -> {
                    try {
                        consumer.add(queue.pool());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread1.join();
        assertThat(consumer.isEmpty()).isTrue();
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(LIMIT);
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index < queue.getLimit(); index++) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.pool());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
    }

    @Test
    public void whenAddToQueueReverseValueAndGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(LIMIT);
        Thread producer = new Thread(
                () -> {
                    for (int index = queue.getLimit(); index > 0; index--) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.pool());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(7, 6, 5, 4, 3, 2, 1));
    }

    @Test
    public void whenAddMoreThanQueueContainsAndGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(LIMIT);
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index < LIMIT + 2; index++) {
                        try {
                            queue.offer(index);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.pool());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    }
}