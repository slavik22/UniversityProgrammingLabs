package org.example.parsers;

import org.example.models.Deposit;

import javax.xml.stream.*;
import java.io.*;
import java.util.*;

public class StAXDepositParser {

    public static List<Deposit> parse(File file) {
        DepositHandler depositHandler = new DepositHandler();
        String currentElement = null;

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(fileInputStream);

            while (xmlStreamReader.hasNext()) {
                int event = xmlStreamReader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        currentElement = xmlStreamReader.getLocalName();
                        depositHandler.startElement(null, null, currentElement, null);
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        String value = xmlStreamReader.getText().trim();
                        depositHandler.characters(value.toCharArray(), 0, value.length());
                    }
                    case XMLStreamConstants.END_ELEMENT -> {
                        currentElement = xmlStreamReader.getLocalName();
                        depositHandler.endElement(null, null, currentElement);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return depositHandler.getDeposits();
    }
}

