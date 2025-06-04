package com.example.doanltweb.dao.model;

public class OrderDetail {
	private int id;
	private int idOrder;
	private Product product;
	private int quantity;
	private double price;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public OrderDetail() {
		super();
	}
	public OrderDetail(int id, int idOrder, Product product, int quantity, double price) {
		super();
		this.id = id;
		this.idOrder = idOrder;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
	}
	
	
	
}
