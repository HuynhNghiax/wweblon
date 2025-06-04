package com.example.doanltweb.controller;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "login-facebook", value = "/login-facebook")
public class FacebookLoginServlet extends HttpServlet {
    private static final String CLIENT_ID = "1451949449515239";
    private static final String REDIRECT_URI = "http://localhost:8080/DoAnLTWeb/facebook-callback";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String fbAuthUrl = "https://www.facebook.com/v18.0/dialog/oauth?"
                + "client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&scope=email,public_profile"
                + "&response_type=code";

        response.sendRedirect(fbAuthUrl);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}