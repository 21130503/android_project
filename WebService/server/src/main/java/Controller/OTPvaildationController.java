package Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/validationOTP")
public class OTPvaildationController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int value = Integer.parseInt(req.getParameter("otp"));
        HttpSession session = req.getSession();
        int otp = (int) session.getAttribute("otp");
        System.out.println("aaa"); 
        JsonObject jsonResponse = new JsonObject();
        Gson gson = new Gson();

        boolean status;
        String mess;

        try {
            if (value == otp) {
                status = true;
                mess = "Thành công";
            } else {
                status = false;
                mess = "OTP không chính xác";
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
