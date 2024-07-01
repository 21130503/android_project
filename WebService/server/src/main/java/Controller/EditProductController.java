package Controller;

import DAO.ProductDAO;
import Model.Product;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editProduct")
public class EditProductController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();
        Product product = gson.fromJson(req.getReader(), Product.class);
        ProductDAO dao = new ProductDAO();
        JsonObject jsonResponse = new JsonObject();

        try {
            int rowsUpdated = dao.editProduct(product);

            if (rowsUpdated > 0) {
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Cập nhật sản phẩm thành công");
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Không tìm thấy sản phẩm để cập nhật");
            }
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Đã xảy ra lỗi: " + e.getMessage());
        }

        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
