package com.example.doanltweb.filter;

import com.example.doanltweb.dao.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebFilter("/admin.jsp")
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Khởi tạo nếu cần
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession();
        // Kiểm tra xem người dùng có đăng nhập hay không
        Object user = session.getAttribute("auth");

        if (user != null) {
            // Giả sử đối tượng User có thuộc tính role
            User authUser = (User) user;
            int role = authUser.getIdPermission();
            // Kiểm tra xem role của user có phải là 1 không
            if (role == 1 || role == 3 || role == 4) {
                // Nếu role là 1, cho phép truy cập vào admin.jsp
                chain.doFilter(request, response);
            } else {
                // Nếu không phải role 1, chuyển hướng đến trang khác (ví dụ: trang chủ)
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/404NotFound.jsp");
            }
        } else {
            // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/404NotFound.jsp");
        }
    }

    @Override
    public void destroy() {
        // Clean up nếu cần
    }
}
