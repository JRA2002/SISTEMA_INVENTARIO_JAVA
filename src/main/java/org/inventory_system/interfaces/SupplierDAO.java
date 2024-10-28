package org.inventory_system.interfaces;

import javafx.scene.control.ComboBox;
import org.inventory_system.model.Supplier;

public interface SupplierDAO {
    ComboBox<Supplier> getComboSupplier() throws Exception;
}
