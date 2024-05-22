package DAO;

import Model.Product;
import Services.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {
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

}
