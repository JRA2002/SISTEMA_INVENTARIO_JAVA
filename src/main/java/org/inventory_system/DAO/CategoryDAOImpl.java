package org.inventory_system.DAO;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import org.inventory_system.interfaces.CategoryDAO;
import org.inventory_system.model.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CategoryDAOImpl extends Database implements CategoryDAO {
    @Override
    public ComboBox<Category> getComboCategory() throws Exception {
        ComboBox<Category> categoryCombo = new ComboBox<>();
        try{
            this.connectDB();

            String sql = "SELECT id, cat_name FROM category";

            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            Category categoryData;
            while (resultSet.next()) {
                categoryData = new Category(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("cat_name"));
                categoryCombo.getItems().add(categoryData);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }
        return categoryCombo;
    }
}
