package org.example;

public class Threads {
    public static int[][] run(int[][] a, int[][] b, int workerCount, MatrixMultiplyNew func) {
        int[][] result = new int[a.length][a.length];

        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a.length; j++){
                result[i][j] = 0;
            }
        }

        Thread[] worker = new Thread[workerCount];
        for(int i = 0; i < worker.length; i++){
            worker[i] = new Thread(func.apply(a, b, result, workerCount, i));
        }

        for (Thread task : worker) {
            task.start();
        }

        for (Thread task : worker) {
            try {
                task.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
