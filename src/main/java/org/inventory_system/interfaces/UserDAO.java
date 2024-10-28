package org.inventory_system.interfaces;

import javafx.collections.ObservableList;
import org.inventory_system.model.User;

import java.sql.SQLException;

public interface UserDAO {
    String getUserById();
    String getUsername();
    ObservableList<User> getUsersList();
    void createNewUser(String username, String password, String email, String phone, String rol) throws SQLException;
    void updateUser(String user,String email, String phone, String rol, String password) throws SQLException;
    void deleteUser(String username);
}
