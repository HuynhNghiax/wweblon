package com.example.doanltweb.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.doanltweb.dao.model.User;
import com.example.doanltweb.utils.CartUtils;
import jakarta.servlet.http.HttpSession;


@WebServlet(name = "LogoutServlet", value = "/LogoutController")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xóa session
        HttpSession session = (HttpSession) request.getSession();
        User user = (User) session.getAttribute("auth");
        CartUtils.mergeSessionCartToDb(user.getId(),session);
        session.invalidate();

        // Điều hướng đến trang login
        response.sendRedirect("login.jsp");
    }
}
