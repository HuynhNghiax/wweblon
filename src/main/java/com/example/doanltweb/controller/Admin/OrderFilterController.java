package com.example.doanltweb.controller.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.example.doanltweb.dao.OrderDao;
import com.example.doanltweb.dao.model.Order;
import com.example.doanltweb.dao.model.OrderDetail;

/**
 * Servlet implementation class OrderFilterController
 */
@WebServlet("/OrderFilterController")
public class OrderFilterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public OrderFilterController() {
        super();
        // TODO Auto-generated constructor stub
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	OrderDao orderDao = new OrderDao();
        String status = request.getParameter("status");
        String fromDateStr = request.getParameter("fromDate");
        String toDateStr = request.getParameter("toDate");
        String paymentMethodStr = request.getParameter("paymentMethod");

 
        // Parse phương thức thanh toán
        Integer paymentMethod = null;
        if (paymentMethodStr != null && !paymentMethodStr.isEmpty()) {
            paymentMethod = Integer.parseInt(paymentMethodStr);
        }

        // Gọi service để lấy danh sách đã lọc
        Map<Order,List<OrderDetail>> orderMap = orderDao.getFilteredOrders(status, fromDateStr, toDateStr, paymentMethod);

        // Truyền dữ liệu lại JSP
        request.setAttribute("orderMap", orderMap);
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
