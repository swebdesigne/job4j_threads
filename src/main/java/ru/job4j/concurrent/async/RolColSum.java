package ru.job4j.concurrent.async;

import com.sun.istack.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Igor Sivolobov
 * @since 06.09.22
 * @version 1.0
 */
public class RolColSum {
    public record Sums(int rowSum, int colSum) {
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int index) {
        return CompletableFuture.supplyAsync(() -> {
                    int rowSum = 0, colSum = 0;
                    for (int row = 0; row < matrix.length; row++) {
                        rowSum += matrix[index][row];
                        colSum += matrix[row][index];
                    }
                    return new Sums(rowSum, colSum);
                }
        );
    }

    public static Sums[] asyncSum(@NotNull int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int col = 0; col < matrix.length; col++) {
            try {
                sums[col] = getTask(matrix, col).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return sums;
    }

    public static Sums[] sum(@NotNull int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int col = 0; col < matrix.length; col++) {
            int colSum = 0, rowSum = 0;
            for (int row = 0; row < matrix.length; row++) {
                rowSum += matrix[col][row];
                colSum += matrix[row][col];
            }
            sums[col] = new Sums(rowSum, colSum);
        }
        return sums;
    }
}
