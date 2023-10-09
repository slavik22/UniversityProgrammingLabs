package org.example.coffee;

import java.util.Comparator;

public class Coffee implements Comparable<Coffee> {
    private String sort;
    private CoffeeState state;
    private double weight;
    private double price;

    public Coffee(CoffeeState state, double weight, String sort, double price) {
        this.state = state;
        this.weight = weight;
        this.sort = sort;
        this.price = price;
    }

    public CoffeeState getState() {
        return state;
    }

    public void setState(CoffeeState state) {
        this.state = state;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Coffee{" +
                "state='" + state + '\'' +
                ", weight=" + weight +
                ", sort='" + sort + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Coffee o) {
        return Comparator.comparing(Coffee::getPrice)
                    .thenComparing(Coffee::getWeight)
                    .compare(this, o);
    }
}