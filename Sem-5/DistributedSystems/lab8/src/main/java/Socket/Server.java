package Socket;

import DAO.DataAccessObject;
import Models.Group;
import Models.Student;

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
    static DataAccessObject dao = new DataAccessObject();

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

    public static void addNewGroup(String[] queryParts) {
        try {
            System.out.println("SERVER: addNewGroup");
            if (paramsAreInvalid(3, queryParts.length)) return;
            int groupId = Integer.parseInt(queryParts[1]);
            String groupName = queryParts[2];
            dao.createNewGroup(new Group(groupName, groupId));
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Group added successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void deleteGroup(String[] queryParts) {
        try {
            System.out.println("SERVER: deleteGroup");
            if (paramsAreInvalid(2, queryParts.length)) return;
            int groupId = Integer.parseInt(queryParts[1]);
            dao.deleteGroup(groupId);
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Group deleted successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void addStudentToGroup(String[] queryParts) {
        try {
            System.out.println("SERVER: addStudentToGroup");
            if (paramsAreInvalid(5, queryParts.length)) return;
            int studentId = Integer.parseInt(queryParts[1]);
            String firstName = queryParts[2];
            String lastName = queryParts[3];
            int groupId = Integer.parseInt(queryParts[4]);
            Group gp = dao.findGroupById(groupId).orElseThrow();
            dao.createNewStudent(new Student(studentId,firstName, lastName, gp));
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Student added successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void deleteStudent(String[] queryParts) {
        try {
            System.out.println("SERVER: deleteStudent");
            if (paramsAreInvalid(3, queryParts.length)) return;
            int studentId = Integer.parseInt(queryParts[1]);
            dao.deleteStudent(studentId);
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Student deleted successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void editStudent(String[] queryParts) {
        try {
            System.out.println("SERVER: editStudent");
            if (paramsAreInvalid(7, queryParts.length)) return;
            int studentId = Integer.parseInt(queryParts[1]);
            int groupId = Integer.parseInt(queryParts[2]);
            Group gp = dao.findGroupById(groupId).orElseThrow();
            String firstName = queryParts[3];
            String lastName = queryParts[4];
            dao.updateStudent(new Student(studentId,firstName, lastName, gp));
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Product updated successfully");
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void countStudentsInGroup(String[] queryParts) {
        try {
            System.out.println("SERVER: countStudentsInGroup");
            if (paramsAreInvalid(2, queryParts.length)) return;
            int groupId = Integer.parseInt(queryParts[1]);
            int count = dao.countStudentsInGroup(groupId);
            sendResponse(ServerResponse.SUCCESS.getStatus(), "Count: " + String.valueOf(count));
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void getAllStudentsInGroup(String[] queryParts) {
        try {
            System.out.println("SERVER: getAllStudentsInGroup");
            if (paramsAreInvalid(2, queryParts.length)) return;
            int groupId = Integer.parseInt(queryParts[1]);
            List<Student> students = dao.findAllStudentsWithGroupId(groupId);
            if (students == null) {
                sendResponse(ServerResponse.SUCCESS.getStatus(), "Category not found");
            } else {
                for (Student student : students) {
                    sendResponse(student.toString());
                }
            }
        } catch (Exception e) {
            sendResponse(ServerResponse.INVALID_PARAMS.getStatus());
        }
    }

    public static void getAllGroups() {
        try {
            System.out.println("SERVER: getAllGroups");
            List<Group> groups = dao.findAllGroups();
            if (groups == null) {
                sendResponse(ServerResponse.SUCCESS.getStatus(), "No categories found");
            } else {
                for (Group group : groups) {
                    sendResponse(group.toString());
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
                case "1" -> addNewGroup(queryParts);
                case "2" -> deleteGroup(queryParts);
                case "3" -> addStudentToGroup(queryParts);
                case "4" -> deleteStudent(queryParts);
                case "5" -> editStudent(queryParts);
                case "6" -> countStudentsInGroup(queryParts);
                case "7" -> getAllStudentsInGroup(queryParts);
                case "8" -> getAllGroups();
                case "9" -> {
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
