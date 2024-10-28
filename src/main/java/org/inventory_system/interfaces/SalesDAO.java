package org.inventory_system.interfaces;

import javafx.collections.ObservableList;
import org.inventory_system.model.SalesDetails;

import java.sql.SQLException;

public interface SalesDAO {
    void insertNewSale(String dateSale, int userId) throws SQLException;
    void insertNewDetailsSales(int salesId, int productId, int quantity) throws SQLException;
    ObservableList<SalesDetails> getSalesList(int salesId);
    int calculateFinalAmount(int salId) throws SQLException;
    void deleteSale(int saleId);
    void deleteSalesDetails(int prodId, int saleId);
}
