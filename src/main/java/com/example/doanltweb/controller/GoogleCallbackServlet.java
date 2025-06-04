package com.example.doanltweb.controller;

import com.example.doanltweb.dao.UserDao;

import com.example.doanltweb.dao.model.GoogleUser;
import com.example.doanltweb.dao.model.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.api.client.http.javanet.NetHttpTransport;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;

@WebServlet(name = "callback", value = "/callback")
public class GoogleCallbackServlet extends HttpServlet {
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String REDIRECT_URI = "http://localhost:8080/DoAnLTWeb/callback";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String code = request.getParameter("code");
        if (code == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Exchange authorization code for access token
        String accessToken = getAccessToken(code);
        GoogleUser googleUser = getUserInfo(accessToken);

        if (googleUser != null) {
            UserDao userDAO = new UserDao();
            User existingUser = userDAO.getUserByEmail(googleUser.getEmail());

            if (existingUser == null) {
                // Chưa có user, tạo mới
                existingUser = new User();
                existingUser.setUsername(googleUser.getName());
                existingUser.setFullname(googleUser.getName());
                existingUser.setEmail(googleUser.getEmail());
                existingUser.setIsVerified(1);

                userDAO.insertUser(existingUser.getUsername(), existingUser.getFullname(), existingUser.getEmail());
            }

            // Lưu session
            HttpSession session = request.getSession();
            session.setAttribute("auth", existingUser);
            response.sendRedirect("Userprofile.jsp");
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    private String getAccessToken(String code) throws IOException {
        String urlParameters = "code=" + code
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + REDIRECT_URI
                + "&grant_type=authorization_code";

        URL url = new URL("https://oauth2.googleapis.com/token");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.getOutputStream().write(urlParameters.getBytes());

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject json = new JSONObject(response.toString());
        return json.getString("access_token");
    }

    private GoogleUser getUserInfo(String accessToken) throws IOException {
        URL url = new URL("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject json = new JSONObject(response.toString());
        GoogleUser user = new GoogleUser();
        user.setId(json.getString("id"));
        user.setEmail(json.getString("email"));
        user.setName(json.getString("name"));
        return user;
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}