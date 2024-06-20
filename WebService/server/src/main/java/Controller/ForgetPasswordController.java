package Controller;

import DAO.UserDAO;
import Model.User;
import Services.SendEmail;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@WebServlet(value = "/forgetPassword")
public class ForgetPasswordController extends HttpServlet {
    public String email;
    public Map<String, Integer> otpMap;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        email = req.getParameter("email");
        SendEmail sendEmail = new SendEmail();
        UserDAO userDAO = new UserDAO();
        JsonObject jsonResponse = new JsonObject();
        Gson gson = new Gson();
        HttpSession session = req.getSession();
        int otpvalue = 0;
        // sending otp
        boolean status;
        String mess;

        try {
            if (userDAO.checkEmailExist(email)) {
                status = true;
                mess = "Thành công";
                Random rand = new Random();
                otpvalue = rand.nextInt(1255650);
                String toEmail = email;
                sendEmail.sendEmail("nguyenthanhquyen5577@gmail.com", "azal wquf vhly btmr", toEmail, "Hello", "Your OTP is " + otpvalue);

            } else {
                status = false;
                mess = "Email không tồn tại";
            }
        } catch (Exception e) {
            status = false;
            mess = "Đã có lỗi xảy ra: " + e.getMessage();
            e.printStackTrace(); // Log the error for debugging

        }

        otpMap.put(email, otpvalue);
        jsonResponse.addProperty("success", status);
        jsonResponse.addProperty("message", mess);

        session.setAttribute("OTP", otpvalue);
        System.out.println(otpvalue);
        session.setAttribute("EMAIL", email);

        System.out.println("Session ID: " + session.getId());
        System.out.println("Generated OTP: " + otpvalue);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(gson.toJson(jsonResponse));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println("dopost");
            String value = req.getParameter("otp");
            int otp = otpMap.get(email);
            System.out.println("OTP in session: " + otp);
            System.out.println(otp);
            JsonObject jsonResponse = new JsonObject();
            Gson gson = new Gson();

            boolean status;
            String mess;

            try {
                int intValue = Integer.parseInt(value);
                if (otp == intValue) {
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        System.out.println(email);
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
