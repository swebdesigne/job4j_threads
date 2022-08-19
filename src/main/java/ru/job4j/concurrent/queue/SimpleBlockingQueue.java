package ru.job4j.concurrent.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final Object monitor = this;
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public void offer(T value) {
        synchronized (monitor) {
            if (queue.size() == limit) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queue.offer(value);
            monitor.notifyAll();
        }
    }

    public T pool() {
        synchronized (monitor) {
            try {
                while (queue.isEmpty()) {
                    monitor.wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            monitor.notifyAll();
            return queue.poll();
        }
    }
}
