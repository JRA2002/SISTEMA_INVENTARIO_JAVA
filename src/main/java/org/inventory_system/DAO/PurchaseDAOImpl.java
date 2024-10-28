package org.inventory_system.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.inventory_system.interfaces.PurchaseDAO;
import org.inventory_system.model.Purchase;
import org.inventory_system.config.ErrorMesajes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class PurchaseDAOImpl extends Database implements PurchaseDAO {
    ErrorMesajes error = new ErrorMesajes();

    @Override
    public ObservableList<Purchase> getPurchaseList() throws SQLException {
        ObservableList<Purchase> purchaseList = FXCollections.observableArrayList();

        String sql = "SELECT pu.purchase_id,pu.date,u.username ,SUM(p.purch_price*dp.quantity) AS total FROM purchases pu\n" +
                "INNER JOIN details_purchases AS dp ON pu.purchase_id=dp.purchase_id\n" +
                "INNER JOIN products AS p ON dp.product_id=p.id\n" +
                "INNER JOIN users AS u ON pu.user_id=u.id\n" +
                "GROUP BY pu.purchase_id";
        try {
            this.connectDB();
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            Purchase purchase;
            while (resultSet.next()) {
                purchase = new Purchase(
                        resultSet.getInt("purchase_id"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("username"),
                        resultSet.getDouble("total"));
                purchaseList.add(purchase);
            }
        } catch (Exception err) {
            error.errorMessage(err);
        }finally {
            this.closeDB();
        }
        return purchaseList;
    }

    @Override
    public void deletePurchaseItem(int prodId, int purchaseId) {

        String sql = "DELETE FROM details_purchases WHERE product_id=? and purchase_id=?";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, prodId);
            preparedStatement.setInt(2, purchaseId);
            preparedStatement.executeUpdate();
        } catch (Exception err) {
            error.errorMessage(err);
        }
    }

    @Override
    public void updatePurchaseItem(int qty, int prodId, int purchaseId) {

        String sql = "UPDATE details_purchases SET quantity=? WHERE product_id=? and purchase_id=?";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, qty);
            preparedStatement.setInt(2, prodId);
            preparedStatement.setInt(3, purchaseId);
            preparedStatement.executeUpdate();
        } catch (Exception err) {
            error.errorMessage(err);

        }
    }

    @Override
    public void insertPurchaseItem(int purchaseId, int prodId, int qty) {
        
    }
}
