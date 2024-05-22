package DAO;

import Model.TypeProduct;
import Services.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TypeProductDAO {
    public ArrayList<TypeProduct> listTypeProduct(){
        Connection connection = null;
        ArrayList<TypeProduct> list = new ArrayList<>();
        try{
            connection = Connect.getConnection();
            // Câu truy vấn lấy dữ liệu topic
            String getAllTypeProduct = "select id, name, image from typeProduct";
            PreparedStatement preparedStatement = connection.prepareStatement(getAllTypeProduct);
            ResultSet resultSetGetTopic = preparedStatement.executeQuery();
            while (resultSetGetTopic.next()) {
                TypeProduct typeProduct = new TypeProduct();
                typeProduct.setId(resultSetGetTopic.getInt("id"));
                typeProduct.setName(resultSetGetTopic.getString("name"));
                typeProduct.setImage(resultSetGetTopic.getString("image"));
                list.add(typeProduct);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
