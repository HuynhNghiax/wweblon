package com.example.doanltweb.controller;

import java.io.*;
import java.util.List;

import com.example.doanltweb.dao.ProductDao;
import com.example.doanltweb.dao.model.Product;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "Search", value = "/Search")
public class SearchServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String search = request.getParameter("search");

        if (search == null) {
            search = "";
        }
        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.searchProducts(search);

        request.setAttribute("products", products);
        request.setAttribute("searchKeyword", search);

        request.getRequestDispatcher("/searchResult.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}