package org.inventory_system.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.inventory_system.DAO.Database;
import org.inventory_system.model.Product;
import org.inventory_system.interfaces.ProductDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl extends Database implements ProductDAO {

    @Override
    public ObservableList<Product> getProductsList1() throws SQLException {
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
}
