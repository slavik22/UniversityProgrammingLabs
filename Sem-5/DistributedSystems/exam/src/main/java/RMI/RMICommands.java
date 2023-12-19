package RMI;

import DAO.ConcertManager;
import Models.Event;
import Models.Seat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMICommands extends UnicastRemoteObject implements RMICommandsInterface {
    private ConcertManager dao;

    public RMICommands() throws RemoteException {
        dao = new ConcertManager();
    }

    @Override
    public void addNewConcert(Event event) throws RemoteException {
        dao.addEvent(event);
    }

    @Override
    public void deleteConcert(String eventName) throws RemoteException {
        dao.deleteEvent(eventName);
    }

    @Override
    public void updateConcert(Event event) throws RemoteException {
        dao.updateEvent(event.getName(), event);
    }

    @Override
    public boolean bookSeat(String concertName, int seatNumber) throws RemoteException {
        return dao.bookSeat(concertName, seatNumber);
    }

    @Override
    public List<Event> getConcerts() throws RemoteException {
        return dao.getEvents();
    }

    @Override
    public List<Seat> getSeats(String concertName) throws RemoteException {
        return dao.getSeats(concertName);
    }
}