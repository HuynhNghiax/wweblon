package com.example.doanltweb.dao.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private int id;
	private int userId;
	private double totalPrice;
	private int totalAmount;
	public Cart(int id, int userId, double totalPrice, int totalAmount) {
		super();
		this.id = id;
		this.userId = userId;
		this.totalPrice = totalPrice;
		this.totalAmount = totalAmount;
	}
	public Cart() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public int getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", userId=" + userId + ", totalPrice=" + totalPrice + ", totalAmount=" + totalAmount
				+ "]";
	}
	

   
}
