package org.inventory_system.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.inventory_system.model.Session;
import org.inventory_system.model.User;
import org.inventory_system.interfaces.UserDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl extends Database implements UserDAO {
    @Override
    public String getUserById() {
        return "hello everyone theres DAO is working";
    }
    @Override
    public String getUsername(){
        User loggedInUser = Session.getCurrentUser();
        return loggedInUser.getUsername().toUpperCase();
    }

    @Override
    public ObservableList<User> getUsersList() {
        ObservableList<User> usersList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        try {
            this.connectDB();
            PreparedStatement statement = this.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            User user;
            while (resultSet.next()) {
                user = new User(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getString("rol"));
                usersList.add(user);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return usersList;
    }

    @Override
    public void createNewUser(String username, String hashedPassword, String email, String phone, String rol) throws SQLException {
        String sql = "INSERT INTO users(username,password,email,phone,rol) VALUES(?,?,?,?,?)";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phone);
            preparedStatement.setString(5, rol);
            preparedStatement.executeUpdate();
        } catch (Exception err) {
            err.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }finally {
            this.closeDB();
        }
    }

    @Override
    public void updateUser(String user, String email, String phone, String rol, String password) throws SQLException {

        String sql = "UPDATE users SET email=? ,phone=?, rol=? ,password=? WHERE username='" + user + "'";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, phone);
            preparedStatement.setString(3, rol);
            preparedStatement.setString(4, password);
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Message");
                alert.setHeaderText(null);
                alert.setContentText("USUARIO ACTUALIZADO CORRECTAMENTE.");
                alert.showAndWait();
            }
        } catch (Exception err) {
            err.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }finally {
            this.closeDB();
        }
    }

    @Override
    public void deleteUser(String username) {
        String sql = "DELETE FROM users WHERE username=?";
        try {
            this.connectDB();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (Exception err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeight(500);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText(err.getMessage());
            alert.showAndWait();
        }
    }
}
