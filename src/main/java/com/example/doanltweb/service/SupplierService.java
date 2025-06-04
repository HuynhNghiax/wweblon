package com.example.doanltweb.service;

import com.example.doanltweb.dao.ProductDao;
import com.example.doanltweb.dao.SupplierDao;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.Supplier;

import java.awt.*;
import java.util.List;

public class SupplierService {
    static SupplierDao supplierDao = new SupplierDao();

    public List<Supplier> getAllSupplier() {
        return supplierDao.getAllSuppliers();
    }

    public Supplier getSupplierBySupplierId(int supplierid) {
        return supplierDao.getSupplierById(supplierid);
    }

    public static void main(String[] args) {
        SupplierService service = new SupplierService();
        List<Supplier> suppliers = service.getAllSupplier();
        System.out.println(suppliers);
    }
}
