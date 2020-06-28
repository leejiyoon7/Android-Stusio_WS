package com.example.basicexam2;

public class Product {
    private String name;
    private String maker;
    private int price;

    public Product(){}

    public Product(String name, String maker, int price) {
        this.name = name;
        this.maker = maker;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
