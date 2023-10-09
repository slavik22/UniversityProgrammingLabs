package client;

import object.MyObject;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);

        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
            MyObject myObject = new MyObject("Це мій об'єкт на клієнті");
            out.writeObject(myObject);
            out.flush();

            socket.close();
        }
    }
}

