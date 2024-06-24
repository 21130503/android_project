package Controller;

import DAO.ProductDAO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/search")
public class SearchProductController  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String key = req.getParameter("key");
//        Gson gson = new Gson();
//        JsonObject jsonResponse = new JsonObject();
//        boolean sucess = false;
//        String mess = "Không có sản phẩm" ;
//        ProductDAO productDAO = new ProductDAO();
//        if(productDAO.searchProduct(key).size() >0) {
//            sucess = true;
//            mess ="Thành công";
//        }
//        jsonResponse.addProperty("success", sucess);
//        jsonResponse.addProperty("message", mess);
//        JsonArray products = gson.toJsonTree(productDAO.searchProduct(key)).getAsJsonArray();
//        jsonResponse.add("results", products);
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
//        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
