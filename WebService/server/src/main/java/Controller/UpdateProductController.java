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

@WebServlet("/updateProduct")
public class UpdateProductController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        Product product = gson.fromJson(req.getReader(), Product.class);
        JsonObject jsonResponse = new JsonObject();
        ProductDAO productDAO = new ProductDAO();

        boolean status;
        String mess;
        int rowsUpdated = productDAO.updateProduct(product);
        try {
            if (rowsUpdated > 0) {
                status = true;
                mess = "Thành công";
            } else {
                status = false;
                mess = "Thất bại";
            }
        } catch (Exception e) {
            status = false;
            mess = "Đã có lỗi xảy ra: " + e.getMessage();
            e.printStackTrace(); // Log the error for debugging

        }
        jsonResponse.addProperty("success", status);
        jsonResponse.addProperty("message", mess);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
