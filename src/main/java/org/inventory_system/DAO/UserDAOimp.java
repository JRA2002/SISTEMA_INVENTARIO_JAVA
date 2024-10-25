package org.inventory_system.DAO;

import org.inventory_system.interfaces.User1DAO;

public class UserDAOimp implements User1DAO {
    @Override
    public String getUserById() {
        String user1 = "hello ther DAO is working";
        return user1;
    }
}
