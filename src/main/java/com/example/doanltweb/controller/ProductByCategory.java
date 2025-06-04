package com.example.doanltweb.controller;

import com.example.doanltweb.dao.model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.example.doanltweb.dao.OrderDao;
import com.example.doanltweb.dao.ProductDao;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Servlet implementation class CancelOrderServlet
 */
@MultipartConfig
@WebServlet("/ProductByCategory")
public class ProductByCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String categoryId = request.getParameter("categoryId");
		System.out.println(categoryId);
		if(categoryId!=null) {
			ProductDao productDao = new ProductDao();
			List<Product> products = productDao.getProductByCategory(Integer.parseInt(categoryId));
			System.out.println(products);
			request.setAttribute("products", products);
			request.getRequestDispatcher("cacsanpham.jsp").forward(request, response);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
