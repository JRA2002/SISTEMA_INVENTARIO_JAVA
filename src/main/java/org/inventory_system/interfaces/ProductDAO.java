package org.inventory_system.interfaces;

import javafx.collections.ObservableList;
import org.inventory_system.model.Product;

public interface ProductDAO {
    public ObservableList<Product> getProductsList1() throws Exception ;
}
