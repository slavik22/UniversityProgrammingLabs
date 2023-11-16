package JMS;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

public class GroceryClient {
    public static void main(String[] args) throws JMSException {
        // Setup ActiveMQ connection and producer
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("grocery.queue");
        MessageProducer producer = session.createProducer(destination);

        // Create and start the GroceryClientMenu
        GroceryClientMenu clientMenu = new GroceryClientMenu(producer, session);
        clientMenu.start();
    }
}

class GroceryClientMenu {
    private final MessageProducer producer;
    private final Session session;

    public GroceryClientMenu(MessageProducer producer, Session session) {
        this.producer = producer;
        this.session = session;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display console menu
            System.out.println("1. Add Product");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Example: "ADD_PRODUCT,categoryName,productName,price,amount"
                    System.out.print("Enter category name: ");
                    String categoryName = scanner.nextLine();
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter price: ");
                    int price = scanner.nextInt();
                    System.out.print("Enter amount: ");
                    int amount = scanner.nextInt();

                    String message = "ADD_PRODUCT," + categoryName + "," + productName + "," + price + "," + amount;
                    sendMessage(message);
                    break;
                case 2:
                    // Exit the client
                    try {
                        session.close();
                        scanner.close();
                        System.exit(0);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void sendMessage(String text) {
        try {
            TextMessage message = session.createTextMessage(text);
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
