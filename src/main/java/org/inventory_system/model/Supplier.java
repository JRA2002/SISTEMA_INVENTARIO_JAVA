package org.inventory_system.model;

public class Supplier {
    private int supp_id;
    private String supp_name;
    private String supp_phone;

    public Supplier(int supp_id, String supp_name, String supp_phone){
        this.supp_id = supp_id;
        this.supp_name = supp_name;
        this.supp_phone = supp_phone;
    }

    public int getSupp_id(){
        return supp_id;
    }
    public String getSupp_name() {
        return supp_name;
    }

    public String getSupp_phone() {
        return supp_phone;
    }

    @Override
    public String toString() {
        return supp_name;
    }
}
