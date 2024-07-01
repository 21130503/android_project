package Controller;

import DAO.UserDAO;
import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/get-token")
public class getTokenController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String isAdmin = req.getParameter("isAdmin");
        UserDAO userDAO = new UserDAO();
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();


        boolean status;
        String mess;
        List<User> result = new ArrayList<>();

        try {
                status = true;
                mess = "Thành công";
                User user = userDAO.getAdmin(isAdmin);
                System.out.println(user);
                if (user != null) {
                    result.add(user);
                }

        } catch (Exception e) {
            status = false;
            mess = "Đã có lỗi xảy ra: " + e.getMessage();
            e.printStackTrace(); // Log the error for debugging
        }

        jsonResponse.addProperty("success", status);
        jsonResponse.addProperty("message", mess);

        // Serialize the result list to JSON array
        JsonArray jsonResultArray = gson.toJsonTree(result).getAsJsonArray();
        jsonResponse.add("result", jsonResultArray);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
