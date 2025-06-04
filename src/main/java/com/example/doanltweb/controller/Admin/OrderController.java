package com.example.doanltweb.controller.Admin;

import com.example.doanltweb.dao.OrderDao;
import com.example.doanltweb.dao.ProductDao;
import com.example.doanltweb.dao.model.Order;
import com.example.doanltweb.dao.model.OrderDetail;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.User;
import com.example.doanltweb.service.ProductService;
import com.example.doanltweb.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@MultipartConfig
public class OrderController extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	OrderDao orderDao = new OrderDao();
    	int page = 1;
    	int limit = 5;
    	if (request.getParameter("page") != null) {
    	    page = Integer.parseInt(request.getParameter("page"));   
    	}
    	if (request.getParameter("limit") != null) {
    	    limit = Integer.parseInt(request.getParameter("limit"));
    	}

    	List<Order> orders = orderDao.getOrdersWithPagination((page - 1) * limit, limit);
    	Map<Order, List<OrderDetail>> orderMap = new HashMap<Order, List<OrderDetail>>();
    	for (Order order : orders) {
			orderMap.put(order, orderDao.getDetailById(order.getId()));
		}

    	int totalOrders = orderDao.getAllOrder().size();
    	int totalPages = (int) Math.ceil(totalOrders * 1.0 / limit);

    	request.setAttribute("orders", orders);
    	request.setAttribute("orderMap", orderMap);
    	request.setAttribute("currentPage", page);
    	request.setAttribute("totalPages", totalPages);
    	request.setAttribute("limit", limit);
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tham số từ request
    	response.setContentType("application/json");
    	OrderDao orderDao = new OrderDao();
        String orderIdStr = request.getParameter("orderId");
        String orderStatus = request.getParameter("newStatus");
        System.out.println("Received orderId: " + orderIdStr); // Kiểm tra giá trị orderIdStr
	    PrintWriter out = response.getWriter();
	    JsonObject jsonResponse = new JsonObject();
	    Gson gson = new Gson();

        if (orderIdStr != null && !orderIdStr.isEmpty()) {
            try {
                // Chuyển đổi từ String sang Integer
                int orderId = Integer.parseInt(orderIdStr);

                // Giả sử bạn có phương thức hủy đơn hàng
                boolean success = orderDao.updateStatus(orderId, orderStatus);

                if (success) {
                    jsonResponse.addProperty("status", "success");
                    jsonResponse.addProperty("message", "Xử lý thành công!");
                    jsonResponse.addProperty("orderId", orderId);
                    jsonResponse.addProperty("newStatus", orderStatus);

                } else {
                    jsonResponse.addProperty("status", "error");
                    jsonResponse.addProperty("message", "Đã xảy ra lỗi! ");
                }


            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"status\": \"error\", \"message\": \"ID đơn hàng không hợp lệ.\"}");
            }
            // Chuyển đổi phản hồi thành JSON và trả về client
		    String jsonString = gson.toJson(jsonResponse);
		    System.out.println("JSON Response: " + jsonString);
		    out.print(jsonString);
		    out.flush();
        } 
    }

}




