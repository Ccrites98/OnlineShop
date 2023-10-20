package com.pluralsight;

public class Product {
    private String id;
    private String name;
    private double price;

    private String department;
    public Product(String id, String name, double price, String department) {
        this.id = id;
        this.name = String.valueOf(name);
        this.price = price;
        this.department = department;
    }



    public String getName() {
        return this.name;
    }
    public double getPrice() {
        return this.price;
    }
    public String getDepartment() {return this.department;}
    public String getId() {return this.id;}
}
