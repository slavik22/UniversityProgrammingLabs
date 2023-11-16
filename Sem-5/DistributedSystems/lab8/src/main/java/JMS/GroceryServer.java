package JMS;

import DAO.GroceryDAO;
import org.apache.activemq.broker.BrokerService;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.sql.SQLException;

public class GroceryServer {
    public static void main(String[] args) throws Exception {
        // Start the ActiveMQ broker
        BrokerService broker = new BrokerService();
        broker.setPersistent(false);
        broker.addConnector("tcp://localhost:61616");
        broker.start();

        // Create and start the GroceryServerHandler
        GroceryServerHandler serverHandler = new GroceryServerHandler();
        serverHandler.start();
    }
}

class GroceryServerHandler implements MessageListener {
    private final GroceryDAO groceryDAO;

    public GroceryServerHandler() throws JMSException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.groceryDAO = new GroceryDAO();
        // Setup ActiveMQ connection and consumer
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("grocery.queue");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);
    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                String text = ((TextMessage) message).getText();
                processMessage(text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private void processMessage(String text) {
        // Parse the text and call the corresponding methods in GroceryDAO
        // You need to define a protocol for the messages exchanged between client and server
        // For simplicity, you can use a CSV format or JSON for your messages
        // Example: "ADD_PRODUCT,categoryName,productName,price,amount"
        String[] parts = text.split(",");
        String operation = parts[0];

        switch (operation) {
            case "ADD_PRODUCT":
                groceryDAO.addProduct(parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
                break;
            // Add more cases for other operations
        }
    }

    public void start() throws IOException {
        System.out.println("Grocery Server is running. Press enter to stop.");
        System.in.read();
    }
}

