package com.example.doanltweb.service;

import com.example.doanltweb.dao.SaleDao;
import com.example.doanltweb.dao.model.Sale;

import java.time.LocalDateTime;
import java.util.List;

public class SaleService {
    private SaleDao saleDao;

    public SaleService() {
        this.saleDao = new SaleDao();
    }

    public List<Sale> getAllSales() {
        return saleDao.getAllSales();
    }

    public boolean addSale(Sale sale) {
        return saleDao.addSale(sale);
    }
    public boolean updateSale(Sale sale) {
        return saleDao.updateSale(sale);
    }

    public boolean deleteSale(int id) {
        return saleDao.deleteSale(id);
    }

    public static void main(String[] args) {
        SaleService saleService = new SaleService();

        // Tạo một đối tượng Sale mới
        Sale newSale = new Sale();
        newSale.setId(22); // Đảm bảo id không bị trùng trong DB
        newSale.setPromotion(15.0);
        newSale.setDescription("Giảm giá mùa hè");
        newSale.setStatus(1); // Có thể là 1 là "có hiệu lực", 0 là "không"
        newSale.setStartDate(LocalDateTime.now());
        newSale.setEndDate(LocalDateTime.now().plusDays(7));
        newSale.setIdProduct(22); // ID sản phẩm phải tồn tại trong bảng product

        // Thêm khuyến mãi
        boolean added = saleService.addSale(newSale);
        System.out.println("Thêm khuyến mãi: " + (added ? "Thành công" : "Thất bại"));

        // In danh sách khuyến mãi
        List<Sale> sales = saleService.getAllSales();
        for (Sale sale : sales) {
            System.out.println("ID: " + sale.getId());
            System.out.println("Giảm giá: " + sale.getPromotion());
            System.out.println("Mô tả: " + sale.getDescription());
            System.out.println("Trạng thái: " + sale.getStatus());
            System.out.println("Bắt đầu: " + sale.getStartDate());
            System.out.println("Kết thúc: " + sale.getEndDate());
            System.out.println("ID Sản phẩm: " + sale.getIdProduct());
            System.out.println("--------------------------");
        }
    }
}

