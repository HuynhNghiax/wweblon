package com.example.doanltweb.dao;

import com.example.doanltweb.dao.db.JDBIConnect;
import com.example.doanltweb.dao.model.Voucher;
import org.jdbi.v3.core.Jdbi;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VoucherDao {
    private static final Jdbi jdbi = JDBIConnect.get();

    public List<Voucher> getAllVouchers() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM vouchers")
                        .mapToBean(Voucher.class)
                        .list());
    }

    public boolean addVoucher(Voucher voucher) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO vouchers (code, discountValue, minOrderValue, usageLimit, usedCount, maxUsagePerUser, startDate, endDate, status) " +
                                "VALUES (:code, :discountValue, :minOrderValue, :usageLimit, :usedCount, :maxUsagePerUser, :startDate, :endDate, :status)")
                        .bind("code", voucher.getCode())
                        .bind("discountValue", voucher.getDiscountValue())
                        .bind("minOrderValue", voucher.getMinOrderValue())
                        .bind("usageLimit", voucher.getUsageLimit())
                        .bind("usedCount", voucher.getUsedCount())
                        .bind("maxUsagePerUser", voucher.getMaxUsagePerUser())
                        .bind("startDate", voucher.getStartDate())
                        .bind("endDate", voucher.getEndDate())
                        .bind("status", voucher.getStatus())
                        .execute() > 0);
    }
    // Cập nhật thông tin Voucher
    public boolean updateVoucher(Voucher voucher) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE vouchers SET " +
                                "code = :code, " +
                                "discountValue = :discountValue, " +
                                "minOrderValue = :minOrderValue, " +
                                "usageLimit = :usageLimit, " +
                                "usedCount = :usedCount, " +
                                "maxUsagePerUser = :maxUsagePerUser, " +
                                "startDate = :startDate, " +
                                "endDate = :endDate, " +
                                "status = :status " +
                                "WHERE id = :id")
                        .bindBean(voucher)  // Sử dụng bindBean để tự động ánh xạ các thuộc tính
                        .execute() > 0);
    }
    public Voucher getVoucherById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM vouchers WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Voucher.class)
                        .findOnly());
    }
    public boolean deleteVoucher(int id) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM vouchers WHERE id = :id")
                        .bind("id", id)
                        .execute() > 0);
    }
    public Voucher getVoucherByCode(String code) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM vouchers WHERE code = :code")
                        .bind("code", code)
                        .mapToBean(Voucher.class)
                        .findOne()
                        .orElse(null)
        );
    }


    public static void main(String[] args) {
        VoucherDao voucherDao = new VoucherDao();

        Voucher updatedVoucher = new Voucher();
        updatedVoucher.setId(1);  // Đảm bảo có id của voucher cần sửa
        updatedVoucher.setCode("NEWYEAR2029");
        updatedVoucher.setDiscountValue("25");
        updatedVoucher.setMinOrderValue(600000.00);
        updatedVoucher.setUsageLimit(200);
        updatedVoucher.setUsedCount(10);
        updatedVoucher.setMaxUsagePerUser(2);
        updatedVoucher.setStartDate(LocalDate.of(2025, 1, 1));
        updatedVoucher.setEndDate(LocalDate.of(2025, 1, 31));
        updatedVoucher.setStatus(1);

// Gọi hàm cập nhật
        boolean success = voucherDao.updateVoucher(updatedVoucher);
        if (success) {
            System.out.println("Voucher updated successfully!");
        } else {
            System.out.println("Failed to update voucher.");
        }

    }

}
