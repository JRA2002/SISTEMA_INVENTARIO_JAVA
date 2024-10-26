package org.inventory_system.DAO;

import org.inventory_system.model.Session;
import org.inventory_system.model.User;
import org.inventory_system.interfaces.UserDAO;

public class UserDAOImpl implements UserDAO {
    @Override
    public String getUserById() {
        return "hello everyone theres DAO is working";
    }
    @Override
    public String getUsername(){
        User loggedInUser = Session.getCurrentUser();
        return loggedInUser.getUsername().toUpperCase();
    }
}
