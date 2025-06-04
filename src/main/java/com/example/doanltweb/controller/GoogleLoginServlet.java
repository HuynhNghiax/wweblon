package com.example.doanltweb.controller;import java.io.*;

import com.example.doanltweb.dao.UserDao;
import com.example.doanltweb.dao.model.GoogleUser;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "login-google", value = "/login-google")
public class GoogleLoginServlet extends HttpServlet {
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String REDIRECT_URI = "http://localhost:8080/DoAnLTWeb/callback";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&response_type=code"
                + "&scope=email profile";

        response.sendRedirect(googleAuthUrl);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}