package server;

import object.MyObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Сервер запущено...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Клієнт підключився.");

            try(ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())){
                MyObject myObject = (MyObject) in.readObject();
                System.out.println("Прийнято об'єкт від клієнта: " + myObject);

                clientSocket.close();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }
}