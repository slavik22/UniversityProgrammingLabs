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

    public static void addNewGroup() {
        System.out.println("Enter group ID: ");
        String newGroupId = scanner.nextLine();
        System.out.println("Enter group name: ");
        String newGroupName = scanner.nextLine();
        out.println(generateLineWithSeparator("1", newGroupId, newGroupName));
    }

    public static void deleteGroup() {
        System.out.println("Enter group ID: ");
        String groupId = scanner.nextLine();
        out.println(generateLineWithSeparator("2", groupId));
    }

    public static void addStudentToGroup() {
        System.out.println("Enter student ID: ");
        String newStudentId = scanner.nextLine();
        System.out.println("Enter student first name: ");
        String newStudentFirstName = scanner.nextLine();
        System.out.println("Enter student last name: ");
        String newStudentLastName = scanner.nextLine();
        System.out.println("Enter student group ID: ");
        String newStudentGroupId = scanner.nextLine();
        out.println(generateLineWithSeparator("3", newStudentId, newStudentFirstName,newStudentLastName, newStudentGroupId));
    }

    public static void deleteStudent() {
        System.out.println("Enter student ID: ");
        String studentId = scanner.nextLine();
        out.println(generateLineWithSeparator("4", studentId));
    }

    public static void editStudent() {
        System.out.println("Enter student ID: ");
        String studentId = scanner.nextLine();
        System.out.println("Enter group ID: ");
        String groupId = scanner.nextLine();
        System.out.println("Enter student first name: ");
        String firstName = scanner.nextLine();
        System.out.println("Enter student last name: ");
        String lastName = scanner.nextLine();
        out.println(generateLineWithSeparator("5", studentId, groupId,firstName, lastName));
    }

    public static void countStudentsInGroup() {
        System.out.println("Enter group ID: ");
        String groupId = scanner.nextLine();
        out.println(generateLineWithSeparator("6", groupId));
    }

    public static void getAllStudentsInGroup() {
        System.out.println("Enter group ID: ");
        String groupId = scanner.nextLine();
        out.println(generateLineWithSeparator("7", groupId));
    }

    public static void getAllGroups() {
        out.println(generateLineWithSeparator("8"));
    }

    public static void showMenu() {
        System.out.println("1. Add a new group");
        System.out.println("2. Delete a group");
        System.out.println("3. Add student to a group");
        System.out.println("4. Delete a student");
        System.out.println("5. Edit a student");
        System.out.println("6. Count total amount of students in a group");
        System.out.println("7. Get all students in a group");
        System.out.println("8. Get all groups");
        System.out.println("9. Exit");
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
                    case "1" -> addNewGroup();
                    case "2" -> deleteGroup();
                    case "3" -> addStudentToGroup();
                    case "4" -> deleteStudent();
                    case "5" -> editStudent();
                    case "6" -> countStudentsInGroup();
                    case "7" -> getAllStudentsInGroup();
                    case "8" -> getAllGroups();
                    case "9" -> {
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
