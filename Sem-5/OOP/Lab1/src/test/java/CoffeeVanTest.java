import org.example.van.CoffeeVan;
import org.example.coffee.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoffeeVanTest {

    private CoffeeVan coffeeVan;

    @Before
    public void setUp() {
        coffeeVan = new CoffeeVan(100.0, 4); // Set max budget and max weight
    }

    @Test
    public void testLoadCargo() {
        Coffee coffee1 = new Coffee(CoffeeState.CANS, 2.0, "Арабіка", 10.0);
        assertTrue(coffeeVan.loadCargo(coffee1));
        assertEquals(1, coffeeVan.getCargo().size());
        assertEquals(2.0, coffeeVan.getWeight(), 0.01);
        assertEquals(10.0, coffeeVan.getBudget(), 0.01);

        Coffee coffee2 = new Coffee(CoffeeState.GROUND, 1.5, "Арабіка", 7.5);
        assertTrue(coffeeVan.loadCargo(coffee2));
        assertEquals(2, coffeeVan.getCargo().size());
        assertEquals(3.5, coffeeVan.getWeight(), 0.01);
        assertEquals(17.5, coffeeVan.getBudget(), 0.01);

        Coffee coffee3 = new Coffee(CoffeeState.GRAIN, 0.5, "Арабіка", 2.5);
        assertTrue(coffeeVan.loadCargo(coffee3));
        assertEquals(3, coffeeVan.getCargo().size());
        assertEquals(4, coffeeVan.getWeight(), 0.01);
        assertEquals(20, coffeeVan.getBudget(), 0.01);

        Coffee coffee4 = new Coffee(CoffeeState.BAGS, 0.5, "Арабіка", 5.0);
        assertFalse(coffeeVan.loadCargo(coffee3));
        assertEquals(3, coffeeVan.getCargo().size());
        assertEquals(4, coffeeVan.getWeight(), 0.01);
        assertEquals(20, coffeeVan.getBudget(), 0.01);
    }

    @Test
    public void testFilter() {
        Coffee coffee1 = new Coffee(CoffeeState.GRAIN, 2.0, "Arabica", 10.0);
        Coffee coffee2 = new Coffee(CoffeeState.GROUND, 1.5, "Robusta", 7.5);
        Coffee coffee3 = new Coffee(CoffeeState.BAGS, 0.5, "Blend", 5.0);

        coffeeVan.loadCargo(coffee1);
        coffeeVan.loadCargo(coffee2);
        coffeeVan.loadCargo(coffee3);

        assertEquals(1, coffeeVan.filter(coffee -> coffee.getSort().equals("Arabica")).size());
        assertEquals(2, coffeeVan.filter(coffee -> coffee.getSort().equals("Blend") || coffee.getSort().equals("Robusta")).size());

        assertEquals(3, coffeeVan.filter(coffee -> coffee.getPrice() >= 5.0).size());
    }
}

