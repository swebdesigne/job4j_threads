package ru.job4j.concurrent.pools;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ParallelSearchIndexInArrayTest {
    private Integer[] fillingArray(int amount) {
        return IntStream.range(0, amount)
                .boxed()
                .toArray(Integer[]::new);
    }

    @Test
    public void whenUseLinearSearchAndTypeDataIsInteger() {
        Integer[] array = fillingArray(3);
        assertThat(ParallelSearchIndexInArray.getIndex(array, 1)).isEqualTo(1);
    }

    @Test
    public void whenUseLinearSearchAndTypeDataIsString() {
        String[] array = new String[]{"Igor", "Alina", "Boris"};
        assertThat(ParallelSearchIndexInArray.getIndex(array, "Igor")).isEqualTo(0);
    }

    @Test
    public void whenUseParallelSearch() {
        Integer[] array = fillingArray(12);
        assertThat(ParallelSearchIndexInArray.getIndex(array, 11)).isEqualTo(11);
    }

    @Test
    public void whenElementNotFound() {
        Integer[] array = fillingArray(12);
        assertThat(ParallelSearchIndexInArray.getIndex(array, 12)).isEqualTo(-1);
    }

}