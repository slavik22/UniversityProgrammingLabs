package RMI;

import Models.Event;
import Models.Seat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMICommandsInterface extends Remote {

    void addNewConcert(Event event) throws RemoteException;

    void deleteConcert(String eventName) throws RemoteException;

    void updateConcert(Event event) throws RemoteException;

    boolean bookSeat(String concertName, int seatNumber) throws RemoteException;

    List<Event> getConcerts() throws RemoteException;

    List<Seat> getSeats(String concertName) throws RemoteException;

}