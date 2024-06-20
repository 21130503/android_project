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
@WebServlet(value = "/OTPValidation")
public class OTPValidationController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("dopost");
        String value = req.getParameter("otp");
        HttpSession session = req.getSession();
        Integer otp = (Integer) session.getAttribute("otp");
        System.out.println("Session ID: " + session.getId());
        System.out.println("OTP in session: " + otp);
        System.out.println(otp);
        JsonObject jsonResponse = new JsonObject();
        Gson gson = new Gson();

        boolean status;
        String mess;

        try {
            int intValue = Integer.parseInt(value);
            if (otp == intValue) {
                System.out.println("Success" );
                status = true;
                mess = "Thành công";
            } else {
                System.out.println("Error" );
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
