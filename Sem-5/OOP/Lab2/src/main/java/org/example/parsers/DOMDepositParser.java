package org.example.parsers;

import org.example.models.Deposit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DOMDepositParser {

    public static List<Deposit> parseDOM(File xml){
        // Визначаємо API, який дозволяє програмам отримувати синтаксичний аналізатор, який створює дерева об’єктів DOM з документів XML.
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            // Визначаємо API для отримання екземплярів документа DOM з документа XML.
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xml);
            document.getDocumentElement().normalize();
            Element rootNode = document.getDocumentElement();
            DepositHandler depositHandler = new DepositHandler();
            NodeList listOfDeposits = rootNode.getElementsByTagName("deposit");

            // Проходимо по дереву об'єктів DOM
            for (int i = 0; i < listOfDeposits.getLength(); i++) {
                Element depositElement = (Element) listOfDeposits.item(i);
                detouringNodes(depositElement,depositHandler);
            }
            return depositHandler.getDepositList();
        } catch (Exception e) {e.getStackTrace();}
        return null;
    }

    // Проходимо по вузлу дерева та витягуємо дані про девайси (рекурсивно)
    private static void detouringNodes(Node node, DepositHandler depositHandler) {
        if(node.getNodeType() == Node.ELEMENT_NODE) {
            Map<String, String> attributes = new HashMap<>();

            depositHandler.setField(node.getNodeName(), node.getTextContent(), attributes);
            for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                detouringNodes(node.getChildNodes().item(i), depositHandler);
            }
        }
    }
}
