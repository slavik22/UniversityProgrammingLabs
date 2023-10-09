import object.MyObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private Thread serverThread;
    private Thread clientThread;
    private ServerSocket serverSocket;
    private int serverPort = 1234;
    private ByteArrayOutputStream serverOutput;
    private ByteArrayOutputStream clientOutput;

    @Before
    public void setUp() throws IOException {
        serverOutput = new ByteArrayOutputStream();
        clientOutput = new ByteArrayOutputStream();


        serverThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(serverPort);
                Socket clientSocket = serverSocket.accept();
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                MyObject myObject = (MyObject) in.readObject();
                serverOutput.write(myObject.getData().getBytes());
                serverOutput.flush();
                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        // Start the client
        clientThread = new Thread(() -> {
            try (Socket socket = new Socket("localhost", serverPort)) {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                MyObject myObject = new MyObject("Test Data");
                out.writeObject(myObject);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        serverThread.start();
        clientThread.start();

    }

    @After
    public void tearDown() throws IOException, InterruptedException {
        serverThread.interrupt();
        clientThread.interrupt();

        serverSocket.close();
        serverOutput.close();
        clientOutput.close();
    }

    @Test
    public void testIntegration() throws InterruptedException {
        clientThread.join();
        serverThread.join();

        String receivedData = serverOutput.toString();
        assertEquals("Test Data", receivedData);
    }

//    public static void main(String[] args) {
//        JUnitCore.runClasses(IntegrationTest.class);
//    }
}
