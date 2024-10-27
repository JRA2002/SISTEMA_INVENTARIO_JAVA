package org.inventory_system.DAO;

import javafx.scene.control.ComboBox;
import org.inventory_system.interfaces.LocationDAO;
import org.inventory_system.model.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LocationDAOImpl extends Database implements LocationDAO {
    @Override
    public ComboBox<Location> getComboLocation() throws Exception {
        ComboBox<Location> comboLocation= new ComboBox<>();
        try{
            this.connectDB();

            String sql = "SELECT loc_id, loc_name FROM location";

            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            Location locationData;
            while (resultSet.next()) {
                locationData = new Location(
                        Integer.parseInt(resultSet.getString("loc_id")),
                        resultSet.getString("loc_name"));
                comboLocation.getItems().add(locationData);
            }

        } catch (Exception err) {
            err.printStackTrace();
        }finally {
            this.closeDB();
        }
        return comboLocation;
    }
}
