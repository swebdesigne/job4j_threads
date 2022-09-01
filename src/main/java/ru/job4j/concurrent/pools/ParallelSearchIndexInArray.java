package ru.job4j.concurrent.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class ParallelSearchIndexInArray<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T value;

    public ParallelSearchIndexInArray(T[] array, int from, int to, T value) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    private int findIndex() {
        return IntStream.range(0, array.length)
                .filter(i -> this.value.equals(array[i]))
                .findFirst()
                .orElse(-1);
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return findIndex();
        }
        int mid = (from + to) / 2;
        ParallelSearchIndexInArray<T> leftPart = new ParallelSearchIndexInArray<>(array, from, mid, value);
        ParallelSearchIndexInArray<T> rightPart = new ParallelSearchIndexInArray<>(array, mid + 1, to, value);
        leftPart.fork();
        rightPart.fork();
        int left = leftPart.join();
        int right = leftPart.join();
        return Math.max(left, right);
    }

    public static int getIndex(Object[] array, Object index) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSearchIndexInArray<>(array, 0, array.length - 1, index));
    }

}
