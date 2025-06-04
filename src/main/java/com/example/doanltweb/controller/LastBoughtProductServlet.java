package com.example.doanltweb.controller;

import com.google.gson.Gson;
import com.example.doanltweb.dao.OrderDao;
import com.example.doanltweb.dao.model.Order;
import com.example.doanltweb.dao.model.OrderDetail;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/LastBoughtProductServlet")
public class LastBoughtProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDao orderDao = new OrderDao(); 
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Không tạo session mới
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        User user = (User) session.getAttribute("auth");
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            return;
        }
        try {
            int userId = user.getId();
            System.out.println("User ID: " + userId); // debug
            Map<Order, List<OrderDetail>> map = orderDao.getOrderWithDetails(userId);
            List<Product> products = new ArrayList<>();

            for (List<OrderDetail> details : map.values()) {
                for (OrderDetail detail : details) {
                	products.add(detail.getProduct()); 
                }
            }

            // Lấy 4 sản phẩm cuối cùng
            int size = products.size();
            products = products.subList(Math.max(size - 4, 0), size);
            System.out.println("Sản phẩm: " + products.size());

            Gson gson = new Gson();
            String json = gson.toJson(products);
            System.out.println("JSON: " + json);

            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
