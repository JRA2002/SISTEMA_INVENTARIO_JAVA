package org.inventory_system.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.inventory_system.interfaces.SalesDAO;
import org.inventory_system.model.SalesDetails;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesDAOImpl extends Database implements SalesDAO {
    @Override
    public void insertNewSale(String dateSale, int userId) throws SQLException {
        try{
            this.connectDB();

            String sql = "INSERT INTO sales(date,user_id) VALUES(?,?)";

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, dateSale);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }finally {
            this.closeDB();
        }
    }

    @Override
    public void insertNewDetailsSales(int salesId, int productId, int quantity) throws SQLException {
        try{
            this.connectDB();

            String sql = "INSERT INTO details_sales(sales_id,quantity,product_id) VALUES(?,?,?)";

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, salesId);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setInt(3, productId);
            preparedStatement.executeUpdate();
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }finally {
            this.closeDB();
        }
    }

    @Override
    public ObservableList<SalesDetails> getSalesList(int salesId) {
        ObservableList<SalesDetails> salesList = FXCollections.observableArrayList();
        String sql = "SELECT p.id,ds.quantity,p.name, p.price,(p.price*ds.quantity) AS subtotal " +
                "FROM details_sales AS ds " +
                "JOIN products AS p ON ds.product_id=p.id\n" +
                "WHERE ds.sales_id=?";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, salesId);
            ResultSet resultSet = preparedStatement.executeQuery();

            SalesDetails salesData;
            while(resultSet.next()) {
                salesData = new SalesDetails(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("name"),
                        Integer.parseInt(resultSet.getString("quantity")),
                        Double.parseDouble(resultSet.getString("price")),
                        Double.parseDouble(resultSet.getString("subtotal")));
                salesList.addAll(salesData);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return salesList;
    }

    @Override
    public int calculateFinalAmount(int salId) throws SQLException {
        int saleId = 0;
        String sql = "SELECT ds.sales_id,SUM(ds.quantity*p.price) AS final_amount " +
                "FROM details_sales AS ds JOIN products AS p\n" +
                "WHERE ds.product_id=p.id and ds.sales_id=?;";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, salId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                saleId = Integer.parseInt(resultSet.getString("final_amount"));
            }
        } catch (Exception err) {
            err.printStackTrace();
        }finally {
            this.closeDB();
        }
        return saleId;
    }

    @Override
    public void deleteSale(int saleId) {
        String sql = "DELETE FROM sales WHERE sales_id=?";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, saleId);
            preparedStatement.executeUpdate();
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void deleteSalesDetails(int prodId, int saleId) {
        String sql = "DELETE FROM details_sales WHERE product_id=? and sales_id=?";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, prodId);
            preparedStatement.setInt(2, saleId);
            preparedStatement.executeUpdate();
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }
}
