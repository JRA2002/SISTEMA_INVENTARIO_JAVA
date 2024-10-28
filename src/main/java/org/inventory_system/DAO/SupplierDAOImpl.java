package org.inventory_system.DAO;

import javafx.scene.control.ComboBox;
import org.inventory_system.interfaces.SupplierDAO;
import org.inventory_system.model.Location;
import org.inventory_system.model.Supplier;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SupplierDAOImpl extends Database implements SupplierDAO {
    @Override
    public ComboBox<Supplier> getComboSupplier() throws Exception {
        ComboBox<Supplier> comboSupplier= new ComboBox<>();
        try{
            this.connectDB();

            String sql = "SELECT id, supp_name, phone FROM supplier";

            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            Supplier supplierData;
            while (resultSet.next()) {
                supplierData = new Supplier(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("supp_name"),
                        resultSet.getString("supp_name"));
                comboSupplier.getItems().add(supplierData);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }finally {
            this.closeDB();
        }
        return comboSupplier;
    }
}
