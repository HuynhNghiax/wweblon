package com.example.doanltweb.dao.model;

public class CartItem {
	private int id;
	private int cartId;
    private Product product;
    private int quantity;



    public CartItem() {
		super();
	}

	public CartItem(int id, int cartId, Product product, int quantity) {
		super();
		this.id = id;
		this.cartId = cartId;
		this.product = product;
		this.quantity = quantity;
	}

	public CartItem(int cartId,Product product, int quantity) {
		this.cartId = cartId;
		this.product = product;
		this.quantity = quantity;
	}

	public void increaseQuantity() {
        this.quantity++;
    }
    
	
	
	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
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

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void decreaseQuantity() {
        this.quantity--;
    }

	@Override
	public String toString() {
		return "CartItem [id=" + id + ", cartId=" + cartId + ", product=" + product + ", quantity=" + quantity + "]";
	}
	
	
}
