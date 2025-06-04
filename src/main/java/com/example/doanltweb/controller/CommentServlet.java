package com.example.doanltweb.controller;

import java.io.*;

import com.example.doanltweb.dao.CommentDao;
import com.example.doanltweb.dao.model.Comment;
import com.example.doanltweb.dao.model.User;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "CommentServlet", value = "/Comment")
public class CommentServlet extends HttpServlet {
    private CommentDao commentDao;

    @Override
    public void init() throws ServletException {
        commentDao = new CommentDao(); // Khởi tạo DAO dùng JDBI
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("auth");
        try {
            String content = request.getParameter("content");
            int star = Integer.parseInt(request.getParameter("star"));
            int idProduct = Integer.parseInt(request.getParameter("idProduct"));
            int idUser = Integer.parseInt(request.getParameter("idUser"));

            // Kiểm tra dữ liệu
            if (content == null || content.trim().isEmpty() || content.length() > 255) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nội dung không hợp lệ.");
                return;
            }

            Comment comment = new Comment();
            comment.setContent(content);
            comment.setStar(star);
            comment.setIdUser(idUser);
            comment.setIdProduct(idProduct);

            commentDao.insertComment(comment);

            // Chuyển hướng về Servlet hiển thị chi tiết sản phẩm
            response.sendRedirect("chitietsanpham?id=" + idProduct);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Lỗi khi gửi đánh giá.");
        }
    }
}
