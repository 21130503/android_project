package DAO;

import Model.User;
import Services.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {
    public ArrayList<User> getAllUser(){
        Connection connection = null;
        ArrayList<User> listUser = new ArrayList<>();
        try{
            connection = Connect.getConnection();
            String sql = "select id, email,name, isAdmin, createdAt from user";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setAdmin(resultSet.getBoolean("isAdmin"));
                user.setCreatedAt(resultSet.getDate("createdAt"));
                listUser.add(user);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            Connect.closeConnection(connection);
        }
        return  listUser;
    }
}
