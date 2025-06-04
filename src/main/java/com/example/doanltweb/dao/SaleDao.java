package com.example.doanltweb.dao;

import com.example.doanltweb.dao.db.JDBIConnect;
import com.example.doanltweb.dao.model.Sale;
import org.jdbi.v3.core.Jdbi;

import java.time.LocalDateTime;
import java.util.List;

public class SaleDao {

    private static final Jdbi jdbi = JDBIConnect.get();

    // Thêm một Sale mới
    public boolean addSale(Sale sale) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO sales (id, promotion, description, status, startDate, endDate, idProduct) " +
                                "VALUES (:id, :promotion, :description, :status, :startDate, :endDate, :idProduct)")
                        .bindBean(sale)
                        .execute() > 0);
    }


    // Lấy tất cả các Sale
    public List<Sale> getAllSales() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM sales")
                        .mapToBean(Sale.class)
                        .list());
    }

    // Lấy Sale theo ID
    public Sale getSaleById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM sales WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Sale.class)
                        .findOnly());
    }

    // Cập nhật thông tin Sale
    public boolean updateSale(Sale sale) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE sales SET promotion = :promotion, description = :description, status = :status, " +
                                "startDate = :startDate, endDate = :endDate, idProduct = :idProduct WHERE id = :id")
                        .bindBean(sale)
                        .execute() > 0);
    }

    // Xóa Sale theo ID
    public boolean deleteSale(int id) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM sales WHERE id = :id")
                        .bind("id", id)
                        .execute() > 0);
    }


    public static void main(String[] args) {

            SaleDao saleDao = new SaleDao();

            // 1. Tạo một Sale mới để test
//            Sale newSale = new Sale();
//            newSale.setId( 21);  // ID tự đặt nếu không có auto-increment
//            newSale.setPromotion(15.5);
//            newSale.setDescription("Khuyến mãi đầu năm");
//            newSale.setStatus(1);
//            newSale.setStartDate(LocalDateTime.now());
//            newSale.setEndDate(LocalDateTime.now().plusDays(10));
//            newSale.setIdProduct(22);  // ID sản phẩm tồn tại
//
//            // 2. Thêm khuyến mãi
//            boolean isAdded = saleDao.addSale(newSale);
//            System.out.println("Thêm khuyến mãi: " + (isAdded ? "Thành công" : "Thất bại"));

            // 3. Lấy danh sách tất cả khuyến mãi
            List<Sale> allSales = saleDao.getAllSales();
            System.out.println("Danh sách khuyến mãi:");
            allSales.forEach(sale -> {
                System.out.println("ID: " + sale.getId());
                System.out.println("Giảm giá: " + sale.getPromotion() + "%");
                System.out.println("Mô tả: " + sale.getDescription());
                System.out.println("Trạng thái: " + (sale.getStatus() == 1 ? "Đang áp dụng" : "Không áp dụng"));
                System.out.println("Bắt đầu: " + sale.getStartDate());
                System.out.println("Kết thúc: " + sale.getEndDate());
                System.out.println("ID Sản phẩm: " + sale.getIdProduct());
                System.out.println("----------------------");
            });


        // 4. Cập nhật một khuyến mãi có id = 3
        Sale updateSale = saleDao.getSaleById(3);
        if (updateSale != null) {
            updateSale.setDescription("Khuyến mãi cập nhật cho ID 3");
            updateSale.setPromotion(20.0);
            boolean isUpdated = saleDao.updateSale(updateSale);
            System.out.println("Cập nhật khuyến mãi có ID 3: " + (isUpdated ? "Thành công" : "Thất bại"));
        } else {
            System.out.println("Không tìm thấy khuyến mãi có ID = 3");
        }


//        // 5. Xóa khuyến mãi theo ID
//            int deleteId = 103;
//            boolean isDeleted = saleDao.deleteSale(deleteId);
//            System.out.println("Xóa khuyến mãi ID " + deleteId + ": " + (isDeleted ? "Thành công" : "Thất bại"));
        }



    }

