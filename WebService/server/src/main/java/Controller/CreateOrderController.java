package Controller;

import DAO.OrderDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/create-order")
public class CreateOrderController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idUser = req.getParameter("idUser");
        String totalPrice = req.getParameter("totalPrice");
        String address = req.getParameter("address");
        String cart = req.getParameter("carts");
        OrderDAO orderDAO = new OrderDAO();
        System.out.println("start");
        JsonObject jsonResponse = new JsonObject();
        Gson gson = new Gson();
        String message = null;
        boolean success = false;
        System.out.println(cart);
        if(orderDAO.createOrder(idUser,address, totalPrice, cart)){
            success = true;
            message = "Thành công";

        }
        else{
            success = false;
            message = "Thất bại";
        }

        jsonResponse.addProperty("success", success);
        jsonResponse.addProperty("message", message);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
