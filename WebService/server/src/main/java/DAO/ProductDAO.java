package DAO;

import Model.Product;
import Services.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public Product getProductById(int id) {
        Connection connection = null;
        Product product = new Product();
        try{
            connection = Connect.getConnection();
            String sql = "select id, price,image,description, name, type from product where id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                product.setId(resultSet.getInt("id"));
                product.setPrice(resultSet.getInt("price"));
                product.setType(resultSet.getInt("type"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setImage(resultSet.getString("image"));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        System.out.println(product.toString());
        return  product;
    }
}
