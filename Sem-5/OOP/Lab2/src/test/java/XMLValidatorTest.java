import org.example.parsers.XMLValidator;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class XMLValidatorTest {
    @Test
    public void xmlValidate() {
        assertTrue(XMLValidator.validate("src/main/resources/deposits.xml", "src/main/resources/deposits.xsd"));
    }
}
