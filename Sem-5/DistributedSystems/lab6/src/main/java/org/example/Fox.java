package org.example;

public class Fox extends MatrixMultiply {
    public Fox(int[][] a, int[][] b, int[][] result, int workerCount, int workerIndex) {
        super(a, b, result, workerCount, workerIndex);
    }

    @Override
    public void run() {
        int pivot = (int) Math.ceil(a.length / (double) workerCount);
        for (int row = workerIndex * pivot; row < (workerIndex + 1) * pivot && row < a.length; row++) {
            int counter = 0;
            int row1 = row;
            int row2 = row;
            while (counter < a.length) {
                for (int i = 0; i < a.length; i++) {
                    result[row][i] += a[row][row2] * b[row1][i];
                }

                row1 = (row1 + 1) % a.length;
                row2 = (row2 + 1) % a.length;
                counter++;
            }
        }
    }
}