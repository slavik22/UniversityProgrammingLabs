import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Pot {
    private final int honeyMax;
    static int curr = 0;

    public Pot(int honeyMax) {
        this.honeyMax = honeyMax;
    }

    public synchronized void addHoney(){
        while(isFull()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        curr++;
        System.out.println("Bee add honey to the pot. Size is: " + curr);
    }

    synchronized boolean isFull() {
        return honeyMax == curr;
    }

    synchronized void eatAllHoney() {
        System.out.println("Winnie have eaten all the honey");
        curr = 0;
        notifyAll();
    }
}

class Bee implements Runnable {

    private final Pot pot;
    private final Bear bear;


    public Bee(Pot pot, Bear bear) {
        this.pot = pot;
        this.bear = bear;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);

            } catch (Exception e) {
                e.printStackTrace();
            }
            pot.addHoney();

            if (pot.isFull()) {
                bear.wakeUpBear();
            }
        }
    }

}

class Bear implements Runnable {

    private final Pot honey;
    private volatile boolean isAwake;

    public Bear(Pot honey) {
        this.honey = honey;
        this.isAwake = false;

    }

    public synchronized void wakeUpBear() {
        isAwake = true;
        notify();
    }

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                while (!isAwake){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                honey.eatAllHoney();
                isAwake = false;
                notifyAll();
            }
        }
    }
}



public class Main {
    public static void main(String[] args) {
        int beeNumber = 10;
        Pot honey = new Pot(100);
        Bear bear = new Bear(honey);
        new Thread(bear).start();

        ExecutorService executors = Executors.newFixedThreadPool(beeNumber);
        for (int i = 0; i < beeNumber; i++) {
            Runnable bee = new Bee(honey,bear);
            executors.execute(bee);
        }

        executors.shutdown();
    }
}