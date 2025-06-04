package com.example.doanltweb.dao;

import java.util.List;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Query;

import com.example.doanltweb.dao.db.JDBIConnect;
import com.example.doanltweb.dao.model.Cart;
import com.example.doanltweb.dao.model.CartItem;
import com.example.doanltweb.dao.model.Product;


public class CartDao {
	ProductDao productDao;

	public Cart getCartByUserId(int id) {
		 Jdbi jdbi = JDBIConnect.get();
	        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM cart WHERE user_id = :id")
	                .bind("id", id)
	                .mapToBean(Cart.class).findOne().orElse(null));
	}
	
	public Cart createCart(int userId) {
	    Jdbi jdbi = JDBIConnect.get();

	    // Chèn vào database và lấy ID của cart vừa tạo
	    Integer cartId = jdbi.withHandle(handle ->
	        handle.createUpdate("INSERT INTO cart (user_id) VALUES (:userId)")
	            .bind("userId", userId)
	            .executeAndReturnGeneratedKeys("id") // Lấy ID của giỏ hàng vừa tạo
	            .mapTo(Integer.class)
	            .findOne()
	            .orElse(null)
	    );

	    // Nếu không tạo được, trả về null
	    if (cartId == null) {
	        return null;
	    }

	    // Trả về đối tượng Cart mới
	    return new Cart(cartId, userId, 0,0);
	}
	
	public CartItem existInCartItem(int cartId, int productId) {
	    Jdbi jdbi = JDBIConnect.get();
	    return jdbi.withHandle(handle ->
	        handle.createQuery("SELECT * FROM cart_item WHERE cart_id = :cartId AND product_id = :productId")
	            .bind("cartId", cartId)
	            .bind("productId", productId)
	            .mapToBean(CartItem.class)
	            .findOne()
	            .orElse(null)
	    );
	}

	
	public boolean updateCartItem(int cartId, int productId, int quantity) {
	    Jdbi jdbi = JDBIConnect.get();
	    int rowsAffected = jdbi.withHandle(handle ->
	        handle.createUpdate("UPDATE cart_item SET quantity = :quantity WHERE cart_id = :cartId AND product_id = :productId")
	              .bind("quantity", quantity)
	              .bind("cartId", cartId)
	              .bind("productId", productId)
	              .execute()
	    );
	    return rowsAffected > 0;
	}
	
	public boolean updateCart(int cartId, double totalPrice, int totalQuantity) {
	    Jdbi jdbi = JDBIConnect.get();
	    int rowsAffected = jdbi.withHandle(handle ->
	        handle.createUpdate("UPDATE cart SET total_amount = :totalQuantity, total_price = :totalPrice WHERE id = :cartId")
	              .bind("totalQuantity", totalQuantity)
	              .bind("totalPrice", totalPrice)
	              .bind("cartId", cartId)
	              .execute()
	    );
	    return rowsAffected > 0;
	}

	
	public boolean addNewCartItem(int cartId, int productId, int quantity) {
	    Jdbi jdbi = JDBIConnect.get();

	    int rowsAffected = jdbi.withHandle(handle ->
	        handle.createUpdate("INSERT INTO cart_item (cart_id, product_id, quantity) VALUES (:cartId, :productId, :quantity)")
	            .bind("cartId", cartId)
	            .bind("productId", productId)
	            .bind("quantity", quantity)
	            .execute()
	    );

	    return rowsAffected > 0;
	}
	
	public List<CartItem> getListCartItemByCartId(int cartId) {
	    Jdbi jdbi = JDBIConnect.get();
	    ProductDao productDao = new ProductDao(); // Dùng để lấy đối tượng Product theo productId
	    String query = "SELECT * FROM cart_item WHERE cart_id = :cartId";

	    return jdbi.withHandle(handle -> 
	        handle.createQuery(query)
	              .bind("cartId", cartId)
	              .map((rs, ctx) -> {
	                  CartItem item = new CartItem();
	                  item.setId(rs.getInt("id"));
	                  item.setCartId(rs.getInt("cart_id"));
	                  // Lấy product id từ result set
	                  int prodId = rs.getInt("product_id");
	                  item.setQuantity(rs.getInt("quantity"));

	                  // Lấy đối tượng Product từ product id
	                  Product product = productDao.getById(prodId);
	                  item.setProduct(product);
	                  return item;
	              })
	              .list()
	    );
	}

	public boolean clearCart(int cartId) {
	    Jdbi jdbi = JDBIConnect.get();
	    
	    try {
	        return jdbi.inTransaction(handle -> {
	            // Xóa tất cả cart items liên quan đến cartId
	            handle.createUpdate("DELETE FROM cart_item WHERE cart_id = :cartId")
	                  .bind("cartId", cartId)
	                  .execute();

	            // Cập nhật cart: đặt total_price và total_amount về 0
	            int rowsAffected = handle.createUpdate(
	                    "UPDATE cart SET total_price = 0, total_amount = 0 WHERE id = :cartId")
	                    .bind("cartId", cartId)
	                    .execute();

	            return rowsAffected > 0;
	        });
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}


}
