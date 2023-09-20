import java.util.concurrent.SynchronousQueue;

class MilitaryBase{

    private final SynchronousQueue<Integer> ivanovQueue;
    private final SynchronousQueue<Integer> petrovQueue;

    public MilitaryBase() {
        ivanovQueue = new SynchronousQueue<>();
        petrovQueue = new SynchronousQueue<>();
    }

    public Runnable ivanov(int numItems) {
        return () -> {
            try {
                for (int i = 0; i < numItems; i++) {
                    System.out.println("Ivanov: " + i);
                    this.ivanovQueue.put(i);
                    Thread.sleep(700);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }
    public Runnable petrov(int numItems) {
        return () -> {
            try {
                for (int i = 0; i < numItems; i++) {
                    int item = this.ivanovQueue.take();
                    System.out.println("Petrov: " + i);
                    this.petrovQueue.put(item);

                    Thread.sleep(600);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }
    public Runnable nechip(int numItems) {
        return () -> {
            try {
                for (int i = numItems; i > 0; i--) {
                    int item = petrovQueue.take();
                    System.out.println("Nechiporuk: " + item);

                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }
    }



public class Main {
    private static final Integer count = 25;
    public static void main(String[] args) throws InterruptedException {
        MilitaryBase mb = new MilitaryBase();

        Thread ivanov = new Thread(mb.ivanov(count));
        Thread petrov = new Thread(mb.petrov(count));
        Thread nechip = new Thread(mb.nechip(count));

        ivanov.start(); petrov.start(); nechip.start();

        ivanov.join(); petrov.join(); nechip.join();
    }
}
