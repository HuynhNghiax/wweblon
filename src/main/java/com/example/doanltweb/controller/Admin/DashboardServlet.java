package com.example.doanltweb.controller.Admin;

import java.io.*;
import java.util.List;

import com.example.doanltweb.dao.OrderDao;
import com.example.doanltweb.dao.model.Order;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "dashboard-data", value = "/dashboard-data")
public class DashboardServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        OrderDao orderDAO = new OrderDao();

        Integer totalPumps = orderDAO.getTotalPumps();
        Double totalSales = orderDAO.getTotalSales();
        List<Order> orders = orderDAO.getRecentOrders();

        // Sử dụng thư viện Gson để trả JSON
        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("totalPumps", totalPumps != null ? totalPumps : 0);
        jsonResponse.addProperty("totalSales", totalSales != null ? totalSales : 0.0);
        JsonElement ordersJson = gson.toJsonTree(orders);
        jsonResponse.add("orders", ordersJson);

        response.getWriter().write(gson.toJson(jsonResponse));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}