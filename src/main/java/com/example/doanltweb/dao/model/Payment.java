package com.example.doanltweb.dao.model;

public class Payment {
	private int id;
	private String name;
	public Payment(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Payment() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
