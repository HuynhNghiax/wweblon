package com.example.doanltweb.dao;

import com.example.doanltweb.dao.db.JDBIConnect;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.Supplier;
import org.jdbi.v3.core.Jdbi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierDao {
    static Map<Integer, Product> data = new HashMap<>();
    public List<Supplier> getAllSuppliers() {
        Jdbi jdbi = JDBIConnect.get();
        return jdbi.withHandle(handle ->  handle.createQuery("select * from supplier").mapToBean(Supplier.class).list());
    }
    public Supplier getSupplierById(int id) {
        Jdbi jdbi = JDBIConnect.get();
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM suppliers WHERE id_supplier = id\n").bind("id", id)
                .mapToBean(Supplier.class).findOne().orElse(null));
    }
}
