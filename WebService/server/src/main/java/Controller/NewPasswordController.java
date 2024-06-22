package Controller;

import DAO.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

@WebServlet(value = "/newPassword")
public class NewPasswordController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        System.out.println(email);
        System.out.println(password);
        System.out.println("doPUT");
        UserDAO userDAO = new UserDAO();

        JsonObject jsonResponse = new JsonObject();
        Gson gson = new Gson();

        boolean status;
        String mess;

        try {
            if (userDAO.updatePassword(email, password)) {
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
