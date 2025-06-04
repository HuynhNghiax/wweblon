package com.example.doanltweb.dao.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class StockIn {
	private int id;
	private String stockDate;
	private Product product;
	private int quantity;
	
	
	
	
	public StockIn() {
		super();
	}
	public StockIn(int id, String stockDate, Product product, int quantity) {
		super();
		this.id = id;
		this.stockDate = stockDate;
		this.product = product;
		this.quantity = quantity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStockDate() {
		return stockDate;
	}
	public void setStockDate(String stockDate) {
		this.stockDate = stockDate;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setAmount(int quantity) {
		this.quantity = quantity;
	}
	
	
}
