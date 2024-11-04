package org.inventory_system.interfaces;

import javafx.collections.ObservableList;
import org.inventory_system.model.Product;

import java.sql.SQLException;

public interface InventoryDAO {
    void createInventory();
    ObservableList<Product> getProductsList() throws SQLException;
    void createNewInventory(int prodID, int realQty) throws SQLException;
}
