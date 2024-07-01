package DAO;

import Model.Product;
import Services.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDAO {
    int recordSize = 5;
    public ArrayList<Product> getAllNewProduct(){
        Connection connection = null;
        ArrayList<Product> list = new ArrayList<>();
        try{
            connection  = Connect.getConnection();
            String query  = "Select id,nameProduct, price, imageProduct , description, type from new_product";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("nameProduct"));
                product.setPrice(resultSet.getInt("price"));
                product.setDescription(resultSet.getString("description"));
                product.setImage(resultSet.getString("imageProduct"));
                product.setType(resultSet.getInt("type"));
                list.add(product);
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return list;
    }
    public ArrayList<Product> getProductByType(int page, int idType){
        Connection connection = null ;
        ArrayList<Product> list  = new ArrayList<>();
        try{
            connection = Connect.getConnection();
            int startIndex = (page -1)*recordSize;
            String query  = "Select id,name, price, image , description, type from product where  type = ? LIMIT ? OFFSET ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idType);
            preparedStatement.setInt(2,recordSize);
            preparedStatement.setInt(3,startIndex);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getInt("price"));
                product.setDescription(resultSet.getString("description"));
                product.setImage(resultSet.getString("image"));
                product.setType(resultSet.getInt("type"));
                list.add(product);
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
        return list;
    }

    public boolean addProduct(String name, int price, String image, String description, int type){
        Connection connection = null;
        ProductDAO productDAO = new ProductDAO();
        int maxProductId = productDAO.getIndex();
        try {
            connection = Connect.getConnection();
            String query = "Insert into product values(?,?,?,?,?,?)";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, maxProductId+1);
            pr.setString(2, name);
            pr.setInt(3, price);
            pr.setString(4, image);
            pr.setString(5, description);
            pr.setInt(6, type);
            int resultSet1 = pr.executeUpdate();
            if (resultSet1 >= 0) {
                return true;
            } else {
                return false;
            }
        }catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }
    public int getIndex(){
        Connection connection = null;
        int maxId = 0;
        try{
            connection = Connect.getConnection();
            String query = "SELECT MAX(id) AS maxId FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                maxId = rs.getInt("maxId");
            }
            return maxId;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public int deleteProduct(int id) {
        Connection connection = null;
        int rowsDeleted;
        try {
            connection = Connect.getConnection();
            String sql = "DELETE FROM product WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            rowsDeleted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowsDeleted;
    }
    public int editProduct(Product product) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            String sql = "UPDATE product SET name=?, price=?, description=?, image=?, type=? WHERE id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, product.getImage());
            preparedStatement.setInt(5, product.getType());
            preparedStatement.setInt(6, product.getId());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
