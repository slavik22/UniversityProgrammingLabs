package org.example.van;

import org.example.coffee.Coffee;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CoffeeVan {

    private double maxWeight;
    private double budget;

    private double maxBudget;

    private final List<Coffee> cargo = new ArrayList<>();

    public List<Coffee> getCargo() {
        return cargo;
    }

    public CoffeeVan(double maxBudget, double maxWeight) {
        this.maxBudget = maxBudget;
        this.maxWeight = maxWeight;
    }

    public boolean loadCargo(Coffee item) {
        if(item.getWeight() + getWeight() <= maxWeight && budget + item.getPrice() <= maxBudget)
        {
            cargo.add(item);
            budget += item.getPrice();
            return true;
        }
        return false;
    }

    public double getWeight(){
        if (cargo.size() == 0){
            return 0;
        }
        return cargo.stream().mapToDouble(Coffee::getWeight).sum();
    }

    public List<Coffee> filter(Predicate<Coffee> predicate) {
        return cargo.stream().filter(predicate).toList();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("CoffeeVan{ \n maxWeight=").append(maxWeight)
                .append(",maxTotalPrice= ").append(maxBudget)
                .append(", currentTotalPrice=").append(budget)
                .append("\n Cargo: \n");

        cargo.forEach(c -> s.append(c.toString()).append("\n"));

        return s.append("}").toString();
    }

    public double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(double maxBudget) {
        this.maxBudget = maxBudget;
    }
}