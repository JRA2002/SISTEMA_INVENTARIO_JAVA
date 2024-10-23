package org.inventory_system;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

import static org.burningwave.core.assembler.StaticComponentContainer.Modules;

public class Application extends javafx.application.Application {

        private double x;
        private double y;

        @Override
        public void start(Stage stage) throws IOException{
            URL rootUrl = getClass().getResource("login-view.fxml");
            System.out.println(rootUrl);
            Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
            System.out.println(root);
            Scene scene = new Scene(root);
            stage.setTitle("Inventory Management System");
            root.setOnMousePressed((event)->{
                x=event.getSceneX();
                y=event.getSceneY();
            });
            root.setOnMouseDragged((event)->{
                stage.setX(event.getScreenX()-x);
                stage.setY(event.getScreenY()-y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        }
        public static void main(String[] args) {
            launch();
        }
}
