package org.example.parsers;

import org.example.models.Deposit;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;


public class DepositHandler extends DefaultHandler{

    private String value;
    private List<Deposit> depositList = new ArrayList<>();

    @Override
    public void startElement(String url, String name, String attributeName, Attributes attributes) {
        if (attributeName.equals("deposit")){
            Deposit deposit = new Deposit();
            depositList.add(deposit);
        }
    }

    @Override
    public void endElement(String uri, String name, String attributeName) {
        switch(attributeName) {
            case "name" -> getLastDeposit().setName(value);
            case "country" -> getLastDeposit().setCountry(value);
            case "type" -> getLastDeposit().setType(Deposit.Type.valueOf(value.toUpperCase()));
            case "depositor" -> getLastDeposit().setDepositor(value);
            case "accountId" -> getLastDeposit().setAccountId(value);
            case "amountOnDeposit" -> getLastDeposit().setAmountOnDeposit(Double.parseDouble(value));
            case "profitability" -> getLastDeposit().setProfitability(Double.parseDouble(value));
            case "timeConstraints" -> getLastDeposit().setTimeConstraints(value);
        }
    }

    public List<Deposit> getDepositList() { return depositList; }
    @Override
    public void startDocument() throws SAXException {depositList = new ArrayList<>();}
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        value = new String(ch, start, length).trim();
    }
    private Deposit getLastDeposit() {return depositList.get(depositList.size() - 1);}

    public void setField(String attributeName, String str, Map<String, String> attributes) {
        switch(attributeName) {
            case "deposit" -> {
                Deposit deposit = new Deposit();
                depositList.add(deposit);
            }
            case "name" -> getLastDeposit().setName(value);
            case "country" -> getLastDeposit().setCountry(value);
            case "type" -> getLastDeposit().setType(Deposit.Type.valueOf(value.toUpperCase()));
            case "depositor" -> getLastDeposit().setDepositor(value);
            case "accountId" -> getLastDeposit().setAccountId(value);
            case "amountOnDeposit" -> getLastDeposit().setAmountOnDeposit(Double.parseDouble(value));
            case "profitability" -> getLastDeposit().setProfitability(Double.parseDouble(value));
            case "timeConstraints" -> getLastDeposit().setTimeConstraints(value);
        }
    }

}