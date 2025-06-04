package com.example.doanltweb.dao.model;

import java.util.Date;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

public class Order {
	private int id;
	private User user;
	private double totalPrice;
	private String orderDate;
	private String status;
	private Payment paymentMethod;
	private int quantity;
	private String otp;
	private boolean verified;
	private int idUser;

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getOtp() {
		return otp;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Payment getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Payment paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public Order() {
		super();
	}
	public Order(int id, User user, double totalPrice, String orderDate, String status, Payment paymentMethod,
			int quantity,String otp) {
		super();
		this.id = id;
		this.user = user;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.quantity = quantity;
		this.otp = otp;
	}


	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", user=" + user +
				", totalPrice=" + totalPrice +
				", orderDate='" + orderDate + '\'' +
				", status='" + status + '\'' +
				", paymentMethod=" + paymentMethod +
				", quantity=" + quantity +
				", otp='" + otp + '\'' +
				", verified=" + verified +
				'}';
	}

	public Order(int id, User user, double totalPrice, String orderDate, String status, Payment paymentMethod, int quantity, String otp, boolean verified) {
		this.id = id;
		this.user = user;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
		this.status = status;
		this.paymentMethod = paymentMethod;
		this.quantity = quantity;
		this.otp = otp;
		this.verified = verified;
	}
}
