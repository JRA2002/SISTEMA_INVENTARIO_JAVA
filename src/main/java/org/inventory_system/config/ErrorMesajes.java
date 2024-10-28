package org.inventory_system.config;

import javafx.scene.control.Alert;

public class ErrorMesajes {
    public void errorMessage(Exception err){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeight(500);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(err.getMessage());
        alert.showAndWait();
    }
}
