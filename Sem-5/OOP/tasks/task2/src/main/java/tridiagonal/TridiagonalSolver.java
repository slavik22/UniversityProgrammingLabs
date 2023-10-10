package tridiagonal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TridiagonalSolver {
    public static double[] solve(double[] a, double[] b, double[] c, double[] d) {
        int n = d.length;
        double temp;
        c[0] /= b[0];
        d[0] /= b[0];
        for (int i = 1; i < n; i++) {
            temp = 1.0 / (b[i] - c[i - 1] * a[i]);
            c[i] *= temp;
            d[i] = (d[i] - d[i - 1] * a[i]) * temp;
        }
        double[] x = new double[n];
        x[n - 1] = d[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            x[i] = d[i] - c[i] * x[i + 1];
        }
        return x;
    }

    public static double[] parallelSolve(double[] a, double[] b, double[] c, double[] d, int numThreads) {
        int n = d.length;

        c[0] /= b[0];
        d[0] /= b[0];
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 1; i < n; i++) {
            final int index = i;
            executor.execute(() -> {
                double temp = 1.0 / (b[index] - c[index - 1] * a[index]);
                c[index] *= temp;
                d[index] = (d[index] - d[index - 1] * a[index]) * temp;
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        double[] x = new double[n];
        x[n - 1] = d[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            final int index = i;
            executor.execute(() -> {
                x[index] = d[index] - c[index] * x[index + 1];
            });
        }


        executor.shutdown();
        try {
            if (!executor.awaitTermination(800, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        return x;
    }
}
