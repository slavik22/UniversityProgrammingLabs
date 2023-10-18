package org.example;

class Cannon extends MatrixMultiply {
    public Cannon(int[][] a, int[][] b, int[][] result, int workerCount, int workerIndex) {
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
                    result[row][i] += a[row][row1] * b[row2][i];
                }

                row1 = (a.length + row1 - 1) % a.length;
                row2 = (a.length + row2 - 1) % a.length;
                counter++;
            }
        }
    }
}

