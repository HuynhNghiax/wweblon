//package com.example.doanltweb.controller.Admin;
//
//import java.io.*;
//import java.util.List;
//
//import com.example.doanltweb.dao.model.Product;
//import com.example.doanltweb.service.ProductService;
//import jakarta.servlet.http.*;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.*;
//
//@WebServlet(name = "ProductManager", value = "/productmanager")
//public class ProductManager extends HttpServlet {
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        ProductService productService = new ProductService();
//        List<Product> products = productService.getAll();
//        request.setAttribute("products", products);
//        request.getRequestDispatcher("admin.jsp").forward(request, response);
//    }
//
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//
//    }
//}