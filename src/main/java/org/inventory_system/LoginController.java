package org.inventory_system;

import com.password4j.Password;
import org.inventory_system.entity.User;
import org.inventory_system.entity.Session;
import org.inventory_system.config.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private ImageView loginImage;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private AnchorPane login_form;

    private User currentUser;

    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;
    private double x;
    private double y;


    public void textfieldDesign(){

        if(username.isFocused()){
            username.setStyle("-fx-background-color:#fff;"+"-fx-border-width:2px");
            password.setStyle("-fx-background-color:transparent;"+"-fx-border-width:1px");
        }else if(password.isFocused()){
            username.setStyle("-fx-background-color:transparent;"+"-fx-border-width:1px");
            password.setStyle("-fx-background-color:#fff;"+"-fx-border-width:2px");
            password.setOnAction(event -> {
                login();
            });
        }
    }

    public void onExit(){
        System.exit(0);
    }
    private String getHashedPass(){
        String hashedPass = "";
        Connection connection = Database.getInstance().connectDB();
        String sql = "SELECT password FROM users WHERE username=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username.getText());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                hashedPass = resultSet.getString("password");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("USUARIO NO ENCONTRADO.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return hashedPass;
    }

    public boolean isCorrectPassword(){
        String hashedPass = getHashedPass();
        String enterPass = password.getText();
        return  Password.check(enterPass,hashedPass).withBcrypt();
    }

    public void login(){
        String hashedPass = getHashedPass();
        if(isCorrectPassword()){
            connection= Database.getInstance().connectDB();
            String sql="SELECT * FROM users WHERE username=? and password=?";
            try{
                preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setString(1,username.getText());
                preparedStatement.setString(2, hashedPass);
                resultSet=preparedStatement.executeQuery();
                if(resultSet.next()){
                    int userId = resultSet.getInt("id");
                    String userName = resultSet.getString("username");
                    String userRol = resultSet.getString("rol");
                    User userData = new User(userId, userName, userRol);
                    Session.setCurrentUser(userData);

                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Mensaje de Bienvenida");
                    alert.setHeaderText(null);
                    alert.setContentText("Datos Correctos !");
                    alert.showAndWait();
                    login_btn.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage=new Stage();

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
            }catch (Exception err){
                err.printStackTrace();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("CONTRASEÑA INCORRECTA !!.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
