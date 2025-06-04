package com.example.doanltweb.controller;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;

import com.example.doanltweb.dao.VoucherDao;
import com.example.doanltweb.dao.VoucherUsageDao;
import com.example.doanltweb.dao.model.User;
import com.example.doanltweb.dao.model.Voucher;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ApplyVoucherServlet", value = "/ApplyVoucherServlet")
public class ApplyVoucherServlet extends HttpServlet {
    private final VoucherDao voucherDao = new VoucherDao();
    private final VoucherUsageDao usageDao = new VoucherUsageDao();

    private String formatDiscountDisplay(double discountValue) {
        if (discountValue <= 100) {
            return String.format("%.0f%%", discountValue);  // Ví dụ: 15%
        } else if (discountValue >= 1000) {
            return String.format("%s VND", String.format("%,.0f", discountValue)); // Ví dụ: 10,000 VND
        } else {
            return "Không hợp lệ";
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // Có thể bỏ trống hoặc redirect nếu không dùng GET
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String code = request.getParameter("voucherCode");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("auth");
        if (user == null) {
            request.setAttribute("voucherError", "Vui lòng đăng nhập để sử dụng mã giảm giá.");
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
            return;
        }
        int userId = user.getId();

        Double totalPrice = (Double) session.getAttribute("TotalPrice");
        if (totalPrice == null) totalPrice = 0.0;

        Voucher voucher = voucherDao.getVoucherByCode(code);

        if (voucher == null) {
            request.setAttribute("voucherError", "Mã giảm giá không tồn tại.");
        } else if (voucher.getStatus() == 0 || voucher.getEndDate().isBefore(LocalDate.now())) {
            request.setAttribute("voucherError", "Voucher đã hết hạn hoặc không còn hoạt động.");
        } else {
            int userUsageCount = usageDao.countUsageByUser(userId, voucher.getId());

            if (!voucher.isValidForUser(userId, totalPrice, userUsageCount)) {
                request.setAttribute("voucherError", "Mã không hợp lệ hoặc không đủ điều kiện.");
            } else {
                double discount = voucher.calculateDiscount(totalPrice);
                double newTotal = totalPrice - discount;

                session.setAttribute("TotalPrice", newTotal);
                session.setAttribute("VoucherApplied", voucher);

                String discountDisplay = formatDiscountDisplay(discount);
                request.setAttribute("voucherSuccess", "Áp dụng mã thành công! Giảm " + discountDisplay + ".");
            }
        }

        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
}

