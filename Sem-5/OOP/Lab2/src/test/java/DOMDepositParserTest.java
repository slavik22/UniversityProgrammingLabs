import org.example.models.Deposit;
import org.example.parsers.DOMDepositParser;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DOMDepositParserTest {
    @Test
    public void parseDOM() throws IOException, XMLStreamException, ParserConfigurationException, SAXException {
        File xmlFile = new File("src/main/resources/deposits.xml");
        List<Deposit> depositList =  DOMDepositParser.parse(xmlFile);
        Deposit first = depositList.get(0);

        assertEquals("Example Bank", first.getName());
        assertEquals("United States", first.getCountry());
        assertEquals(Deposit.Type.ONDEMAND, first.getType());
        assertEquals("John Doe", first.getDepositor());
        assertEquals("12345", first.getAccountId());
        assertEquals(50000.0, first.getAmountOnDeposit(), 0.001);
        assertEquals(5.0, first.getProfitability(), 0.001);
        assertEquals("12 months", first.getTimeConstraints());


        Deposit second = depositList.get(1);

        assertEquals("Sample Bank", second.getName());
        assertEquals("Canada", second.getCountry());
        assertEquals(Deposit.Type.SAVINGS, second.getType());
        assertEquals("Alice Smith", second.getDepositor());
        assertEquals("67890", second.getAccountId());
        assertEquals(75000.0, second.getAmountOnDeposit(), 0.001);
        assertEquals(4.5, second.getProfitability(), 0.001);
        assertEquals("24 months", second.getTimeConstraints());
    }
}