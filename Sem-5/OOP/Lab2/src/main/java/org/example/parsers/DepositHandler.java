package org.example.parsers;

import org.example.models.Deposit;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


public class DepositHandler extends DefaultHandler {
    private List<Deposit> deposits = new ArrayList<>();
    private Deposit currentDeposit;
    private String currentElement;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
        if ("deposit".equals(qName)) {
            currentDeposit = new Deposit();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("deposit".equals(qName)) {
            deposits.add(currentDeposit);
            currentDeposit = null;
        }
        currentElement = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentElement != null) {
            String value = new String(ch, start, length).trim();
            switch (currentElement) {
                case "name" -> currentDeposit.setName(value);
                case "country" -> currentDeposit.setCountry(value);
                case "depositor" -> currentDeposit.setDepositor(value);
                case "accountId" -> currentDeposit.setAccountId(value);
                case "type" -> currentDeposit.setType(Deposit.Type.valueOf(value.toUpperCase()));
                case "amountOnDeposit" -> currentDeposit.setAmountOnDeposit(Double.parseDouble(value));
                case "profitability" -> currentDeposit.setProfitability(Double.parseDouble(value));
                case "timeConstraints" -> currentDeposit.setTimeConstraints(value);
            }
        }
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }
}
