package com.example.doanltweb.dao.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Product implements java.io.Serializable {
        private int id;
        private String nameProduct;
        private String image;
        private double priceProduct;
        private String description;
        private String manufactureDate;
        private String power;
        private double pressure;
        private double flowRate;
        private double pipeDiameter;
        private int voltage;
        private String brand;
        private int warrantyMonths;
        private int stock;
        private int idCategory;
        private int idSupplier;

    public Product() {
    }

        public Product(String nameProduct, String image, double priceProduct, String description, String manufactureDate, String power, double pressure, double flowRate, double pipeDiameter, int voltage, String brand, int warrantyMonths, int stock, int idCategory, int idSupplier) {
            this.nameProduct = nameProduct;
            this.image = image;
            this.priceProduct = priceProduct;
            this.description = description;
            this.manufactureDate = manufactureDate;
            this.power = power;
            this.pressure = pressure;
            this.flowRate = flowRate;
            this.pipeDiameter = pipeDiameter;
            this.voltage = voltage;
            this.brand = brand;
            this.warrantyMonths = warrantyMonths;
            this.stock = stock;
            this.idCategory = idCategory;
            this.idSupplier = idSupplier;
        }

        public int getId() {
            return id;
        }

//        public void setId(int id) {
//            this.id = id;
//        }
        public void setId(int id) {
            this.id = id;
        }


        public String getNameProduct() {
            return nameProduct;
        }

        public void setNameProduct(String nameProduct) {
            this.nameProduct = nameProduct;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public double getPriceProduct() {
            return priceProduct;
        }

        public void setPriceProduct(double priceProduct) {
            this.priceProduct = priceProduct;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getManufactureDate() {
            return manufactureDate;
        }

        public void setManufactureDate(String manufactureDate) {
            this.manufactureDate = manufactureDate;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public double getPressure() {
            return pressure;
        }

        public void setPressure(double pressure) {
            this.pressure = pressure;
        }

        public double getFlowRate() {
            return flowRate;
        }

        public void setFlowRate(double flowRate) {
            this.flowRate = flowRate;
        }

        public int getVoltage() {
            return voltage;
        }

        public void setVoltage(int voltage) {
            this.voltage = voltage;
        }

        public double getPipeDiameter() {
            return pipeDiameter;
        }

        public void setPipeDiameter(double pipeDiameter) {
            this.pipeDiameter = pipeDiameter;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public int getWarrantyMonths() {
            return warrantyMonths;
        }

        public void setWarrantyMonths(int warrantyMonths) {
            this.warrantyMonths = warrantyMonths;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getIdCategory() {
            return idCategory;
        }

        public void setIdCategory(int idCategory) {
            this.idCategory = idCategory;
        }

        public int getIdSupplier() {
            return idSupplier;
        }

        public void setIdSupplier(int idSupplier) {
            this.idSupplier = idSupplier;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Product product = (Product) o;
            return id == product.id; // So sánh theo ID chẳng hạn
        }

        @Override
        public int hashCode() {
            return Objects.hash(id); // Hash dựa trên ID
        }


        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", nameProduct='" + nameProduct + '\'' +
                    ", image='" + image + '\'' +
                    ", priceProduct=" + priceProduct +
                    ", description='" + description + '\'' +
                    ", manufactureDate='" + manufactureDate + '\'' +
                    ", power='" + power + '\'' +
                    ", pressure=" + pressure +
                    ", flowRate=" + flowRate +
                    ", pipeDiameter=" + pipeDiameter +
                    ", voltage=" + voltage +
                    ", brand='" + brand + '\'' +
                    ", warrantyMonths=" + warrantyMonths +
                    ", stock=" + stock +
                    ", idCategory=" + idCategory +
                    ", idSupplier=" + idSupplier +
                    '}';
        }
    }

