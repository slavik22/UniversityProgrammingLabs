package org.example.parsers;

import org.example.models.Deposit;

import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.*;

public class StAXDepositParser {

    public static List<Deposit> parseStAX(File xml) throws XMLStreamException, FileNotFoundException {

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader;

        DepositHandler depositHandler = new DepositHandler();
        reader = xmlInputFactory.createXMLEventReader(new FileInputStream(xml));


        while(reader.hasNext()) {
            XMLEvent nextXMLEvent = reader.nextEvent();
            if(nextXMLEvent.isStartElement()){
                StartElement startElement = nextXMLEvent.asStartElement();

                nextXMLEvent = reader.nextEvent();
                String name = startElement.getName().getLocalPart();
                if(nextXMLEvent.isCharacters()) {
                    List<Attribute> attributesList = new ArrayList<>();
                    Iterator<Attribute> iter = startElement.getAttributes();
                    while(iter.hasNext()) {
                        attributesList.add(iter.next());
                    }
                    Map<String, String> attributeMap = new HashMap<>();

                    for(Attribute attribute : attributesList){
                        attributeMap.put(attribute.getName().getLocalPart(), attribute.getValue());
                    }
                    depositHandler.setField(name, nextXMLEvent.asCharacters().getData(), attributeMap);
                }
            }
        }
        return depositHandler.getDepositList();
    }
}

