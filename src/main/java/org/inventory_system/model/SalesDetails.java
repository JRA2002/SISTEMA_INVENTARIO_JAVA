package org.inventory_system.entity;

public class SalesDetails {
    private int productId;
    private String productName;
    private int quantity;
    private double prodPrice;
    private double subTotal;

    // Constructor
    public SalesDetails(int productId, String productName, int quantity,double prodPrice, double subTotal) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.prodPrice = prodPrice;
        this.subTotal = subTotal;
    }

    // Getters and setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(double prodPrice) {
        this.prodPrice = prodPrice;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
