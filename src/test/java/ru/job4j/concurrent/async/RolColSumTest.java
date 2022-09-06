package ru.job4j.concurrent.async;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RolColSumTest {
    private final int[][] matrix = new int[][]{
            {1, 2, 3},
            {1, 2, 3},
            {1, 2, 3}
    };
    private final String expected = "[Cols: 3, Row: 6, Cols: 6, Row: 6, Cols: 9, Row: 6]";

    private String arrayToString(RolColSum.Sums[] array) {
        return Arrays.toString(
                Arrays.stream(array)
                        .map(sums -> "Cols: %d, Row: %d".formatted(sums.colSum(), sums.rowSum()))
                        .toArray(String[]::new)
        );
    }

    @Test
    public void whenSumConsistently() {
        String result = arrayToString(RolColSum.sum(matrix));
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenSumAsync() {
        String result = arrayToString(RolColSum.asyncSum(matrix));
        assertThat(result).isEqualTo(expected);
    }
}