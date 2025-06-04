package com.example.doanltweb.controller;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

@WebServlet(name = "CapchaServlet", value = "/CapchaServlet")
public class CapchaServlet extends HttpServlet {
    private static final String SECRET_KEY = "6LcIsA4rAAAAAJcp-uZKc7SYaGkX9rV9ljZcn_tk";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        boolean verified = verifyRecaptcha(gRecaptchaResponse);

        if (!verified) {
            response.getWriter().println("Xác minh reCAPTCHA thất bại. Vui lòng thử lại.");
            return;
        }

        // Tiếp tục xử lý đăng nhập...
        response.getWriter().println("Xác minh thành công! Đăng nhập thành công.");
    }

    private boolean verifyRecaptcha(String gRecaptchaResponse) throws IOException {
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "secret=" + URLEncoder.encode(SECRET_KEY, "UTF-8") +
                "&response=" + URLEncoder.encode(gRecaptchaResponse, "UTF-8");

        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
        out.write(params);
        out.flush();
        out.close();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine, responseStr = "";
        while ((inputLine = in.readLine()) != null) {
            responseStr += inputLine;
        }
        in.close();

        JSONObject json = new JSONObject(responseStr);
        return json.getBoolean("success");
    }
}