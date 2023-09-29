import java.util.Queue;
import java.util.concurrent.*;

class Visitor implements Runnable {

    private final Barber barber;

    public Visitor(Barber barber) {
        this.barber = barber;
    }

    @Override
    public void run() {
        barber.addVisitorToQueue(this);
    }
}
class Barber {

    private final Semaphore semaphore = new Semaphore(1);
    private final Queue<Visitor> visitors = new ConcurrentLinkedQueue<>();

    public void addVisitorToQueue(Visitor visitorRunnable){
        visitors.add(visitorRunnable);
        System.out.println(Thread.currentThread().getName() + " went into the queue.");
        cutHair();
    }

    public void cutHair(){
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " sat down in the chair.");
            visitors.remove();
            Thread.sleep(1500);
            System.out.println(Thread.currentThread().getName() + " exited barbershop.");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


public class Main {
    public static void main(String[] args) {
        int numberOfVisitors = 10;
        Barber barber = new Barber();

        ExecutorService executors = Executors.newFixedThreadPool(numberOfVisitors);
        for (int i = 0; i < numberOfVisitors; i++) {
            Runnable visitor = new Visitor(barber);
            executors.execute(visitor);
        }

        executors.shutdown();
    }
}