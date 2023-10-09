import org.example.coffee.Coffee;
import org.example.coffee.CoffeeState;
import org.junit.Test;
import static org.junit.Assert.*;

public class CoffeeTest {

    @Test
    public void testCompareTo() {
        Coffee coffee1 = new Coffee(CoffeeState.CANS, 2.0, "Arabica", 10.0);
        Coffee coffee2 = new Coffee(CoffeeState.GRAIN, 1.5, "Robusta", 7.5);
        Coffee coffee3 = new Coffee(CoffeeState.GROUND, 0.5, "Blend", 5.0);

        assertTrue(coffee1.compareTo(coffee2) > 0);
        assertTrue(coffee2.compareTo(coffee1) < 0);
        assertTrue(coffee1.compareTo(coffee3) > 0);

        assertTrue(coffee2.compareTo(coffee3) > 0);
        assertTrue(coffee3.compareTo(coffee2) < 0);
    }
}