package com.example.doanltweb.controller.Admin;

import com.example.doanltweb.dao.UserDao;
import com.example.doanltweb.dao.model.User;
import com.example.doanltweb.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;



@WebServlet(name = "UserManagerController", value = "/UserManagerController")
public class UserManagerController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Gọi UserDao thay vì UserDAO
        UserDao userDao = new UserDao();
        List<User> users = userDao.getUsersForAdmin();

        // Chuyển danh sách người dùng sang JSON
        Gson gson = new Gson();
        String userJson = gson.toJson(users);

        // Ghi JSON ra response
        response.getWriter().write(userJson);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        JsonObject jsonResponse = new JsonObject();

        try (BufferedReader reader = request.getReader()) {
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }

            String json = jsonBuffer.toString();

            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

            int id = jsonObject.get("id").getAsInt();
            int idPermission = jsonObject.get("idPermission").getAsInt();
            int isVerified = jsonObject.get("isVerified").getAsInt();

            if (id <= 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse.addProperty("message", "ID người dùng không hợp lệ.");
                response.getWriter().write(gson.toJson(jsonResponse));
                return;
            }

            UserService userService = new UserService();
            boolean isUpdated = userService.updateRoleAndStatus(id, idPermission, isVerified);

            if (isUpdated) {
                response.setStatus(HttpServletResponse.SC_OK);
                jsonResponse.addProperty("message", "Cập nhật vai trò và trạng thái thành công.");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                jsonResponse.addProperty("message", "Không tìm thấy người dùng để cập nhật.");
            }

        } catch (JsonSyntaxException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.addProperty("message", "JSON không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.addProperty("message", "Lỗi xử lý: " + e.getMessage());
        }

        response.getWriter().write(gson.toJson(jsonResponse));
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Đọc JSON từ request
            BufferedReader reader = request.getReader();
            Gson gson = new Gson();
            User user = gson.fromJson(reader, User.class);

            // Kiểm tra ID hợp lệ
            if (user.getId() > 0) {
                UserService userService = new UserService();
                boolean success = userService.updateVerifiedStatus(user.getId(), 0);

                if (success) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("{\"message\": \"Đã chuyển trạng thái người dùng thành 'Không hoạt động'\"}");
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("{\"message\": \"Không thể cập nhật trạng thái người dùng\"}");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Thiếu ID người dùng\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Lỗi xử lý yêu cầu\"}");
        }
    }



}
