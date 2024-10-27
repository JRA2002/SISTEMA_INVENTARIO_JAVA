package org.inventory_system.interfaces;

import javafx.scene.control.ComboBox;
import org.inventory_system.model.Location;

public interface LocationDAO {
    ComboBox<Location> getComboLocation() throws Exception;
}
