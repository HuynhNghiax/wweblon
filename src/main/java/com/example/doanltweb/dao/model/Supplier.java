package com.example.doanltweb.dao.model;

import java.io.Serializable;

public class Supplier implements Serializable {
    private String id;
    private String nameSupplier;
    private String phone;
    private String email;
    private String address;
    private String image;

    @Override
    public String toString() {
        return "Supplier{" +
                "id='" + id + '\'' +
                ", nameSupplier='" + nameSupplier + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public Supplier() {
    }

    public Supplier(String id, String nameSupplier, String phone, String email, String address, String image) {
        this.id = id;
        this.nameSupplier = nameSupplier;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameSupplier() {
        return nameSupplier;
    }

    public void setNameSupplier(String nameSupplier) {
        this.nameSupplier = nameSupplier;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
