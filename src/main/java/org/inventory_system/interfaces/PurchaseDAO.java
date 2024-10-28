package org.inventory_system.interfaces;

import javafx.collections.ObservableList;
import org.inventory_system.model.Purchase;

import java.sql.SQLException;

public interface PurchaseDAO {
    ObservableList<Purchase> getPurchaseList() throws SQLException;
    void deletePurchaseItem(int prodId, int purchaseId);
    void updatePurchaseItem(int qty, int prodId, int purchaseId);
    void insertPurchaseItem(int purchaseId, int prodId, int qty);
}
