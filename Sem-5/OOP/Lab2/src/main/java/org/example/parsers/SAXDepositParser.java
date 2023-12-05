package org.example.parsers;

import org.example.models.Deposit;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SAXDepositParser {
    public static List<Deposit> parse(File xml) throws SAXException, IOException, ParserConfigurationException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        DepositHandler depositHandler = new DepositHandler();
        saxParser.parse(xml, depositHandler);
        return depositHandler.getDeposits();
    }
}
