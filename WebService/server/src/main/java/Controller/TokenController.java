package Controller;

import DAO.TypeProductDAO;
import DAO.UserDAO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/update-token")
public class TokenController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idUser = req.getParameter("idUser");
        String token = req.getParameter("token");
        UserDAO dao = new UserDAO();
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();
        System.out.println("idUser"+idUser);
        System.out.println("token"+token);
        if(dao.updateToken(idUser,token)){
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
