package com.example.doanltweb.controller.Admin;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.doanltweb.dao.VoucherDao;
import com.example.doanltweb.dao.model.Voucher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "VoucherController", value = "/VoucherController")
public class VoucherController extends HttpServlet {
    private VoucherDao voucherDao;

    @Override
    public void init() {
        voucherDao = new VoucherDao(); // Khởi tạo DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Lấy dữ liệu từ database
        List<Voucher> vouchers = voucherDao.getAllVouchers();

        // Khởi tạo ObjectMapper và đăng ký module hỗ trợ Java 8 Date/Time
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Trả về ISO string thay vì timestamp

        // Chuyển danh sách voucher thành JSON
        String jsonResponse = objectMapper.writeValueAsString(vouchers);

        // Ghi JSON vào response
        response.getWriter().write(jsonResponse);

        // In ra console để debug nếu cần
        System.out.println("JSON response (Voucher): " + jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            BufferedReader reader = request.getReader();
            Voucher newVoucher = objectMapper.readValue(reader, Voucher.class);

            // Debug
            System.out.println("Voucher received: " + newVoucher);
            System.out.println("Start date: " + newVoucher.getStartDate());
            System.out.println("End date: " + newVoucher.getEndDate());

            if (newVoucher.getStartDate() == null || newVoucher.getEndDate() == null) {
                sendError(response, objectMapper, "Ngày bắt đầu và ngày kết thúc không được để trống!");
                return;
            }

            if (newVoucher.getStartDate().isAfter(newVoucher.getEndDate())) {
                sendError(response, objectMapper, "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc!");
                return;
            }

            boolean success = voucherDao.addVoucher(newVoucher);
            if (!success) {
                sendError(response, objectMapper, "Thêm voucher thất bại. Vui lòng thử lại.");
                return;
            }

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Thêm voucher thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            sendError(response, objectMapper, "Dữ liệu JSON không hợp lệ hoặc sai định dạng ngày (yyyy-MM-dd).");

        } catch (Exception e) {
            e.printStackTrace();
            sendError(response, objectMapper, "Lỗi máy chủ, vui lòng thử lại.");
        }
    }

    private void sendError(HttpServletResponse response, ObjectMapper mapper, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        response.getWriter().write(mapper.writeValueAsString(error));
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            BufferedReader reader = request.getReader();
            Voucher updatedVoucher = objectMapper.readValue(reader, Voucher.class);

            // Debug log
            System.out.println("Voucher received for update: " + updatedVoucher);

            if (updatedVoucher.getId() <= 0) {
                // Trả về lỗi nếu ID voucher không hợp lệ
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("success", false);
                errorResult.put("message", "ID của voucher không hợp lệ.");
                response.getWriter().write(objectMapper.writeValueAsString(errorResult));
                return;
            }

            if (updatedVoucher.getStartDate() == null || updatedVoucher.getEndDate() == null) {
                // Trả về lỗi nếu ngày bắt đầu hoặc ngày kết thúc thiếu
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("success", false);
                errorResult.put("message", "Ngày bắt đầu và ngày kết thúc không được để trống.");
                response.getWriter().write(objectMapper.writeValueAsString(errorResult));
                return;
            }

            if (updatedVoucher.getStartDate().isAfter(updatedVoucher.getEndDate())) {
                // Trả về lỗi nếu ngày bắt đầu sau ngày kết thúc
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("success", false);
                errorResult.put("message", "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
                response.getWriter().write(objectMapper.writeValueAsString(errorResult));
                return;
            }

            // Gọi phương thức updateVoucher từ VoucherDao
            boolean success = voucherDao.updateVoucher(updatedVoucher);
            if (!success) {
                // Trả về lỗi nếu cập nhật thất bại
                Map<String, Object> errorResult = new HashMap<>();
                errorResult.put("success", false);
                errorResult.put("message", "Cập nhật voucher thất bại. Vui lòng thử lại.");
                response.getWriter().write(objectMapper.writeValueAsString(errorResult));
                return;
            }

            // Trả về kết quả thành công
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Cập nhật voucher thành công!");
            response.getWriter().write(objectMapper.writeValueAsString(result));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Trả về lỗi nếu dữ liệu JSON không hợp lệ
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("message", "Dữ liệu JSON không hợp lệ hoặc sai định dạng ngày (yyyy-MM-dd).");
            response.getWriter().write(objectMapper.writeValueAsString(errorResult));
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về lỗi máy chủ
            Map<String, Object> errorResult = new HashMap<>();
            errorResult.put("success", false);
            errorResult.put("message", "Lỗi máy chủ, vui lòng thử lại.");
            response.getWriter().write(objectMapper.writeValueAsString(errorResult));
        }
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String idParam = request.getParameter("id");
            if (idParam == null) {
                sendError(response, objectMapper, "Thiếu ID voucher để xóa.");
                return;
            }

            int id = Integer.parseInt(idParam);

            boolean deleted = voucherDao.deleteVoucher(id);
            if (deleted) {
                Map<String, Object> result = new HashMap<>();
                result.put("success", true);
                result.put("message", "Xóa voucher thành công!");
                response.getWriter().write(objectMapper.writeValueAsString(result));
            } else {
                sendError(response, objectMapper, "Không tìm thấy hoặc xóa voucher thất bại.");
            }

        } catch (NumberFormatException e) {
            sendError(response, objectMapper, "ID không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            sendError(response, objectMapper, "Lỗi máy chủ khi xóa voucher.");
        }
    }

}