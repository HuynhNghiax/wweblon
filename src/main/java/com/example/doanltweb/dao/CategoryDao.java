package com.example.doanltweb.dao;

import com.example.doanltweb.dao.db.JDBIConnect;
import com.example.doanltweb.dao.model.Category;
import com.example.doanltweb.dao.model.Product;
import org.jdbi.v3.core.Jdbi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDao {
    static Map<Integer, Product> data = new HashMap<>();
    public List<Category> getAllCategories() {
        Jdbi jdbi = JDBIConnect.get();
        return jdbi.withHandle(handle ->  handle.createQuery("select * from category").mapToBean(Category.class).list());
    }
    public Category getSupplierById(int id) {
        Jdbi jdbi = JDBIConnect.get();
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM category WHERE id = id\n").bind("id", id)
                .mapToBean(Category.class).findOne().orElse(null));
    }
    public List<Product> getProductByCategory(int categoryId) {
        Jdbi jdbi = JDBIConnect.get();
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM product WHERE idCategory = :categoryId")
                        .bind("categoryId",categoryId)
                        .mapToBean(Product.class)
                        .list()
        );
    }
}
