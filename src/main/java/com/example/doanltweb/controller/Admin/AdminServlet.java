package com.example.doanltweb.controller.Admin;

import java.io.*;
import java.util.List;

import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.User;
import com.example.doanltweb.service.ProductService;
import com.example.doanltweb.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "AdminServlet", value = "/admin")
public class AdminServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        quản lý user
        UserService userService = new UserService();
        List<User> users = userService.getUserList();
        request.setAttribute("users", users);
        // quản lý báo cáo


        request.getRequestDispatcher("admin.jsp").forward(request, response);




    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        UserService userService = new UserService();
        userService.deleteUserById(userId);
        response.sendRedirect(request.getContextPath() + "/admin");
    }
}