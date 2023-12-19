package Models;

import java.io.Serializable;

public class Seat implements Serializable {
    private final int seatNumber;
    private boolean isOccupied;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.isOccupied = true;
    }
    public int getSeatNumber() {
        return seatNumber;
    }
    public boolean isOccupied() {
        return isOccupied;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatNumber=" + seatNumber +
                ", isOccupied=" + isOccupied +
                '}';
    }
}
