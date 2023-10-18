package org.example;

class Tape extends MatrixMultiply {
    public Tape(int[][] a, int[][] b, int[][] result, int workerCount, int workerIndex) {
        super(a, b, result, workerCount, workerIndex);
    }

    @Override
    public void run() {

        int pivot = (int) Math.ceil(a.length / (double) workerCount);
        for (int row = workerIndex * pivot; row < (workerIndex + 1) * pivot && row < a.length; row++) {
            int counter = 0;
            int index = row;
            while (counter < a.length) {
                int cell = 0;
                for (int i = 0; i < a.length; i++) {
                    cell += a[row][i] * b[i][index];
                }

                result[row][index] = cell;
                counter++;
                index = (index + 1) % a.length;
            }
        }
    }
}