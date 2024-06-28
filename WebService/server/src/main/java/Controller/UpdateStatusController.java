package Controller;

import DAO.OrderDAO;
import DAO.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(value = "/update-status")
public class UpdateStatusController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idOrder = req.getParameter("idOrder");
        String status = req.getParameter("status");
        OrderDAO orderDAO = new OrderDAO();
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        if(orderDAO.updateStatus(Integer.parseInt(idOrder), status)){
            jsonResponse.addProperty("success", true);
            jsonResponse.addProperty("message", "Thành công");
        }
        else{
            jsonResponse.addProperty("success", false);
            jsonResponse.addProperty("message", "Thất bại");
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
