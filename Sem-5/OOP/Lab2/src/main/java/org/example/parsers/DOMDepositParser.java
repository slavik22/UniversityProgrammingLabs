package org.example.parsers;

import org.example.models.Deposit;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DOMDepositParser {

    public static List<Deposit> parse(File file) {
        List<Deposit> deposits = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList depositNodes = document.getElementsByTagName("Deposit");
            for (int i = 0; i < depositNodes.getLength(); i++) {
                Node depositNode = depositNodes.item(i);
                if (depositNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) depositNode;
                    Deposit deposit = new Deposit();
                    deposit.setName(getElementValue(element, "name"));
                    deposit.setCountry(getElementValue(element, "country"));
                    deposit.setType(Deposit.Type.valueOf(getElementValue(element, "type").toUpperCase()));
                    deposit.setDepositor(getElementValue(element, "depositor"));
                    deposit.setAccountId(getElementValue(element, "accountId"));
                    deposit.setAmountOnDeposit(Double.parseDouble(getElementValue(element, "amount")));
                    deposit.setProfitability(Double.parseDouble(getElementValue(element, "profitability")));
                    deposit.setTimeConstraints(getElementValue(element, "timeConstraints"));
                    deposits.add(deposit);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deposits;
    }

    private static String getElementValue(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }
}

