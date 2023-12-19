package Models;

public class Seat {
    private int seatNumber;
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
}
