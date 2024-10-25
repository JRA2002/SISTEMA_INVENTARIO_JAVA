package org.inventory_system.DAO;

import org.inventory_system.model.User1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOimp implements User1DAO {
    @Override
    public String getUserById() {
        String user1 = "hello ther DAO is working";
        return user1;
    }
}
