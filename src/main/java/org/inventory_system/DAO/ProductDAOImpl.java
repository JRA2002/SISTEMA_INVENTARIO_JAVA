package org.inventory_system.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.inventory_system.DAO.Database;
import org.inventory_system.model.Location;
import org.inventory_system.model.Product;
import org.inventory_system.interfaces.ProductDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDAOImpl extends Database implements ProductDAO {

    @Override
    public ObservableList<Product> getProductsList() throws SQLException {
        ObservableList<Product> productsList = FXCollections.observableArrayList();
        this.connectDB();

        String sql = "SELECT pd.id,pd.name,pd.unit, pd.quantity,pd.price,ct.cat_name,pd.exp_date,ln.loc_name FROM products AS pd \n" +
                "JOIN category AS ct \n" +
                "JOIN location AS ln \n" +
                "WHERE pd.cat_id=ct.id and pd.loc_id=ln.loc_id";

        PreparedStatement statement = this.connection.prepareStatement(sql);
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            Product product;
            while (resultSet.next()) {
                product = new Product(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("name"),
                        resultSet.getString("unit"),
                        Integer.parseInt(resultSet.getString("quantity")),
                        Double.parseDouble(resultSet.getString("price")),
                        resultSet.getString("cat_name"),
                        resultSet.getDate("exp_date").toLocalDate(),
                        resultSet.getString("loc_name")
                );
                System.out.println("implementacion" + product);
                productsList.add(product);
            }
        }catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
        finally {
            this.closeDB();
        }
        return productsList;
    }

    @Override
    public void updateProduct(double price, int qty, String expDate, int suppId, int locId, int id) throws SQLException {

        String sql = "UPDATE products SET price=?, quantity=?, exp_date=?, supp_id=?, loc_id=? WHERE id=?";
        try{
            this.connectDB();
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setDouble(1, price);
            statement.setInt(2, qty);
            statement.setString(3, expDate);
            statement.setInt(4, suppId);
            statement.setInt(5, locId);
            statement.setInt(6, id);
            statement.executeUpdate();
        }catch (Exception err){
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
    public void updateProductStock(int prodId, int quantity) throws SQLException {
        String sql = "UPDATE products SET quantity=? WHERE id=?";
        try{
            this.connectDB();
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setInt(1, quantity);
            statement.setInt(2, prodId);
            statement.executeUpdate();
        }catch (Exception err){
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
    public void deleteProduct(int prodId) throws SQLException {
        String sql = "DELETE FROM products WHERE id=?";
        try{
            this.connectDB();
            PreparedStatement statement = this.connection.prepareStatement(sql);
            statement.setInt(1, prodId);
            statement.executeUpdate();
        }catch (Exception err){
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
    public void addProduct(String name, int catId, int quantity, double price, String expDate, String unit, int suppId, int locId) throws SQLException {
        try{
            this.connectDB();

            String sql = "INSERT INTO products(name, cat_id, quantity, price, exp_date, unit, supp_id, loc_id)VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, catId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, expDate);
            preparedStatement.setString(6, unit);
            preparedStatement.setInt(7, suppId);
            preparedStatement.setInt(8, locId);
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
    public ObservableList<Product> getProductsListPurchase() throws SQLException {
        ObservableList<Product> productsListPurchase = FXCollections.observableArrayList();
        int qty = 0;
        String sql = "SELECT p.id,p.name,sp.supp_name,p.purch_price,p.unit\n" +
                "FROM products AS p\n" +
                "INNER JOIN supplier AS sp ON p.supp_id=sp.id";
        try {
            this.connectDB();
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            Product productPurchase;
            while (resultSet.next()) {
                productPurchase = new Product(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("name"),
                        resultSet.getString("supp_name"),
                        Double.parseDouble(resultSet.getString("purch_price")),
                        resultSet.getString("unit"),
                        qty);
                productsListPurchase.addAll(productPurchase);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return productsListPurchase;
    }
}

