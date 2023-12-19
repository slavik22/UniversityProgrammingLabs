package Socket;

import DAO.ConcertManager;
import Models.Event;
import Models.Seat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    static ServerSocket server = null;
    static Socket socket = null;
    static BufferedReader in = null;
    static PrintWriter out = null;
    static ConcertManager dao = new ConcertManager();

    public static String generateLineWithSeparator(String... args) {
        StringBuilder line = new StringBuilder();
        for (String arg : args) {
            line.append(arg).append("%");
        }
        return line.toString();
    }

    public static void sendResponse(String... args) {
        out.println(generateLineWithSeparator(args));
    }

    public static boolean paramsAreInvalid(int expected, int actual) {
        if (expected != actual) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            server = new ServerSocket(8888);
            socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (processQuery());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getAllConcerts(String[] queryParts) {
        try {
            System.out.println("SERVER: getAllConcerts");
            List<Event> events = dao.getEvents();
            if (events == null || events.isEmpty()) {
                sendResponse(ServerResponse.SUCCESS.getStatus(), "No events found");
            } else {
                for (Event e : events) {
                    sendResponse(e.toString());
                }
            }
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void addNewConcert(String[] queryParts) {
        try {
            System.out.println("SERVER: addNewConcert");
            if (paramsAreInvalid(5, queryParts.length)) return;
            String concertName = queryParts[1];
            String concertGenre = queryParts[2];
            String concertDate = queryParts[3];
            String concertVenue = queryParts[4];
            int seatNumber = Integer.parseInt(queryParts[4]);
            dao.addEvent (new Event(concertName, concertGenre, concertDate, concertVenue, seatNumber));
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Concert added successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void deleteConcert(String[] queryParts) {
        try {
            System.out.println("SERVER: deleteGroup");
            if (paramsAreInvalid(2, queryParts.length)) return;
            String concertName = queryParts[1];
            dao.deleteEvent(concertName);
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Concert deleted successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void editConcert(String[] queryParts) {
        try {
            System.out.println("SERVER: editConcert");
            if (paramsAreInvalid(7, queryParts.length)) return;
            String concertName = queryParts[1];
            String concertGenre = queryParts[2];
            String concertDate = queryParts[3];
            String concertVenue = queryParts[4];
            int seatNumber = Integer.parseInt(queryParts[4]);
            Event e = dao.getEventByName(concertName).orElseThrow();

            dao.updateEvent(concertName, new Event(concertName,concertGenre, concertDate, concertVenue, seatNumber));
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Product updated successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void bookSeat(String[] queryParts) {
        try {
            System.out.println("SERVER: bookSeat");
            if (paramsAreInvalid(3, queryParts.length)) return;
            String concertName = queryParts[1];
            int seatNumber = Integer.parseInt(queryParts[2]);
            boolean booked = dao.bookSeat(concertName, seatNumber);

            if(booked)
                sendResponse(ServerResponse.SUCCESS.getStatus(), "Seat booked successfully");
            else
                sendResponse(ServerResponse.SUCCESS.getStatus(), "Seat already booked");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void getAllBookedSeats(String[] queryParts) {
        try {
            System.out.println("SERVER: getAllBookedSeats");
            if (paramsAreInvalid(2, queryParts.length)) return;
            String concertName = queryParts[1];
            List<Seat> seats = dao.getSeats(concertName);
            if (seats == null) {
                sendResponse(ServerResponse.SUCCESS.getStatus(), "No seat is booked");
            } else {
                for (Seat s : seats) {
                    sendResponse(s.toString());
                }
            }
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static boolean processQuery() {
        try {
            String query = in.readLine();
            String[] queryParts = parseQuery(query);
            String command = queryParts[0];
            switch (command) {
                case "1" -> addNewConcert(queryParts);
                case "2" -> deleteConcert(queryParts);
                case "3" -> editConcert(queryParts);
                case "4" -> bookSeat(queryParts);
                case "5" -> getAllConcerts(queryParts);
                case "6" -> getAllBookedSeats(queryParts);
                case "7" -> {
                    System.out.println("SERVER: Exit");
                    socket.close();
                    server.close();
                    return false;
                }
                default ->  sendResponse(ServerResponse.QUERY_NOT_FOUND.getStatus());
            }
            return true;
        } catch (Exception e) {
            System.out.println("SERVER: Error: " + e.getMessage());
            return false;
        }
    }

    public static String[] parseQuery(String query) {
        return query.split("%");
    }
}
