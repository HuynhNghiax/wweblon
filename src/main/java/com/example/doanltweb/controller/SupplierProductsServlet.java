//package com.example.doanltweb.controller;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.example.doanltweb.dao.SupplierDao;
//import com.example.doanltweb.dao.model.Product;
//import com.example.doanltweb.dao.model.Supplier;
//import com.example.doanltweb.service.ProductService;
//import com.example.doanltweb.service.SupplierService;
//import jakarta.servlet.http.*;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.*;
//
//@WebServlet(name = "SupplierProductsServlet", value = "/maybomtheohang")
//public class SupplierProductsServlet extends HttpServlet {
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String idSupplier = request.getParameter("id_supplier");
//        ProductService productService = new ProductService();
//        List<Product> products = productService.getProductBySupplier(Integer.parseInt(idSupplier));
//        SupplierDao supplierDao = new SupplierDao();
//        List<Supplier> suppliers = supplierDao.getAllSuppliers();
//        request.setAttribute("suppliers", suppliers);
//        request.setAttribute("products", products);
//        request.getRequestDispatcher("/maybomtheohang.jsp").forward(request, response);
//    }
//
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//
//    }
//}