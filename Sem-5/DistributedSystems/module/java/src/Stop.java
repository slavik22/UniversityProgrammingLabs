import java.util.concurrent.Semaphore;

public class Stop {
    private final String name;
    private final Semaphore s;

    public Stop(String name, Integer busCount) {
        this.name = name;
        this.s = new Semaphore(busCount);
    }

    public void addBus(Bus bus) throws InterruptedException {
        s.acquire();

        System.out.println("Bus " + bus.getBusNumber() + " is at " + name);
        Thread.sleep(1000);

        s.release();
    }

}
