public class Consecutive {
    public static int[][] run(int[][] a, int[][] b) {
        int[][] res = new int[a.length][a.length];

        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a.length; j++){
                res[i][j] = 0;
                for(int k = 0; k < a.length; k++){
                    res[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return res;
    }
}
