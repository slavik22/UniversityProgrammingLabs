package org.example;

@FunctionalInterface
interface MatrixMultiplyNew {
    MatrixMultiply apply(int[][] a, int[][] b, int[][] result, int workerCount, int workerIndex);
}

public abstract class MatrixMultiply implements Runnable {
    int[][] a, b, result;
    int workerCount, workerIndex;

    public MatrixMultiply(int[][] a, int[][] b, int[][] result, int workerCount, int workerIndex) {
        this.a = a;
        this.b = b;
        this.result = result;
        this.workerCount = workerCount;
        this.workerIndex = workerIndex;
    }
}
