package com.example.doanltweb.controller;

import java.io.*;
import java.util.List;

import com.example.doanltweb.dao.ProductDao;
import com.example.doanltweb.dao.model.Product;
import com.google.gson.Gson;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "SearchSuggestions", value = "/SearchSuggestions")
public class SuggestSearchServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String query = request.getParameter("query");
        ProductDao productDao = new ProductDao();
        List<String> suggestions = productDao.searchProductsstr(query);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(suggestions));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}