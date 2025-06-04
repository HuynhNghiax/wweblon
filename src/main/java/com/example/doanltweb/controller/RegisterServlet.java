package com.example.doanltweb.controller;

import java.io.*;
import java.util.Random;

import com.example.doanltweb.dao.OTPDAO;
import com.example.doanltweb.dao.UserDao;
import com.example.doanltweb.service.EmailService;
import com.example.doanltweb.service.UserService;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "RegisterServlet", value = "/register")
public class RegisterServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDao userDAO = new UserDao();
        OTPDAO otpDAO = new OTPDAO();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        if (userDAO.isUserExists(email)) {
            response.getWriter().write("Email already registered.");
            return;
        }

        userDAO.addUser(username, password, email, fullname, phone, address);
        int userId = userDAO.getUserIdByEmail(email); // Lấy user_id mới tạo

        if (userId == -1) {
            response.getWriter().write("User registration failed.");
            return;
        }

        String otp = String.format("%06d", new Random().nextInt(999999));
        otpDAO.saveOTP(userId, otp);
        EmailService.sendOTP(email, otp);

        response.sendRedirect("verify-otp.jsp?user_id=" + userId);
    }
}