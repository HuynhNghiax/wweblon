package com.example.doanltweb.dao.model;

import java.time.LocalDateTime;

public class VoucherUsage {
    private int id;
    private int voucherId;
    private int userId;
    private int orderId;
    private LocalDateTime usedAt;

    public VoucherUsage() {
    }

    public VoucherUsage(int id, int voucherId, int userId, int orderId, LocalDateTime usedAt) {
        this.id = id;
        this.voucherId = voucherId;
        this.userId = userId;
        this.orderId = orderId;
        this.usedAt = usedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }

    @Override
    public String toString() {
        return "VoucherUsage{" +
                "id=" + id +
                ", voucherId=" + voucherId +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", usedAt=" + usedAt +
                '}';
    }
}
