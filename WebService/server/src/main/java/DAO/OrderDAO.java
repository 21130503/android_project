package DAO;

import Model.Product;
import Model.User;
import Services.Connect;
import com.google.gson.Gson;

import java.sql.*;

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
//    public int mainOrder(String idUser, String address, String totalPrice){
//        Connection connection = null;
//        ResultSet resultSet= null;
//        User user = userDAO.getUserById(idUser);
//        try{
//            connection = Connect.getConnection();
//            String sql = "Insert into orderproduct(idUser, address, phoneNumber, totalPrice, status) values (?,?,?,?,?)";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
//            preparedStatement.setInt(1, Integer.parseInt(idUser));
//            preparedStatement.setString(2, address);
//            preparedStatement.setString(3, user.getPhoneNumber());
//            System.out.println("abc");
//            preparedStatement.setString(4, totalPrice);
//            preparedStatement.setString(5, "Đang chuẩn bị");
//            int check = preparedStatement.executeUpdate();
//            System.out.println("check");
//            if(check>=0){
//                String getIdSql = "SELECT max(id) as id from orderproduct";
//                PreparedStatement preparedStatement1 = connection.prepareStatement(getIdSql);
//                resultSet = preparedStatement1.executeQuery();
//                idOrder = resultSet.getInt("id");
//                System.out.println(idUser);
//                return idOrder;
//            }
//        }
//        catch (SQLException e){
//            e.printStackTrace();
//        }
//        return  idOrder;
//    }
public int mainOrder(String idUser, String address, String totalPrice) {
    Connection connection = null;
    ResultSet resultSet = null;
    User user = userDAO.getUserById(idUser);
    try {
        connection = Connect.getConnection();
        String sql = "INSERT INTO orderproduct(idUser, address, phoneNumber, totalPrice, status) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, Integer.parseInt(idUser));
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, user.getPhoneNumber());
        preparedStatement.setString(4, totalPrice);
        preparedStatement.setString(5, "Đang chuẩn bị");

        int check = preparedStatement.executeUpdate();
        if (check > 0) {
            // Sau khi chèn thành công, lấy ID của hàng vừa chèn bằng cách sử dụng truy vấn SELECT
            String getIdSql = "SELECT MAX(id) as id FROM orderproduct";
            PreparedStatement getIdStatement = connection.prepareStatement(getIdSql);
            resultSet = getIdStatement.executeQuery();
            if (resultSet.next()) {
                idOrder = resultSet.getInt("id");
                System.out.println("Generated Order ID: " + idOrder);
            }
            getIdStatement.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return idOrder;
}

    public boolean ct_order(String cart) throws SQLException {
        Product[] products =  gson.fromJson(cart, Product[].class);
        boolean checkBool = true;
        Connection connection = null;
        connection = Connect.getConnection();
        for (Product product:products) {
            System.out.println("Count:" + product.getCount());
            System.out.println("id:" + product.getIdProduct());
            String sql = "Insert into ct_order(idOrder, idProduct, quantity) values (?,?,?)";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, idOrder);
                preparedStatement.setInt(2, product.getIdProduct());
                preparedStatement.setInt(3, product.getCount());
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
