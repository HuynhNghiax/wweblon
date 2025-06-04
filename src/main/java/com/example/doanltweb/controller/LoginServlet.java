package com.example.doanltweb.controller;import java.io.*;
import java.util.List;
import java.util.Optional;

import com.example.doanltweb.dao.LogDao;
import com.example.doanltweb.utils.CartUtils;
import com.example.doanltweb.dao.CartDao;
//import com.example.doanltweb.dao.LogDao;
import com.example.doanltweb.dao.UserDao;
import com.example.doanltweb.dao.model.User;
import com.example.doanltweb.dao.model.Cart;
import com.example.doanltweb.dao.model.CartItem;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

@WebServlet(name = "LoginServlet", value = "/LoginController")
public class LoginServlet extends HttpServlet {
    private UserDao userDao = new UserDao();
    private static final Logger logger = LogManager.getLogger(LoginServlet.class);
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        String ip = request.getRemoteAddr();
        Integer count = (Integer) session.getAttribute("loginFail"); // Lấy số lần đăng nhập thất bại
        if (count == null) {
            count = 0; // Nếu chưa có, gán mặc định là 0
        }	
        
        // Khởi tạo UserDao để kiểm tra đăng nhập
        UserDao userDao = new UserDao();
        User user = userDao.login(username, password);  // Phương thức checkUser đã kiểm tra mật khẩu mã hóa

        // Kiểm tra kết quả đăng nhập
        if (user != null) {
            if (user.getIsVerified() == 0) {
                request.setAttribute("error", "Tài khoản của bạn đã bị vô hiệu hóa. Vui lòng liên hệ quản trị viên.");
                request.setAttribute("username", username);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            session.setAttribute("auth", user); // Lưu thông tin người dùng vào session
            ThreadContext.put("user_id", String.valueOf(user.getId()));
            ThreadContext.put("ip", ip);
            ThreadContext.put("resource", "LOGIN");
            ThreadContext.put("data_in", "username=" + username);
            ThreadContext.put("data_out", "SUCCESS");

            logger.info("User login successful");

            // Nếu người dùng là Admin (role == 1)
            int role = user.getIdPermission();

            if (role == 1 || role == 3 || role == 4 ) {
                System.out.println("lỗi");

                response.sendRedirect("admin");
                return;
            } else {

            	CartUtils.mergeSessionCartToDb(user.getId(),session);
                response.sendRedirect("/DoAnLTWeb/index.jsp");
                return;
            }

        } else {

        	count++; // Tăng số lần thất bại
            session.setAttribute("loginFail", count); // Lưu lại số lần thất bại vào session
//        	LogDao.saveLog(0, "WARN", ip, "LOGIN", "username=" + username,"Login fail: " + count +"times");
            ThreadContext.put("user_id", "0");
            ThreadContext.put("ip", ip);
            ThreadContext.put("resource", "LOGIN");
            ThreadContext.put("data_in", "username=" + username);
            ThreadContext.put("data_out", "FAIL #" + count);
            logger.warn("User login failed");

            request.setAttribute("error", "Đăng nhập không thành công. Vui lòng kiểm tra lại thông tin.");
            request.setAttribute("username", username);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            if (userDao.checkLockUserByUsername(username) == true ) {
                request.setAttribute("error", "tài khoản của bạn đã bị khóa vui lòng quên mật khẩu để mở khóa.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            if (count <= 3) {
                count++;// Tăng số lần thất bại
                session.setAttribute("loginFail", count); // Lưu lại số lần thất bại vào session
                request.setAttribute("error", "Đăng nhập không thành công. Vui lòng kiểm tra lại thông tin.");
                request.setAttribute("username", username);
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
            userDao.lockUserByUsername(username);
            request.setAttribute("error", "tài khoản của bạn đã bị khóa vui lòng quên mật khẩu để mở khóa.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }



}