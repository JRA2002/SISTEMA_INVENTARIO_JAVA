package org.inventory_system.model;

public class Category {
    private final int id;
    private final String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getNameCat() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
