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

@WebServlet(value = "/getProductByType")
public class getProductByTypeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDAO productDAO = new ProductDAO();
        Gson gson = new Gson();
        int page  = Integer.parseInt(req.getParameter("page"));
        int idTypeProduct =  Integer.parseInt(req.getParameter("type"));
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", true);
        jsonResponse.addProperty("message", "Thành công");
        JsonArray productByType = gson.toJsonTree(productDAO.getProductByType(page, idTypeProduct)).getAsJsonArray();
        jsonResponse.add("results", productByType);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
