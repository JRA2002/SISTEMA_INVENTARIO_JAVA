package org.inventory_system.model;

public class Location {

    private final int loc_id;
    private final String loc_name;

    public Location(int loc_id, String loc_name){
        this.loc_id = loc_id;
        this.loc_name = loc_name;
    }

    public int getLoc_id(){
        return loc_id;
    }
    public String getLoc_name() {
        return loc_name;
    }

    @Override
    public String toString() {
        return loc_name;
    }

}
