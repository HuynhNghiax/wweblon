package com.example.doanltweb.controller;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import com.example.doanltweb.dao.OrderDao;
import com.example.doanltweb.dao.model.User;
import com.example.doanltweb.utils.Config;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "VnpayReturn", value = "/VnpayReturn")
public class VnpayReturn extends HttpServlet {
    OrderDao orderdao = new OrderDao();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
                String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            String vnp_SecureHash = request.getParameter("vnp_SecureHash");
            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }
            String signValue = Config.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {
                String paymentCode = request.getParameter("vnp_TransactionNo");

                String orderId = request.getParameter("vnp_TxnRef");

                // Get userId
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("auth");
                if (user == null) {
                    response.sendRedirect("login.jsp"); // hoặc trang báo lỗi
                    return;
                }
                int userId = user.getId();

                boolean transSuccess = false;
                if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                    //update banking system
                    orderdao.updateStatus(Integer.parseInt(orderId), "VERIFIED");
                    transSuccess = true;
                } else {
                    orderdao.updateStatus(Integer.parseInt(orderId), "FAILED");
                }
                request.setAttribute("transResult", transSuccess);
                request.getRequestDispatcher("vnpay_result.jsp").forward(request, response);
            } else {
                //RETURN PAGE ERROR
                System.out.println("GD KO HOP LE (invalid signature)");
            }
        }
    }
}