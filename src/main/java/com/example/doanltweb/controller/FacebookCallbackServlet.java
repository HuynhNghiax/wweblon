package com.example.doanltweb.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.doanltweb.dao.UserDao;
import com.example.doanltweb.dao.model.FacebookUser;
import com.example.doanltweb.dao.model.User;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

@WebServlet(name = "facebook-callback", value = "/facebook-callback")
public class FacebookCallbackServlet extends HttpServlet {
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String REDIRECT_URI = "http://localhost:8080/DoAnLTWeb/facebook-callback";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendRedirect("login.jsp?error=Facebook login failed");
            return;
        }

        // Lấy access token từ Facebook
        String tokenUrl = "https://graph.facebook.com/v18.0/oauth/access_token?"
                + "client_id=" + CLIENT_ID
                + "&redirect_uri=" + REDIRECT_URI
                + "&client_secret=" + CLIENT_SECRET
                + "&code=" + code;

        String accessToken = getAccessToken(tokenUrl);

        // Lấy thông tin người dùng
        String userInfo = getUserInfo(accessToken);

        // Chuyển đổi JSON response sang Java Object
        FacebookUser fbUser = parseFacebookUser(userInfo);

        // Xử lý đăng nhập hoặc đăng ký tài khoản mới
        UserDao userDAO = new UserDao();
        User user = userDAO.findByEmail(fbUser.getEmail());

        if (user == null) {
            // Chưa có tài khoản, tạo mới
            user = new User();
            user.setUsername(fbUser.getName());
            user.setEmail(fbUser.getEmail());
            user.setIsVerified(1);
            userDAO.insertUser(user.getUsername(),user.getUsername(),user.getEmail());
        }

        // Lưu thông tin vào session
        HttpSession session = request.getSession();
        session.setAttribute("auth", user);

        // Chuyển hướng đến trang chính
        response.sendRedirect("Userprofile.jsp");
    }

    private String getAccessToken(String tokenUrl) throws IOException {
        URL url = new URL(tokenUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());
        return json.getString("access_token");
    }

    private String getUserInfo(String accessToken) throws IOException {
        String url = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;
        return sendGetRequest(url);
    }

    private String sendGetRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private FacebookUser parseFacebookUser(String json) {
        JSONObject obj = new JSONObject(json);
        return new FacebookUser(obj.getString("id"), obj.getString("name"), obj.optString("email"));
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}