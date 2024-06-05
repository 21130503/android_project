package Controller;

import DAO.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/register")
public class RegisterController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phoneNumber = req.getParameter("phoneNumber");
        String username = req.getParameter("username");
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();
        UserDAO userDAO = new UserDAO();
        System.out.println(email);
        String status;
        String mess;
        if(userDAO.register(email,password, phoneNumber,username)){
           status = "Success";
           mess = "Thành công";
        }
        else{
            status = "Error";
            mess = "Đã có lỗi xảy ra";
        }
        jsonResponse.addProperty("status", status);
        jsonResponse.addProperty("message", mess);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(jsonResponse));
    }
}
