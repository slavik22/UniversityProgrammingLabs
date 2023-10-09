package org.example;

import org.example.coffee.*;
import org.example.van.*;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        CoffeeVan van = new CoffeeVan(1400, 100);

        van.loadCargo(new Coffee(CoffeeState.CANS, 12.3, "Арабіка", 30));
        van.loadCargo(new Coffee(CoffeeState.BAGS, 20.3, "Арабіка", 50.2));
        van.loadCargo(new Coffee(CoffeeState.GROUND, 10.4, "Арабіка", 12.6));

        System.out.println(van);

        List<Coffee> searchResult = van.filter(c -> c.getPrice() > 20);

        System.out.println("Search results:");

        for (Coffee c: searchResult) {
            System.out.println(c);
        }
    }
}
