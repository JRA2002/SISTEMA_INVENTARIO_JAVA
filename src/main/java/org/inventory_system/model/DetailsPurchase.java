package org.inventory_system.model;

public class DetailsPurchase {
    //private int idProd;
    private String prodName;
    private String supplier;
    private Double priceC;
    private int qtyP;
    private Double total;

    // Constructor, getters y setters
    public DetailsPurchase(String prodName,String supplier, Double priceC, int qtyP, Double total) {
        //this.idProd = idProd;
        this.prodName = prodName;
        this.priceC = priceC;
        this.qtyP = qtyP;
        this.supplier = supplier;
        this.total = total;
    }

    //public Integer getIdProd() {
        //return idProd;
    //}

    public String getProdName() {
        return prodName;
    }

    public String getSupplier() {
        return supplier;
    }

    public Double getPriceC() {
        return priceC;
    }

    public Integer getQtyP() {
        return qtyP;
    }

    public void setQtyP(Integer qtyP) {
        this.qtyP = qtyP;
    }

    public Double getTotal() {
        return total;
    }
}
