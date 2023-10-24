public class Bus extends Thread {
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
