package org.example.parsers;

import org.example.models.Deposit;

import javax.xml.stream.*;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StAXDepositParser {

    public static List<Deposit> parse(File file) throws IOException, XMLStreamException {
        List<Deposit> deposits = new ArrayList<>();
        Deposit currentDeposit = null;

        XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(file));

        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            if (event.isStartElement()) {
                StartElement startElement = event.asStartElement();
                switch (startElement.getName().getLocalPart()) {
                    case "Deposit" -> currentDeposit = new Deposit();
                    case "name" -> {
                        event = reader.nextEvent();
                        if (currentDeposit != null) {
                            currentDeposit.setName(event.asCharacters().getData());
                        }
                    }
                    case "accountId" -> {
                        event = reader.nextEvent();
                        if (currentDeposit != null) {
                            currentDeposit.setAccountId(event.asCharacters().getData());
                        }
                    }
                    case "country" -> {
                        event = reader.nextEvent();
                        if (currentDeposit != null) {
                            currentDeposit.setCountry(event.asCharacters().getData());
                        }
                    }
                    case "depositor" -> {
                        event = reader.nextEvent();
                        if (currentDeposit != null) {
                            currentDeposit.setDepositor(event.asCharacters().getData());
                        }
                    }
                    case "amount" -> {
                        event = reader.nextEvent();
                        if (currentDeposit != null) {
                            currentDeposit.setAmountOnDeposit(Double.parseDouble(event.asCharacters().getData()));
                        }
                    }
                    case "profitability" -> {
                        event = reader.nextEvent();
                        if (currentDeposit != null) {
                            currentDeposit.setProfitability(Double.parseDouble(event.asCharacters().getData()));
                        }
                    }
                    case "timeConstraints" -> {
                        event = reader.nextEvent();
                        if (currentDeposit != null) {
                            currentDeposit.setTimeConstraints(event.asCharacters().getData());
                        }
                    }
                    case "type" -> {
                        event = reader.nextEvent();
                        if (currentDeposit != null) {
                            currentDeposit.setType(Deposit.Type.valueOf(event.asCharacters().getData().toUpperCase()));
                        }
                    }
                }
            }
            if (event.isEndElement()) {
                EndElement endElement = event.asEndElement();
                if (endElement.getName().getLocalPart().equals("Deposit")) {
                    deposits.add(currentDeposit);
                }
            }
        }
        return deposits;
    }
}

