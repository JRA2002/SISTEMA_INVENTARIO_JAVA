package org.inventory_system.interfaces;

import javafx.scene.control.ComboBox;
import org.inventory_system.model.Category;
import javafx.collections.ObservableList;

public interface CategoryDAO {
    ComboBox<Category> getComboCategory() throws Exception;
}
