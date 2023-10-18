package org.example.models;

public class Deposit {
    private String name;

    private String country;
    private Type type;
    private String depositor;
    private String accountId;
    private Double amountOnDeposit;
    private Double profitability;
    private String timeConstraints;

    public enum Type {
        ONDEMAND,
        URGENT,
        SETTLEMENT,
        ACCUMULATIVE,
        SAVINGS,
        METAL
    }

    public Deposit() {}

    public Deposit(String name, String country, Type type, String depositor, String accountId, Double amountOnDeposit, Double profitability, String timeConstraints) {
        this.name = name;
        this.country = country;
        this.type = type;
        this.depositor = depositor;
        this.accountId = accountId;
        this.amountOnDeposit = amountOnDeposit;
        this.profitability = profitability;
        this.timeConstraints = timeConstraints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDepositor() {
        return depositor;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public double getAmountOnDeposit() {
        return amountOnDeposit;
    }

    public void setAmountOnDeposit(double amountOnDeposit) {
        this.amountOnDeposit = amountOnDeposit;
    }

    public double getProfitability() {
        return profitability;
    }

    public void setProfitability(double profitability) {
        this.profitability = profitability;
    }

    public String getTimeConstraints() {
        return timeConstraints;
    }

    public void setTimeConstraints(String timeConstraints) {
        this.timeConstraints = timeConstraints;
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", type=" + type +
                ", depositor='" + depositor + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amountOnDeposit=" + amountOnDeposit +
                ", profitability=" + profitability +
                ", timeConstraints='" + timeConstraints + '\'' +
                '}';
    }
}
