package org.inventory_system.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
            error.getMessage(err);
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
            error.getMessage(err);
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
            error.getMessage(err);

        }
    }

    @Override
    public void insertPurchaseItem(int purchaseId, int prodId, int qty) {

        String sql = "INSERT INTO details_purchases(purchase_id,quantity,product_id) VALUES(?,?,?)";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, purchaseId);
            preparedStatement.setInt(2, qty);
            preparedStatement.setInt(3, prodId);
            preparedStatement.executeUpdate();
        } catch (Exception err) {
            error.getMessage(err);
        }
    }

    @Override
    public int getPurchaseId() {
        int purchaseId = 0;
        String sql = "SELECT MAX(purchase_id) AS purchase_id FROM purchases";
        try {
            this.connectDB();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                purchaseId = resultSet.getInt("purchase_id");
            }
        } catch (Exception err) {
            error.getMessage(err);
        }
        return purchaseId;
    }

    @Override
    public void cancelPurchase(int purchaseId) {

        String sql = "DELETE FROM purchases WHERE purchase_id=?";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, purchaseId);
            preparedStatement.executeUpdate();

        } catch (Exception err) {
            error.getMessage(err);
        }
    }

    @Override
    public void createPurchase(int userId,String datePurchase) {
        String sql = "INSERT INTO purchases(date,user_id)VALUES(?,?)";

        try {
            this.connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, datePurchase);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();

        } catch (Exception err) {
            error.getMessage(err);
        }
    }

    @Override
    public String getPurchaseAmount(int purchaseId) {
        String result = "";
        String sql = "SELECT SUM(dp.quantity * p.purch_price) AS total_amount\n" +
                "FROM details_purchases AS dp\n" +
                "INNER JOIN products AS p ON p.id = dp.product_id\n" +
                "WHERE dp.purchase_id = ?";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, purchaseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("total_amount");
            }
        } catch (Exception err) {
            error.getMessage(err);
        }
        return result;
    }

    @Override
    public double getTotalPurchase() {
        double total = 0;
        String sql = "SELECT COUNT(purchase_id) as total_purchase FROM purchases";
        try {
            this.connectDB();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                total = resultSet.getDouble("total_purchase");
            }
        } catch (Exception err) {
            error.getMessage(err);
        }
    return total;
    }
}
