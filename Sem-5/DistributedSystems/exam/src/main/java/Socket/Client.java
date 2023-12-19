package Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    final static Scanner scanner = new Scanner(System.in);
    static Socket socket = null;
    static PrintWriter out = null;
    static BufferedReader in = null;

    public static void addNewConcert() {
        System.out.println("Enter concert name: ");
        String concertName = scanner.nextLine();
        System.out.println("Enter concert genre: ");
        String concertGenre = scanner.nextLine();
        System.out.println("Enter concert date: ");
        String concertDate = scanner.nextLine();
        System.out.println("Enter concert venue: ");
        String concertVenue = scanner.nextLine();
        System.out.println("Enter concert seat number: ");
        String seatNumber = scanner.nextLine();
        out.println(generateLineWithSeparator("1", concertName, concertGenre, concertDate, concertVenue, seatNumber));
    }

    public static void deleteConcert() {
        System.out.println("Enter concert name: ");
        String concertName = scanner.nextLine();
        out.println(generateLineWithSeparator("2", concertName));
    }

    public static void editConcert() {
        System.out.println("Enter concert name to edit: ");
        String concertName = scanner.nextLine();
        System.out.println("Enter concert genre: ");
        String concertGenre = scanner.nextLine();
        System.out.println("Enter concert date: ");
        String concertDate = scanner.nextLine();
        System.out.println("Enter concert venue: ");
        String concertVenue = scanner.nextLine();
        System.out.println("Enter concert seat number: ");
        String seatNumber = scanner.nextLine();
        out.println(generateLineWithSeparator("3", concertName, concertGenre, concertDate, concertVenue, seatNumber));
    }

    public static void bookSeat() {
        System.out.println("Enter concertName: ");
        String concertName = scanner.nextLine();
        System.out.println("Enter seat number: ");
        String seatNumber = scanner.nextLine();
        out.println(generateLineWithSeparator("4", concertName, seatNumber));
    }

    public static void getAllConcerts() {
        out.println(generateLineWithSeparator("5"));
    }

    public static void getAllBookedSeats() {
        System.out.println("Enter group ID: ");
        String groupId = scanner.nextLine();
        out.println(generateLineWithSeparator("6", groupId));
    }

    public static void showMenu() {
        System.out.println("1. Add a new concert");
        System.out.println("2. Delete concert");
        System.out.println("3. Edit concert");
        System.out.println("4. Book seat");
        System.out.println("5. Get all concerts");
        System.out.println("6. Get all booked seats");
        System.out.println("7. Exit");
    }

    public static String generateLineWithSeparator(String... args) {
        StringBuilder line = new StringBuilder();
        for (String arg : args) {
            line.append(arg).append("%");
        }
        return line.toString();
    }

    public static String[] parseLine(String line) {
        return line.split("%");
    }

    public static void readResponse() {
        try {
            String response = in.readLine();
            if(response == null){
                return;
            }
            String[] responseParts = parseLine(response);
            System.out.println("CLIENT:");
            System.out.println("Status: ");
            for (String responsePart : responseParts) {
                System.out.println(responsePart);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 8888);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String choice = "";
            while (true) {
                showMenu();
                choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> addNewConcert();
                    case "2" -> deleteConcert();
                    case "3" -> editConcert();
                    case "4" -> bookSeat();
                    case "5" -> getAllConcerts();
                    case "6" -> getAllBookedSeats();
                    case "7" -> {
                        System.out.println("CLIENT: Exit");
                        out.println(generateLineWithSeparator("10"));
                        socket.close();
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }

                readResponse();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
