package org.inventory_system.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.inventory_system.config.ErrorMesajes;
import org.inventory_system.interfaces.InventoryDAO;
import org.inventory_system.model.Product;
import org.inventory_system.model.Purchase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventoryDAOImpl extends Database implements InventoryDAO {
    ErrorMesajes error = new ErrorMesajes();

    @Override
    public void createInventory() {

    }

    @Override
    public ObservableList<Product> getProductsList() throws SQLException {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        int realQty = 0;
        int diff = 0;
        int total = 0;
        String sql = "SELECT id, name, purch_price, unit, quantity FROM products";
        try {
            this.connectDB();
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            Product product;
            while (resultSet.next()) {
                product = new Product(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("name"),
                        Double.parseDouble(resultSet.getString("purch_price")),
                        resultSet.getString("unit"),
                        Integer.parseInt(resultSet.getString("quantity")),
                        realQty,
                        diff,
                        total);
                productList.add(product);
            }
        } catch (Exception err) {
            error.getMessage(err);
        }finally {
            this.closeDB();
        }
        return productList;
    }

    @Override
    public void createNewInventory(int prodID, int realQty) throws SQLException {
        String sql = "UPDATE products SET quantity=? WHERE id=?";
        try{
            this.connectDB();
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setDouble(1, realQty);
            statement.setInt(2, prodID);
            statement.executeUpdate();
        }catch (Exception err){
            error.getMessage(err);
        }finally {
            this.closeDB();
        }
    }
}
