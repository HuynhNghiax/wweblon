package com.example.doanltweb.controller;

import com.example.doanltweb.dao.OrderDao;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@MultipartConfig
@WebServlet("/VerifyOrderServlet")
public class VerifyOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    JsonObject jsonResponse = new JsonObject();
	    try {
	        OrderDao orderDao = new OrderDao();
	        String orderIdStr = request.getParameter("orderId");
	        String otp = request.getParameter("otp").trim();
	        System.out.println(orderIdStr);
	        System.out.println(otp);
	        if (orderIdStr != null && !orderIdStr.isEmpty()) {
	            int orderId = Integer.parseInt(orderIdStr);
	            boolean success = orderDao.verifyOrder(orderId, otp);
	            jsonResponse.addProperty("success", success);
	            jsonResponse.addProperty("message", success ? "Thành công!" : "Thất bại");
	        } else {
	            jsonResponse.addProperty("success", false);
	            jsonResponse.addProperty("message", "Thiếu orderId hoặc otp.");
	        }
	    } catch (Exception e) {
	        jsonResponse.addProperty("success", false);
	        jsonResponse.addProperty("message", "Lỗi xử lý: " + e.getMessage());
	    }
	    PrintWriter out = response.getWriter();
	    out.print(jsonResponse.toString());
	    out.flush();
	}

}
