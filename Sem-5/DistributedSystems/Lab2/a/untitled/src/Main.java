class Forest{
    private final int[][] forest;
    private volatile boolean founded;
    private final int forestSize;

    private final int fieldSize;

    public final int threadsCount;
    private final Thread[] threads;

    private class Bees extends Thread {
        private final int startRow;
        private final int endRow;
        public Bees(int startRow, int endRow){
            this.startRow = startRow;
            this.endRow = endRow;
        }

        public void run() {
            for (int i = startRow; i < endRow && !founded; i++){
                checkRow(i);
            }
        }
        private void checkRow(int row){
            System.out.println(Thread.currentThread().getName() + " group of bees in row: " + row);
            for(int i = 0; i < forestSize; i++){
                if(forest[row][i] == 1){
                    System.out.println(Thread.currentThread().getName() + " pooh was founded in row: " + row);
                    founded = true;
                    break;
                }
            }
        }
    }
    public Forest(int forestSize, int threadsCount) {
        this.forestSize = forestSize;
        this.threadsCount = threadsCount;
        this.threads = new Thread[threadsCount];
        this.fieldSize = forestSize / threadsCount;

        forest = new int[forestSize][forestSize];

        for(int i = 0; i < forestSize; i++){
            for(int j = 0; j < forestSize; j++) {
                forest[i][j] = 0;
            }
        }

        int column = (int)(Math.random()*forestSize);
        int row = (int)(Math.random()*forestSize);
        System.out.println("Pooh is in row: " + row + " column: " + column);
        forest[row][row] = 1;

        checkForest();
    }
    private void checkForest(){
        for(int i = 0, f = 0; i < threadsCount; i++, f+=fieldSize ){
            if(i == threadsCount - 1){
                int end = f + fieldSize + forestSize % fieldSize;
                System.out.println(f + "-" + end);
                threads[i] = new Bees(f, f + fieldSize + forestSize % fieldSize);
            }
            else {
                int end = f + fieldSize - 1;
                System.out.println(f + "-" + end);
                threads[i] = new Bees(f, f + fieldSize - 1);
            }
            threads[i].start();
        }

        for(int i = 0; i < threadsCount; i++){
            try{
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

public class Main {
    public static void main(String[] args) {
        Forest forest = new Forest(101, 10);
    }
}
