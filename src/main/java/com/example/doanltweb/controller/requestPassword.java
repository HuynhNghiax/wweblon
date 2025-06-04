
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
 
 @WebServlet(name="requestPassword", urlPatterns={"/requestPassword"})
 public class requestPassword extends HttpServlet {
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
         request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
     } 
 
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
         UserDao daoUser = new UserDao();
         String email = request.getParameter("email");
         //email co ton tai trong db
         User user = daoUser.getUserByEmail(email);
         if(user == null) {
             request.setAttribute("mess", "email khong ton tai");
             request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
             return;
         }
         resetService service = new resetService();
         String token = service.generateToken();
         
         String linkReset = "http://localhost:8080/DoAnLTWeb/resetPassword?token="+token;
         
         TokenForgetPassword newTokenForget = new TokenForgetPassword(
                 user.getId(), false, token, service.expireDateTime());
         
         //send link to this email
         DAOTokenForget daoToken = new DAOTokenForget();
         boolean isInsert = daoToken.insertTokenForget(newTokenForget);
         if(!isInsert) {
             request.setAttribute("mess", "have error in server");
             request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
             return;
         }
         boolean isSend = service.sendEmail(email, linkReset, user.getUsername());
         if(!isSend) {
             request.setAttribute("mess", "can not send request");
             request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
             return;
         }
         request.setAttribute("mess", "send request success");
         request.getRequestDispatcher("requestPassword.jsp").forward(request, response);
     }
 }
 