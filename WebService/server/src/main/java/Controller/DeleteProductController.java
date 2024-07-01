package Controller;

import DAO.ProductDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteProduct")
public class DeleteProductController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        ProductDAO dao = new ProductDAO();
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        try {
            int rowsAffected = dao.deleteProduct(id);
            if (rowsAffected > 0) {
                jsonResponse.addProperty("success", true);
                jsonResponse.addProperty("message", "Xóa sản phẩm thành công");
            } else {
                jsonResponse.addProperty("success", false);
                jsonResponse.addProperty("message", "Không tìm thấy sản phẩm để xóa");
            }
        } catch (Exception e) {
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Đã xảy ra lỗi: " + e.getMessage());
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
