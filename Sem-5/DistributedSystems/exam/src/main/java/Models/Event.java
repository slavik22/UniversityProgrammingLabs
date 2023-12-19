package Models;

import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String genre;
    private String date;
    private String venue;
    private final List<Seat> seats;

    private int seatNumber;



    public Event(String name, String genre, String date, String venue, int seatNumber) {
        this.name = name;
        this.genre = genre;
        this.date = date;
        this.venue = venue;
        seats = new ArrayList<>(seatNumber);
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public boolean bookSeat(int seat) {
        if (seat > seatNumber) return false;
        boolean isBooked = seats.stream().anyMatch(s -> s.getSeatNumber() == seat);
        if (isBooked) return false;
        seats.add(new Seat(seat));
        return true;
    }
}
