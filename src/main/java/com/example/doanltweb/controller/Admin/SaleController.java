package com.example.doanltweb.controller.Admin;

import com.example.doanltweb.dao.SaleDao;
import com.example.doanltweb.dao.model.Sale;
import com.example.doanltweb.service.SaleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "SaleController", value = "/SaleController")
public class SaleController extends HttpServlet {

    private SaleDao saleDao;

    @Override
    public void init() {
        saleDao = new SaleDao(); // Khởi tạo DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ database
        SaleDao saleDao = new SaleDao();
        List<Sale> sales = saleDao.getAllSales();

        // Khởi tạo ObjectMapper và đăng ký module hỗ trợ Java 8 Date/Time
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Để trả về chuỗi ISO thay vì timestamp

        // Chuyển danh sách khuyến mãi thành JSON
        String jsonResponse = objectMapper.writeValueAsString(sales);

        // Ghi JSON vào response
        response.getWriter().write(jsonResponse);

        // In ra console để debug nếu cần
        System.out.println(" JSON response: " + jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Đọc JSON từ request body
            BufferedReader reader = request.getReader();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            // Chuyển JSON thành đối tượng Sale
            Sale newSale = objectMapper.readValue(reader, Sale.class);

            // Gọi DAO để thêm Sale
            boolean success = saleDao.addSale(newSale);

            // Gửi phản hồi JSON về client
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", success);
            responseMap.put("message", success ? "Thêm khuyến mãi thành công!" : "Thêm khuyến mãi thất bại!");

            String jsonResponse = objectMapper.writeValueAsString(responseMap);
            response.getWriter().write(jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();

            // Trả về lỗi nếu có exception
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Dữ liệu không hợp lệ hoặc lỗi máy chủ.");
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().write(mapper.writeValueAsString(error));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Khởi tạo ObjectMapper một lần
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            // Đọc và parse JSON từ request body
            BufferedReader reader = request.getReader();
            Sale updatedSale = objectMapper.readValue(reader, Sale.class);

            // Kiểm tra các trường bắt buộc
            if (updatedSale.getId() <= 0 || updatedSale.getPromotion() <= 0 ||
                    updatedSale.getDescription() == null || updatedSale.getDescription().trim().isEmpty() ||
                    updatedSale.getStartDate() == null || updatedSale.getEndDate() == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                Map<String, String> error = new HashMap<>();
                error.put("error", "Vui lòng cung cấp đầy đủ thông tin bao gồm ID, khuyến mãi, mô tả, ngày bắt đầu và kết thúc!");
                response.getWriter().write(objectMapper.writeValueAsString(error));
                return;
            }

            // Gọi DAO để cập nhật
            boolean success = saleDao.updateSale(updatedSale);

            // Phản hồi kết quả
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", success);
            responseMap.put("message", success ? "Cập nhật khuyến mãi thành công!" : "Có lỗi xảy ra khi cập nhật khuyến mãi!");

            response.getWriter().write(objectMapper.writeValueAsString(responseMap));

        } catch (Exception e) {
            e.printStackTrace();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Dữ liệu không hợp lệ hoặc lỗi máy chủ.");
            response.getWriter().write(objectMapper.writeValueAsString(error));
        }
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            int id = Integer.parseInt(request.getParameter("id")); // Lấy ID từ query string

            boolean success = saleDao.deleteSale(id);

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("success", success);
            responseMap.put("message", success ? "Xóa khuyến mãi thành công!" : "Xóa khuyến mãi thất bại hoặc không tìm thấy!");

            response.getWriter().write(objectMapper.writeValueAsString(responseMap));

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> error = new HashMap<>();
            error.put("error", "ID không hợp lệ!");
            response.getWriter().write(objectMapper.writeValueAsString(error));

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> error = new HashMap<>();
            error.put("error", "Có lỗi xảy ra khi xóa khuyến mãi.");
            response.getWriter().write(objectMapper.writeValueAsString(error));
        }
    }


}
