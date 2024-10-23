package org.inventory_system.entity;

import java.time.LocalDate;

public class Product {
    private int id;
    private String name;
    private String unit;
    private int quantity;
    private double price;
    private String cat_name;
    private LocalDate exp_date;
    private String loc_name;
    private String  suppName;
    private double pricePur;
    private int qty;

    public Product(int id, String name, String unit, int quantity, double price, String cat_name, LocalDate exp_date, String loc_name) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.quantity = quantity;
        this.price = price;
        this.cat_name = cat_name;
        this.exp_date = exp_date;
        this.loc_name = loc_name;
    }

    public Product(Double price, String name){
        this.price = price;
        this.name = name;
    }

    public Product(int id,String name,String suppName,double pricePur,String unit ,int qty){
        this.id = id;
        this.name = name;
        this.suppName = suppName;
        this.pricePur = pricePur;
        this.unit = unit;
        this.qty = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public String getSuppName() {
        return suppName;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQty() {
        return qty;
    }
    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }
    public double getPricePur() {
        return pricePur;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(double price) {
        this.cat_name = cat_name;
    }

    public LocalDate getExp_date() {
        return exp_date;
    }

    public void setExp_date(LocalDate exp_date) {
        this.exp_date = exp_date;
    }

    public String getLoc_name() {return loc_name;}

    public void setLoc_name(String loc_name) {
        this.loc_name = loc_name;
    }

    @Override
    public String toString() {
        return "Product{name='" + name + "', quantity=" + qty + "}";
    }
}
