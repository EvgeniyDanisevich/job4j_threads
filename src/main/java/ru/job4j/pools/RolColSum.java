package ru.job4j.pools;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{"
                    + "rowSum=" + rowSum
                    + ", colSum=" + colSum
                    + '}';
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[getSumsLength(matrix)];
        for (int i = 0; i < sums.length; i++) {
            int[] rowCol = getRowCol(matrix, i);
            sums[i] = new Sums(rowCol[0], rowCol[1]);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[getSumsLength(matrix)];
        for (int i = 0; i < sums.length; i++) {
            int finalI = i;
            int[] rowCol = CompletableFuture.supplyAsync(() -> getRowCol(matrix, finalI)).get();
            sums[i] = new Sums(rowCol[0], rowCol[1]);
        }
        return sums;
    }

    private static int getSumsLength(int[][] matrix) {
        int max = matrix.length;
        for (int[] ints : matrix) {
            if (ints.length > max) {
                max = ints.length;
            }
        }
        return max;
    }

    private static int calculateColSum(int[] matrix, int index) {
        if (index > matrix.length - 1) {
            return 0;
        }
        return Arrays.stream(matrix).sum();
    }

    private static int calculateRowSum(int[][] matrix, int index) {
        int sum = 0;
        for (int[] ints : matrix) {
            if (index > ints.length - 1) {
                sum += 0;
            } else {
                sum += ints[index];
            }
        }
        return sum;
    }

    private static int[] getRowCol(int[][] matrix, int index) {
        int[] rowCol = new int[2];
        if (index < matrix.length - 1) {
            rowCol[0] = calculateColSum(matrix[index], index);
        }
        rowCol[1] = calculateRowSum(matrix, index);
        return rowCol;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Arrays.stream(asyncSum(new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8},
                {},
                {9, 10, 11, 12, 13, 14, 15}
        })).forEach(System.out::println);
    }
}