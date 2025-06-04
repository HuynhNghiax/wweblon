package com.example.doanltweb.controller;

import java.io.*;

import com.example.doanltweb.dao.OTPDAO;
import com.example.doanltweb.dao.UserDao;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "verify-otp", value = "/verify-otp")
public class VerifyOTPServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UserDao userDAO = new UserDao();
        OTPDAO otpDAO = new OTPDAO();
        String userIdParam = request.getParameter("user_id");

        String message;
        if (userIdParam == null || userIdParam.isEmpty()) {
            message = "Lỗi: Thiếu user_id!";
        } else {
            int userId = Integer.parseInt(userIdParam);
            String enteredOTP = request.getParameter("otp");

            if (enteredOTP == null || enteredOTP.isEmpty()) {
                message = "Lỗi: Bạn chưa nhập OTP!";
            } else {
                String correctOTP = otpDAO.getOTPByUserId(userId);
                if (correctOTP != null && correctOTP.equals(enteredOTP)) {
                    userDAO.updateUserVerifiedById(userId);
                    message = "Xác minh thành công! Tài khoản của bạn đã được kích hoạt.";
                } else {
                    message = "OTP không hợp lệ. Vui lòng thử lại.";
                }
            }
        }

        // Truyền message và userId lại về JSP
        request.setAttribute("message", message);
        request.setAttribute("userId", userIdParam);
        request.getRequestDispatcher("verify-otp.jsp").forward(request, response);
    }
}