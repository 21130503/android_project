package DAO;

import Model.TypeProduct;
import Services.Connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TypeProductDAO {
    public ArrayList<TypeProduct> listTypeProduct() {
        Connection connection = null;
        ArrayList<TypeProduct> list = new ArrayList<>();
        try {
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

    public boolean addTypeProduct(TypeProduct typeProduct) {
        Connection connection = null;
        try {
            connection = Connect.getConnection();
            if (connection != null) {
                System.out.println("Connected to database!");
            } else {
                System.out.println("Failed to connect to database!");
            }

            // Kiểm tra dữ liệu đầu vào
            if (typeProduct.getName() == null || typeProduct.getName().isEmpty()) {
                System.out.println("Name is empty!");
                return false;
            }

            if (typeProduct.getImage() == null || typeProduct.getImage().isEmpty()) {
                System.out.println("Image URL is empty!");
                return false;
            }

            String insertTypeProduct = "INSERT INTO typeProduct (name, image) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertTypeProduct);
            preparedStatement.setString(1, typeProduct.getName());
            preparedStatement.setString(2, typeProduct.getImage());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Inserted successfully!");
            } else {
                System.out.println("Failed to insert data!");
            }
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}