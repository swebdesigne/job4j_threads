package ru.job4j.concurrent.pools;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class ParallelSearchIndexInArray<T> {
    private final T[] array;

    public ParallelSearchIndexInArray(T[] array) {
        this.array = array;
    }

    public int findIndex(T index) {
        if (array.length <= 10) {
            return IntStream.range(0, array.length)
                    .filter(i -> index.equals(array[i]))
                    .findFirst()
                    .orElse(-1);
        }

        return 1;
    }

    public static void main(String[] args) {
        Integer[] array = IntStream.generate(() -> new Random().nextInt(3))
                .limit(3)
                .boxed()
                .toArray(Integer[]::new);
        ParallelSearchIndexInArray<Integer> finder = new ParallelSearchIndexInArray<>(array);
        Arrays.stream(array).forEach(System.out::println);
        System.out.println(finder.findIndex(2));
    }
}
