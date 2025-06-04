package com.example.doanltweb.controller;

import com.example.doanltweb.dao.CommentDao;
import com.example.doanltweb.dao.ProductDao;
import com.example.doanltweb.dao.model.Comment;
import com.example.doanltweb.dao.model.Product;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductDetailServlet", value = "/chitietsanpham")
public class ProductDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if ((id == null || id.isEmpty()) && request.getSession().getAttribute("currentProductId") != null) {
            id = (String) request.getSession().getAttribute("currentProductId");
            request.getSession().removeAttribute("currentProductId");
        }

        if (id == null || id.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID sản phẩm không hợp lệ.");
            return;
        }

        ProductDao productDao = new ProductDao();
        Product product = productDao.getById(Integer.parseInt(id));
        CommentDao commentDao = new CommentDao();
        List<Comment> comments = commentDao.getCommentsByProductId(product.getId());

        request.setAttribute("product", product);
        request.setAttribute("comments", comments);

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object auth = session.getAttribute("auth");
            System.out.println("Session auth: " + auth);
        }
        System.out.println("ID sản phẩm nhận được: " + id);

        request.getRequestDispatcher("chitietsanpham.jsp").forward(request, response);
    }
}
