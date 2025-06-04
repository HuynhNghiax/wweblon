package com.example.doanltweb.dao.model;

import java.time.LocalDateTime;

public class Sale {
    private int id;
    private double promotion;
    private String description;
    private int status;
    private LocalDateTime startDate;  // Sử dụng LocalDateTime cho ngày giờ bắt đầu
    private LocalDateTime endDate;
    private int idProduct;

    public Sale() {
    }

    public Sale(int id, double promotion, String description, int status, LocalDateTime startDate, LocalDateTime endDate, int idProduct) {
        this.id = id;
        this.promotion = promotion;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.idProduct = idProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPromotion() {
        return promotion;
    }

    public void setPromotion(double promotion) {
        this.promotion = promotion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", promotion=" + promotion +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", idProduct=" + idProduct +
                '}';
    }
}
