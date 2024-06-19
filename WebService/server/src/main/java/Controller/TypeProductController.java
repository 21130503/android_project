package Controller;

import DAO.TypeProductDAO;
import Model.TypeProduct;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/typeProduct")
public class TypeProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TypeProductDAO dao = new TypeProductDAO();
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        try {
            JsonArray typeProductJsonArray = gson.toJsonTree(dao.listTypeProduct()).getAsJsonArray();
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message", "Thành công");
            jsonResponse.add("results", typeProductJsonArray);
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Có lỗi xảy ra: " + e.getMessage());
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TypeProductDAO dao = new TypeProductDAO();
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        try {
            // Đọc dữ liệu từ yêu cầu
            TypeProduct newTypeProduct = gson.fromJson(req.getReader(), TypeProduct.class);

            // Kiểm tra null cho trường 'name'
            if (newTypeProduct.getName() == null || newTypeProduct.getName().isEmpty()) {
                throw new IllegalArgumentException("Tên loại sản phẩm không được để trống");
            }

            // Kiểm tra null cho trường 'image'
            if (newTypeProduct.getImage() == null || newTypeProduct.getImage().isEmpty()) {
                throw new IllegalArgumentException("URL hình ảnh không được để trống");
            }

            // Thêm loại sản phẩm mới vào cơ sở dữ liệu
            boolean success = dao.addTypeProduct(newTypeProduct);
            if (success) {
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Thêm loại sản phẩm thành công");
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Thêm loại sản phẩm thất bại");
            }
        } catch (IllegalArgumentException e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", e.getMessage());
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Có lỗi xảy ra: " + e.getMessage());
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}