package ru.job4j.concurrent.pool;

import ru.job4j.concurrent.queue.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new ArrayList<>();
    private final int size = Runtime.getRuntime().availableProcessors();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);

    public ThreadPool() {
        while (threads.size() < size) {
            threads.add(
                    new Thread(
                            () -> {
                                while (!Thread.currentThread().isInterrupted()) {
                                    try {
                                        tasks.pool().run();
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }
                            }
                    )
            );
        }
        threads.forEach(Thread::start);
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutDown() {
        threads.forEach(Thread::interrupt);
    }

}
