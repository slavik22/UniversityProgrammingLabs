package RMI;

import Models.Event;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry();
            RMICommandsInterface rmiCommands = (RMICommandsInterface) registry
                    .lookup("Dao");
            String commands =
                    """
                            1. Create new concert
                            2. Delete concert
                            3. Edit concert
                            4. Book seat
                            5. Get all concerts
                            6. Get concert seats
                            7. Exit
                            """;

            Integer operation = null;
            Scanner scanner = new Scanner(System.in);
            System.out.println(commands);

            while (operation == null || operation != 11) {
                System.out.println("Input operation:");
                operation = Integer.parseInt(scanner.next());

                try {
                    switch (operation) {
                        case 1 -> {
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
                            rmiCommands.addNewConcert(new Event(concertName, concertGenre, concertDate, concertVenue, Integer.parseInt(seatNumber)));
                            System.out.println("Operation completed successfully");
                        }
                        case 2 -> {
                            System.out.println("Enter concert name: ");
                            String concertName = scanner.nextLine();
                            rmiCommands.deleteConcert(concertName);
                            System.out.println("Operation completed successfully");
                        }
                        case 3 -> {
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
                            rmiCommands.updateConcert(new Event(concertName, concertGenre, concertDate, concertVenue, Integer.parseInt(seatNumber)));
                            System.out.println("Operation completed successfully");
                        }
                        case 4 -> {
                            System.out.println("Enter concert name: ");
                            String concertName = scanner.nextLine();
                            System.out.println("Enter concert seat number: ");
                            int seatNumber = Integer.parseInt(scanner.nextLine());
                            rmiCommands.bookSeat(concertName, seatNumber);
                            System.out.println("Operation completed successfully");
                        }
                        case 5 -> {
                            rmiCommands.getConcerts();
                            System.out.println("Operation completed successfully");
                        }
                        case 6 -> {
                            System.out.println("Enter concert name: ");
                            String concertName = scanner.nextLine();
                            rmiCommands.getSeats(concertName);
                            System.out.println("Operation completed successfully");
                        }
                        case 7 -> System.out.println("Program stopped");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}