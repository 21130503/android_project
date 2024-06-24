package DAO;

import Controller.ForgetPasswordController;
import Model.User;
import Services.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {
    java.util.Date utilDate = new java.util.Date();
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

    public ArrayList<User> getAllUser() {
        Connection connection = null;
        ArrayList<User> listUser = new ArrayList<>();

        try {
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
//                user.setCreatedAt(resultSet.getDate("createdAt"));
                listUser.add(user);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return listUser;
    }
    public User getUser(String email) {
        Connection connection = null;
        User user = new User();

        if (checkEmailExist(email)){
            try {
                connection = Connect.getConnection();
                String sql = "select id, email,name, isAdmin, createdAt from user where email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    user.setEmail(resultSet.getString("email"));
                    user.setName(resultSet.getString("name"));
                    user.setAdmin(resultSet.getBoolean("isAdmin"));
//                user.setCreatedAt(resultSet.getDate("createdAt"));
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                Connect.closeConnection(connection);
            }
        }
        return user;
    }

    public boolean checkEmailExist(String email) {
        Connection connection = null;
        boolean checkEmail = false;
        try {
            connection = Connect.getConnection();
            String checkEmailQuery = "select email from user where email = ?";
            PreparedStatement preparedStatementCheckEmail = connection.prepareStatement(checkEmailQuery);
            preparedStatementCheckEmail.setString(1, email);
            ResultSet resEmail = preparedStatementCheckEmail.executeQuery();
            if (resEmail.next()) {
                checkEmail = true;
                return checkEmail;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Connect.closeConnection(connection);
        }
        return checkEmail;
    }


    public boolean register(String email, String password,String phoneNumber, String name) {
        Connection connection = null;
        if (checkEmailExist(email)) {
            return false;
        } else {
            try {
                connection = Connect.getConnection();
                String insert = "Insert into user( email, password,phoneNumber, name, createdAt) values (?,?,?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, phoneNumber);
                preparedStatement.setString(4, name);
                preparedStatement.setDate(5, sqlDate);
                int check = preparedStatement.executeUpdate();
                if (check >= 0) {
                    return true;
                } else {
                    return false;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public User login(String email, String password){
        Connection connection = null;
        try{
            connection = Connect
                    .getConnection();
            String sql = "select id, email, isAdmin, phoneNumber , name from user where email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("phoneNumber"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setAdmin(resultSet.getBoolean("isAdmin"));
                return user;
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return  null;
    }

    public boolean loginBoolean(String email, String password) {
        Connection connection = null;
        try{
            connection = Connect
                    .getConnection();
            String sql = "select id, email, isAdmin, phoneNumber , name from user where email = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return  false;
    }
    public User getUserById(String id) {
        Connection connection = null;
        try{
            connection = Connect
                    .getConnection();
            String sql = "select id, email, isAdmin, phoneNumber , name from user where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setName(resultSet.getString("name"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setAdmin(resultSet.getBoolean("isAdmin"));
                return user;
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return  null;
    }
    public boolean updatePassword(String email, String pass){
        Connection connection = null;
        if (!checkEmailExist(email)) {
            return false;
        }else {
            try {
                connection = Connect.getConnection();
                String query = "UPDATE user SET `password` = ?  WHERE email = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, pass);
                preparedStatement.setString(2, email);
                int check = preparedStatement.executeUpdate();
                if (check >= 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
