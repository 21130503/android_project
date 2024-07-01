package Controller;

import DAO.ProductDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB)
@WebServlet(value = "/addProduct")
public class AddProductController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int price = Integer.parseInt(req.getParameter("price"));
        String image = req.getParameter("image");
        String description = req.getParameter("description");
        int type = Integer.parseInt(req.getParameter("type"));

        JsonObject jsonResponse = new JsonObject();
        Gson gson = new Gson();
        ProductDAO productDAO = new ProductDAO();

        boolean status;
        String mess;
        try {
            if (productDAO.addProduct(name, price, image, description, type)) {
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
