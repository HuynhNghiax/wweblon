package com.example.doanltweb.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.doanltweb.dao.CartDao;
import com.example.doanltweb.dao.model.Cart;
import com.example.doanltweb.dao.model.CartItem;

import jakarta.servlet.http.HttpSession;

public class CartUtils {
    public static void mergeSessionCartToDb(int userId, HttpSession session) {
        List<CartItem> sessionCart = (List<CartItem>) session.getAttribute("cart");
        CartDao cartDao = new CartDao();
        Cart cart = cartDao.getCartByUserId(userId);
        if(cart==null) {
        	cart = cartDao.createCart(userId);
        	System.out.println(cart);
        }
        
        if (sessionCart == null || sessionCart.isEmpty()) {
        	List<CartItem> newCart = cartDao.getListCartItemByCartId(cart.getId());
        	int amount =0;
     		double price =0;
     		 for (CartItem cartItem : newCart) {
     			amount+= cartItem.getQuantity();
     			price+= cartItem.getQuantity()*cartItem.getProduct().getPriceProduct();
     		}
     		boolean update =cartDao.updateCart(cart.getId(), price, amount);
        	session.setAttribute("TotalAmount", amount);
    	    session.setAttribute("TotalPrice", price);
        	session.setAttribute("cart", newCart);
            return;
        }



        List<CartItem> dbCart = cartDao.getListCartItemByCartId(cart.getId());

        for (CartItem item : sessionCart) {
            Optional<CartItem> existingItem = dbCart.stream()
                .filter(dbItem -> dbItem.getProduct().getId() == item.getProduct().getId())
                .findFirst();

            if (existingItem.isPresent()) {
                // Nếu sản phẩm đã tồn tại, cập nhật số lượng mới
                CartItem dbItem = existingItem.get();

                int newQuantity = dbItem.getQuantity() == item.getQuantity()?dbItem.getQuantity():dbItem.getQuantity() + item.getQuantity();
                
                cartDao.updateCartItem(cart.getId(), item.getProduct().getId(), newQuantity);
            } else {
                // Nếu sản phẩm chưa có, thêm vào DB
                cartDao.addNewCartItem(cart.getId(), item.getProduct().getId(), item.getQuantity());
            }
        }
        
        List<CartItem> newCart = cartDao.getListCartItemByCartId(cart.getId());
        int amount =0;
		double price =0;
        for (CartItem cartItem : newCart) {
			amount+= cartItem.getQuantity();
			price+= cartItem.getQuantity()*cartItem.getProduct().getPriceProduct();
		}
        cartDao.updateCart(userId, price, amount);
        // Cập nhật giỏ hàng trong session
        session.setAttribute("TotalAmount", amount);
	    session.setAttribute("TotalPrice", price);
        session.setAttribute("cart", newCart);
       

    }
}
