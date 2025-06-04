package com.example.doanltweb.filter;

import com.example.doanltweb.dao.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/Userprofile.jsp")

public class FilterProfile implements Filter {
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
            chain.doFilter(request, response);
            // Kiểm tra xem role của user có phải là 1 không

        } else {
            // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        // Clean up nếu cần
    }
}

