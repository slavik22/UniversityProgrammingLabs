@FunctionalInterface
interface GenericNew {
    Generic apply(int[][] a, int[][] b, int[][] result, int workerCount, int workerIndex);
}

public abstract class Generic implements Runnable {
    int[][] a, b, result;
    int workerCount, workerIndex;

    public Generic(int[][] a, int[][] b, int[][] result, int workerCount, int workerIndex) {
        this.a = a;
        this.b = b;
        this.result = result;
        this.workerCount = workerCount;
        this.workerIndex = workerIndex;
    }
}
