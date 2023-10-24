import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

class Bus extends Thread {
    private final int busNumber;
    private final Route route;

    public Bus(int busNumber, Route route) {
        this.busNumber = busNumber;
        this.route = route;
    }

    @Override
    public void run() {
        for (Stop stop : route.getStops()) {
            try {
                stop.addBus(this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Bus " + busNumber + " completed the route and returned to the fleet.");
    }

    public int getBusNumber() {
        return busNumber;
    }
}


class Stop {
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



class Route {
    private final List<Stop> stops;

    public Route() {
        stops = new ArrayList<>();
    }

    public void addStop(Stop stop) {
        stops.add(stop);
    }

    public List<Stop> getStops() {
        return stops;
    }
}


public class Task4Java {
    public static void main(String[] args) {
        int maxBusesAtStop = 2,
                busesCount = 10;

        Route route = new Route();
        route.addStop(new Stop("Stop A", maxBusesAtStop ));
        route.addStop(new Stop("Stop B", maxBusesAtStop ));
        route.addStop(new Stop("Stop C", maxBusesAtStop ));

        ExecutorService executor = Executors.newFixedThreadPool(busesCount);

        for (int i = 0; i < busesCount; i++) {
            executor.execute(new Bus(i,route));
        }

        executor.shutdown();
    }
}
