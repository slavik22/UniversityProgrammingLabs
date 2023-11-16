package Grocery;

import java.io.Serializable;

public class Product implements Serializable {
    public int id;
    public String productName;
    public int amount;
    public int price;

    public Product(int productCode, String productName, int amount, int price) {
        this.id = productCode;
        this.productName = productName;
        this.amount = amount;
        this.price = price;
    }

    public int getProductCode() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String toString() {
        return "Product Code: " + id + ", Product Name: " + productName + ", Amount: " + amount + ", Price: " + price;
    }
}
