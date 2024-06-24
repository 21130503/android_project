package DAO;

import Model.Product;
import Model.User;
import Services.Connect;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {
    Gson gson = new Gson();
    int idOrder = 0;
    UserDAO userDAO = new UserDAO();
    public boolean createOrder(String idUser, String address, String totalPrice, String cart){
            this.mainOrder(idUser,address, totalPrice);

        try {
            if(idOrder!=0 && this.ct_order(cart)){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
    public int mainOrder(String idUser, String address, String totalPrice){
        Connection connection = null;
        ResultSet resultSet= null;
        User user = userDAO.getUserById(idUser);
        try{
            connection = Connect.getConnection();
            String sql = "Insert into orderproduct(idUser, address, phoneNumber, totalPrice, status) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, Integer.parseInt(idUser));
            preparedStatement.setString(2, address);
            preparedStatement.setString(3, user.getPhoneNumber());
            System.out.println("abc");
            preparedStatement.setString(4, totalPrice);
            preparedStatement.setString(5, "Đang chuẩn bị");
            int check = preparedStatement.executeUpdate();
            System.out.println("check");
            if(check>=0){
                resultSet = preparedStatement.getGeneratedKeys();
                idOrder = resultSet.getInt(1);
                return idOrder;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return  idOrder;
    }
    public boolean ct_order(String cart) throws SQLException {
        Product[] products =  gson.fromJson(cart, Product[].class);
        boolean checkBool = true;
        Connection connection = null;
        connection = Connect.getConnection();
        for (Product product:products) {
            String sql = "Insert into ct_order(idOrder, idProduct, quantity) values (?,?,?)";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, String.valueOf(idOrder));
                preparedStatement.setString(2, String.valueOf(product.getId()));
                preparedStatement.setString(2, String.valueOf(product.getCount()));
                int check = preparedStatement.executeUpdate();
                if(check< 0){
                    checkBool =false;
                    return  checkBool;
                }
                else{
                    preparedStatement.addBatch();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
        return  checkBool;
    }
}
