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

@WebServlet(value = "/register")
public class RegisterController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phoneNumber = req.getParameter("phoneNumber");
        String username = req.getParameter("username");
        String uid = req.getParameter("uid");
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();
        UserDAO userDAO = new UserDAO();
        System.out.println(email); // For debugging purposes
        System.out.println("uid : " + uid); // For debugging purposes

        boolean status;
        String mess;
        List<User> result = new ArrayList<>();

        try {
            if (userDAO.register(email, password, phoneNumber, username,uid)) {
                status = true;
                mess = "Thành công";
                User user = userDAO.login(email, password);
                if (user != null) {
                    result.add(user);
                }
            } else {
                status = false;
                mess = "Đã có lỗi xảy ra";
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
