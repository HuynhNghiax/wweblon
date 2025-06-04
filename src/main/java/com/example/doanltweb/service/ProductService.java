package com.example.doanltweb.service;


import com.example.doanltweb.dao.ProductDao;
import com.example.doanltweb.dao.model.Product;

import java.util.List;

public class ProductService {
    private ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
    }

    // Thêm sản phẩm
    public boolean addProduct(Product product) {
        return productDao.addProduct(product);
    }

    // Lấy tất cả sản phẩm
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    public boolean updateProduct(Product product) {

        return productDao.updateProduct(product);
    }

    // Xóa sản phẩm
    public boolean deleteProduct(int id) {
        return productDao.deleteProduct(id);
    }
    public static void main(String[] args) {
        // Tạo đối tượng ProductService để thực hiện các thao tác với sản phẩm
        ProductService productService = new ProductService();

        // Tạo đối tượng Product để thêm
        Product product = new Product();
        product.setNameProduct("Máy bơm nước100");
        product.setImage("image_url.jpg");
        product.setPriceProduct(1000000);
        product.setDescription("Máy bơm nước hiệu quả cao");
        product.setManufactureDate("2025-03-20");
        product.setPower("2 HP");
        product.setPressure(10.5);
        product.setFlowRate(500);
        product.setPipeDiameter(50);
        product.setVoltage(220);
        product.setBrand("Bơm ABC");
        product.setWarrantyMonths(24);
        product.setStock(50);
        product.setIdCategory(1);
        product.setIdSupplier(2);

        // Thêm sản phẩm vào cơ sở dữ liệu thông qua ProductService
        boolean isAdded = productService.addProduct(product);

        // Kiểm tra kết quả thêm sản phẩm
        if (isAdded) {
            System.out.println("Sản phẩm đã được thêm thành công!");
        } else {
            System.out.println("Có lỗi xảy ra khi thêm sản phẩm.");
        }
    }


}
