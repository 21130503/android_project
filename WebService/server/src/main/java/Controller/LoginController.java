package Controller;

import DAO.UserDAO;
import Model.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(value = "/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String email = req.getParameter("email");
       String password = req.getParameter("password");
        UserDAO userDAO = new UserDAO();
        Gson gson = new Gson();
        User user  = userDAO.login(email,password);
        String userJson = gson.toJson(user);
        // Thiết lập loại nội dung và trả về JSON
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(userJson);
    }
}
