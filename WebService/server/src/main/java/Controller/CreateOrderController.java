package Controller;

import DAO.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/create-order")
public class CreateOrderController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idUser = req.getParameter("idUser");
        String totalPrice = req.getParameter("totalPrice");
        String address = req.getParameter("address");
        String cart = req.getParameter("carts");
        OrderDAO orderDAO = new OrderDAO();
        System.out.println("start");
        orderDAO.createOrder(idUser,address, totalPrice, cart);
        System.out.println("end");
    }
}
