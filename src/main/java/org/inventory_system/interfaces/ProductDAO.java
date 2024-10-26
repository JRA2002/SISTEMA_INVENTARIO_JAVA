package org.inventory_system.interfaces;

import javafx.collections.ObservableList;
import org.inventory_system.model.Product;

import java.sql.SQLException;
import java.util.Date;

public interface ProductDAO {
    ObservableList<Product> getProductsList() throws Exception ;
    void updateProduct(double price, int qty, String expDate, int suppId, int locId, int id) throws SQLException;
    void updateProductStock(int prodId, int quantity) throws SQLException;
    void deleteProduct(int prodId) throws SQLException;
}
