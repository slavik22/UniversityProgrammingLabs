package org.example;


import tridiagonal.TridiagonalSolver;

//public class Main {
//    public static void main(String[] args) {
//        double[] mainDiagonal = {2, 2, 2, 2,2};
//        double[] upperDiagonal = {-1, -1, -1,-1,0};
//        double[] lowerDiagonal = {-1, -1, -1,0};
//        double[] rightHandSide = {1, 1, 1, 1};
//
//        double[] solution = TridiagonalSolver.solve(mainDiagonal, upperDiagonal, lowerDiagonal, rightHandSide);
//
//        System.out.println("Solution:");
//        for (int i = 0; i < solution.length; i++) {
//            System.out.println("x[" + i + "] = " + solution[i]);
//        }
//    }
//}

import java.util.concurrent.*;

class TridiagonalMatrixSolver {
    private final double[] a, b, c, d, x;
    private final int size;

    public TridiagonalMatrixSolver(double[] a, double[] b, double[] c, double[] d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

        this.size = d.length;
        this.x = new double[size];
    }

    public void solve() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<?> upperTriangular = executorService.submit(this::computeUpperTriangular);
        Future<?> backSubstitution = executorService.submit(this::performBackSubstitution);

        try {
            // Wait for both tasks to complete
            upperTriangular.get();
            backSubstitution.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    private void computeUpperTriangular() {
        for (int i = 1; i < size; i++) {
            double factor = a[i] / b[i - 1];
            b[i] -= factor * c[i - 1];
            d[i] -= factor * d[i - 1];
        }
    }

    private void performBackSubstitution() {
        x[size - 1] = d[size - 1] / b[size - 1];

        for (int i = size - 2; i >= 0; i--) {
            x[i] = (d[i] - c[i] * x[i + 1]) / b[i];
        }
    }

    public double[] getResult() {
        return x;
    }
}

public class Main {
    public static void main(String[] args) {
        // Example tridiagonal matrix
        double[] a = {1, 2, 3, 4}; // Lower diagonal
        double[] b = {5, 6, 7, 8}; // Main diagonal
        double[] c = {9, 10, 11, 12}; // Upper diagonal
        double[] d = {20, 30, 40, 50}; // Right-hand side vector

        TridiagonalMatrixSolver solver = new TridiagonalMatrixSolver(a, b, c, d);

        solver.solve();

        double[] result = solver.getResult();

        // Print the result
        System.out.println("Solution:");
        for (double value : result) {
            System.out.println(value);
        }
    }
}




