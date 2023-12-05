package org.example.parsers;

import org.example.models.Deposit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DOMDepositParser {
    public static List<Deposit> parse(File file) throws ParserConfigurationException, IOException, SAXException {
        DepositHandler depositHandler = new DepositHandler();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);

        Element root = document.getDocumentElement();
        NodeList depositNodes = root.getElementsByTagName("deposit");


        for (int i = 0; i < depositNodes.getLength(); i++) {
            Element depositElement = (Element) depositNodes.item(i);
            depositHandler.startElement(null, null, "deposit", null);

            NodeList childNodes = depositElement.getChildNodes();
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                    Element childElement = (Element) childNodes.item(j);
                    depositHandler.startElement(null, null, childElement.getNodeName(), null);
                    depositHandler.characters(childElement.getTextContent().toCharArray(), 0, childElement.getTextContent().length());
                    depositHandler.endElement(null, null, childElement.getNodeName());
                }
            }

            depositHandler.endElement(null, null, "deposit");
        }

        return depositHandler.getDeposits();
    }
}
