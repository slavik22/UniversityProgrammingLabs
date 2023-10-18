package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;


public class Main {
    static int size = 500;
    static Random rand = new Random();

    static class Result {
        public int[][] matrix;
        public long ms;
    }

    static int[][] randMatrix() {
        var r = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                r[i][j] = rand.nextInt(10);
                r[i][j] = rand.nextInt(10);
            }
        }
        return r;
    }

    static boolean matricesEquals(int[][] a, int[][] b) {
        return Arrays.deepEquals(a, b);
    }

    static Result run(Supplier<int[][]> supplier) {
        var result = new Result();
        var start = System.currentTimeMillis();
        result.matrix = supplier.get();
        result.ms = System.currentTimeMillis() - start;
        return result;
    }

    static boolean resultsEquals(ArrayList<Result> m) {
        for (int i = 1; i < m.size(); i += 1) {
            var a = m.get(0).matrix;
            var b = m.get(i).matrix;
            if (!matricesEquals(a, b)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        var a = randMatrix();
        var b = randMatrix();

        var results = new ArrayList<Result>();

        var start = System.currentTimeMillis();
        results.add(run(() -> Consecutive.run(a, b)));
        System.out.printf("Equals: %b\n", System.currentTimeMillis() - start);

        results.add(run(() -> Threads.run(a, b, 1, Tape::new)));
        results.add(run((() -> Threads.run(a, b, 1, Fox::new))));
        results.add(run(() -> Threads.run(a, b, 1, Cannon::new)));
        results.add(run(() -> Threads.run(a, b, 2, Tape::new)));
        results.add(run(() -> Threads.run(a, b, 2, Fox::new)));
        results.add(run(() -> Threads.run(a, b, 2, Cannon::new)));
        results.add(run(() -> Threads.run(a, b, 4, Tape::new)));
        results.add(run(() -> Threads.run(a, b, 4, Fox::new)));
        results.add(run(() -> Threads.run(a, b, 4, Cannon::new)));

        System.out.printf("Equals: %b\n", resultsEquals(results));
        System.out.printf("Consecutive: %d ms\n", results.get(0).ms);
        System.out.printf("Tape 1: %d ms\n", results.get(1).ms);
        System.out.printf("Fox 1: %d ms\n", results.get(2).ms);
        System.out.printf("Cannon 1: %d ms\n", results.get(3).ms);
        System.out.printf("Tape 2: %d ms\n", results.get(4).ms);
        System.out.printf("Fox 2: %d ms\n", results.get(5).ms);
        System.out.printf("Cannon 2: %d ms\n", results.get(6).ms);
        System.out.printf("Tape 4: %d ms\n", results.get(7).ms);
        System.out.printf("Fox 4: %d ms\n", results.get(8).ms);
        System.out.printf("Cannon 4: %d ms\n", results.get(9).ms);
    }
}