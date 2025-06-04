/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

 package com.example.doanltweb.controller;


 import java.io.IOException;
 import com.example.doanltweb.dao.DAOTokenForget;
 import com.example.doanltweb.dao.UserDao;
 import com.example.doanltweb.dao.model.TokenForgetPassword;
 import com.example.doanltweb.dao.model.User;
 import jakarta.servlet.ServletException;
 import jakarta.servlet.annotation.WebServlet;
 import jakarta.servlet.http.HttpServlet;
 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;
 import jakarta.servlet.http.HttpSession;
 import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger;
 import org.apache.logging.log4j.ThreadContext;

@WebServlet(name="resetPassword", urlPatterns={"/resetPassword"})
 public class resetPassword extends HttpServlet {
     DAOTokenForget DAOToken = new DAOTokenForget();
     UserDao DAOUser = new UserDao();
    private static final Logger logger = LogManager.getLogger(resetPassword.class);
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
         String token = request.getParameter("token");
         HttpSession session = request.getSession();
         if(token != null) {
             TokenForgetPassword tokenForgetPassword = DAOToken.getTokenPassword(token);
             resetService service = new resetService();
             if(tokenForgetPassword == null) {
                 request.setAttribute("mess", "token invalid");
                 request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
                 return;
             }
             if(tokenForgetPassword.isIsUsed()) {
                 request.setAttribute("mess", "token is used");
                 request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
                 return;
             }
             if(service.isExpireTime(tokenForgetPassword.getExpiryTime())) {
                 request.setAttribute("mess", "token is expiry time");
                 request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
                 return;
             }
             User user = DAOUser.getUserById(tokenForgetPassword.getUserId());
             request.setAttribute("email", user.getEmail());
             session.setAttribute("token", tokenForgetPassword.getToken());
             request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
         } else {
             request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
         }
     } 
 
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
         String email = request.getParameter("email");
         String password = request.getParameter("password");
         String confirmPassword = request.getParameter("confirm_password");
         //validate password...
         if(!password.equals(confirmPassword)) {
             request.setAttribute("mess", "confirm password must same password");
             request.setAttribute("email", email);
             request.getRequestDispatcher("resetPassword.jsp").forward(request, response);
             return;
         }
         HttpSession session = request.getSession();
         String tokenStr = (String) session.getAttribute("token");
         TokenForgetPassword tokenForgetPassword = DAOToken.getTokenPassword(tokenStr);
         //check token is valid, of time, of used
         resetService service = new resetService();
         if (tokenForgetPassword == null) {
             request.setAttribute("mess", "token invalid");
             request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
             return;
         }
         if (tokenForgetPassword.isIsUsed()) {
             request.setAttribute("mess", "token is used");
             request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
             return;
         }
         if (service.isExpireTime(tokenForgetPassword.getExpiryTime())) {
             request.setAttribute("mess", "token is expiry time");
             request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
             return;
         }
 
         //update is used of token
         tokenForgetPassword.setToken(tokenStr);
         tokenForgetPassword.setIsUsed(true);
 
         DAOUser.updatePassword(email, password);
         DAOToken.updateStatus(tokenForgetPassword);

         //log
         String ip = request.getRemoteAddr();
         User user = (User) session.getAttribute("auth");
         ThreadContext.put("user_id", String.valueOf(user.getId()));
         ThreadContext.put("ip", ip);
         ThreadContext.put("resource", "Reset password");
         ThreadContext.put("data_in", "username=" + user.getUsername());
         ThreadContext.put("data_out", "SUCCESS");

         logger.info("User reset password successful");
 
         //save user in session and redirect to home
         request.getRequestDispatcher("index.jsp").forward(request, response);
     }
 }
 