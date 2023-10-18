package org.example.models;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class DepositHandler extends DefaultHandler {
    private List<Deposit> deposits = new ArrayList<>();
    private StringBuilder currentElementValue;
    private Deposit currentDeposit;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElementValue = new StringBuilder();
        if ("Deposit".equals(qName)) {
            currentDeposit = new Deposit();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        currentElementValue.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (currentDeposit != null) {
            String value = currentElementValue.toString().trim();
            if ("name".equals(qName)) {
                currentDeposit.setName(value);
            } else if ("country".equals(qName)) {
                currentDeposit.setCountry(value);
            } else if ("type".equals(qName)) {
                currentDeposit.setType(Deposit.Type.valueOf(value.toUpperCase()));
            } else if ("depositor".equals(qName)) {
                currentDeposit.setDepositor(value);
            } else if ("accountId".equals(qName)) {
                currentDeposit.setAccountId(value);
            } else if ("amount".equals(qName)) {
                currentDeposit.setAmountOnDeposit(Double.parseDouble(value));
            } else if ("profitability".equals(qName)) {
                currentDeposit.setProfitability(Double.parseDouble(value));
            } else if ("timeConstraints".equals(qName)) {
                currentDeposit.setTimeConstraints(value);
            } else if ("Deposit".equals(qName)) {
                deposits.add(currentDeposit);
                currentDeposit = null;
            }
        }
    }

    public List<Deposit> getDevices() {
        return deposits;
    }
}