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

    public int getLimit() {
        return limit;
    }

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (monitor) {
            if (queue.size() == limit) {
                monitor.wait();
            }
            queue.offer(value);
            monitor.notifyAll();
        }
    }

    public T pool() throws InterruptedException {
        synchronized (monitor) {
            while (queue.isEmpty()) {
                monitor.wait();
            }
            T poll = queue.poll();
            monitor.notifyAll();
            return poll;
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
