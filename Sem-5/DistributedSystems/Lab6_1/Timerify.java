public class Timerify {
    static class Result {
        public int[][] matrix;
        public long ms;
    }

    @FunctionalInterface
    interface Timered {
        int[][] apply();
    }

    public static Result run(Timered timered) {
        var result = new Result();
        var start = System.currentTimeMillis();
        result.matrix = timered.apply();
        result.ms = System.currentTimeMillis() - start;
        return result;
    }
}
