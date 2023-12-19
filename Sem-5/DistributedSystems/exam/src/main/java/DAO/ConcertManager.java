package DAO;


import java.sql.*;
import java.util.*;
import Models.Event;
import Models.Seat;

public class ConcertManager {
    private final List<Event> events;

    public ConcertManager() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    public Optional<Event> getEventByName(String eventName) {
        return events.stream().filter(e -> e.getName().equals(eventName)).findFirst();
    }

    public boolean updateEvent(String eventName, Event updatedEvent) {
        Optional<Event> optionalEvent = getEventByName(eventName);

        if (optionalEvent.isPresent()) {
            Event existingEvent = optionalEvent.get();
            int index = events.indexOf(existingEvent);
            events.set(index, updatedEvent);
            return true;
        }

        return false;
    }

    // Delete
    public boolean deleteEvent(String eventName) {
        Optional<Event> optionalEvent = getEventByName(eventName);

        if (optionalEvent.isPresent()) {
            events.remove(optionalEvent.get());
            return true;
        }

        return false;
    }

    public boolean bookSeat(String eventName, int seatNumber) {
        Event e = getEventByName(eventName).orElseThrow();
        return e.bookSeat(seatNumber);
    }

    public List<Seat> getSeats(String eventName) {
        Event e = getEventByName(eventName).orElseThrow();
        return e.getSeats();
    }



    public void sort(Comparator<Event> comparator) {
        Collections.sort(events, comparator);
    }
}