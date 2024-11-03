package org.inventory_system.config;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ErrorMesajes {
    public void getMessage(Exception err){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeight(500);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(err.getMessage());
        alert.showAndWait();
    }

    public void getInfo(String info){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeight(500);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(info);
        alert.showAndWait();
    }

    public Optional<ButtonType> getConfirm(String info){
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("CONFIRMACION");
        confirmationAlert.setHeaderText(info);
        confirmationAlert.setContentText("");
        return confirmationAlert.showAndWait();
    }

}
