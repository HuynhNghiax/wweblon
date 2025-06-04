package com.example.doanltweb.controller.Admin;

import com.example.doanltweb.dao.ProductDao;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "ProductController", value = "/productController")
public class ProductController extends HttpServlet {

    private ProductDao productDao;  // Đổi tên từ ProductService thành productDao cho dễ hiểu

    public void init() {
        productDao = new ProductDao();  // Khởi tạo ProductDao
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ database
        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.getAll();

        // Chuyển danh sách sản phẩm thành JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(products);

        // Kiểm tra JSON
        System.out.println(jsonResponse); // In ra dữ liệu JSON để kiểm tra

        // Gửi dữ liệu JSON về client
        response.getWriter().write(jsonResponse);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Đọc JSON từ request body
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }
            String jsonData = jsonBuffer.toString();

            // Parse JSON thành Product object, bỏ qua id
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Product product = gson.fromJson(jsonData, Product.class);

//            System.out.println("bcd  " + product);
            // Không sử dụng id từ client gửi lên (nếu có)
//            product.setId(null);  // Đảm bảo id không ảnh hưởng, DB sẽ tự sinh nếu cần

            // Kiểm tra các trường bắt buộc
            if (product.getNameProduct() == null || product.getNameProduct().trim().isEmpty() ||
                    product.getImage() == null || product.getImage().trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Vui lòng điền đầy đủ tên sản phẩm và ảnh!\"}");
                return;
            }

            // Gọi service để thêm sản phẩm
            ProductService productService = new ProductService();
            boolean success = productService.addProduct(product);

            JsonObject jsonResponse = new JsonObject();
            if (success) {
                jsonResponse.addProperty("message", "Sản phẩm đã được thêm thành công!");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.addProperty("message", "Có lỗi xảy ra khi thêm sản phẩm.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            response.getWriter().write(gson.toJson(jsonResponse));

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Dữ liệu JSON không hợp lệ\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Lỗi hệ thống: " + e.getMessage() + "\"}");
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            // Đọc JSON từ request body
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }
            String jsonData = jsonBuffer.toString();
            System.out.println("Received JSON: " + jsonData);  // Kiểm tra dữ liệu JSON nhận được

            // Parse JSON thành Product object
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

            Product product = gson.fromJson(jsonData, Product.class);
            product.setId(id);
            System.out.println("Product: " + product);
            // Kiểm tra các trường bắt buộc
            if (product.getId() == 0 || product.getNameProduct() == null || product.getNameProduct().trim().isEmpty() ||
                    product.getImage() == null || product.getImage().trim().isEmpty() ||
                    product.getPriceProduct() <= 0 || product.getStock() < 0) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Vui lòng điền đầy đủ thông tin (bao gồm id, tên sản phẩm, ảnh, giá và số lượng)!\"}");
                return;
            }

            // Gọi ProductDao để cập nhật sản phẩm
            boolean success = productDao.updateProduct(product);

            JsonObject jsonResponse = new JsonObject();
            if (success) {
                jsonResponse.addProperty("message", "Sản phẩm đã được cập nhật thành công!");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.addProperty("message", "Có lỗi xảy ra khi cập nhật sản phẩm.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            response.getWriter().write(gson.toJson(jsonResponse));

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Dữ liệu JSON không hợp lệ\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Lỗi hệ thống: " + e.getMessage() + "\"}");
        }
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Lấy ID từ query string
            String idParam = request.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"ID sản phẩm là bắt buộc\"}");
                return;
            }

            int productId = Integer.parseInt(idParam);

            // Gọi phương thức xóa sản phẩm
            ProductDao productDao = new ProductDao();
            boolean isDeleted = productDao.deleteProduct(productId);

            // Trả về kết quả
            JsonObject jsonResponse = new JsonObject();
            if (isDeleted) {
                jsonResponse.addProperty("message", "Sản phẩm đã được xóa thành công!");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                jsonResponse.addProperty("message", "Có lỗi xảy ra khi xóa sản phẩm.");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

            // Gửi dữ liệu JSON về client
            response.getWriter().write(new Gson().toJson(jsonResponse));

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"ID sản phẩm không hợp lệ\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"Lỗi hệ thống: " + e.getMessage() + "\"}");
        }
    }


}




