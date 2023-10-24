import java.util.ArrayList;
import java.util.List;

public class Route {
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
