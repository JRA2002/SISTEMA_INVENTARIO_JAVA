package org.inventory_system.model;

import java.time.LocalDate;

public class Purchase {
    private int purchId;
    private String supplier;
    private int quantity;
    private LocalDate dateOfPurchase;
    private Double totalAmount;
    private int userId;
    private String userName;

    public Purchase(int purchId, String supplier, int quantity,LocalDate dateOfPurchase, Double totalAmount) {
        this.purchId = purchId;
        this.supplier = supplier;
        this.quantity = quantity;
        this.dateOfPurchase = dateOfPurchase;
        this.totalAmount = totalAmount;}

    public Purchase(int purchId,LocalDate dateOfPurchase,int userId){
        this.purchId = purchId;
        this.dateOfPurchase = dateOfPurchase;
        this.userId = userId;
    }
    public Purchase(int purchId,LocalDate dateOfPurchase,String userName,Double totalAmount){
        this.purchId = purchId;
        this.dateOfPurchase = dateOfPurchase;
        this.userName = userName;
        this.totalAmount = totalAmount;

    }

    public int getPurchId() {
        return purchId;
    }

    public void setPurchId(int id) {
        this.purchId = purchId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUserName() {
        return userName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }
}
