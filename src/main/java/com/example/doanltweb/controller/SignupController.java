package com.example.doanltweb.controller;

import java.io.*;

import com.example.doanltweb.dao.UserDao;
import com.example.doanltweb.dao.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "SignupController", value = "/SignupController")
public class SignupController extends HttpServlet {
    private UserDao userDao = new UserDao();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Mặc định quyền là User
        int idPermission = 2;

        try {
            userDao.insert(username, password, fullname, email, phone, address, idPermission);
            response.sendRedirect("login.jsp"); // Redirect to login page if successful
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Failed to create user: " + e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("dangki.jsp");
            dispatcher.forward(request, response); // Redirect back to register page with error
        }
    }
}