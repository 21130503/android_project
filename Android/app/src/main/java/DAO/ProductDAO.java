package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import  JDBC.jdbc;
public class ProductDAO {
    public void getData(){
        Connection connection = null;
        try{
            connection = jdbc.getConnection();;
            String sql = "select * from product";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet res = preparedStatement.executeQuery();
            System.out.println(res);
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            jdbc.closeConnection(connection);

        }

    }

    public static void main(String[] args) {

    }

}
