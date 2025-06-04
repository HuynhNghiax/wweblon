package com.example.doanltweb.controller.CartController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.example.doanltweb.dao.CartDao;
import com.example.doanltweb.dao.ProductDao;
import com.example.doanltweb.dao.model.Cart;
import com.example.doanltweb.dao.model.CartItem;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.User;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@MultipartConfig
public class CartServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	HttpSession session = request.getSession();
    	CartDao cartDao = new CartDao();
    	User user = (User) session.getAttribute("auth");

    	List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
    	int amount = 0;
    	double price = 0;

    	if (cart == null) {
    	    cart = new ArrayList<CartItem>(); // Khởi tạo giỏ hàng
    	    session.setAttribute("cart", cart); // Lưu vào session
    	} else {
    	    for (CartItem cartItem : cart) {
    	        amount += cartItem.getQuantity();
    	        price += cartItem.getQuantity() * cartItem.getProduct().getPriceProduct();
    	    }
    	}

    	// Nếu người dùng đã đăng nhập thì cập nhật giỏ hàng vào database
    	if (user != null) {
    	    Cart userCart = cartDao.getCartByUserId(user.getId());
    	    if (userCart != null) {
    	        cartDao.updateCart(userCart.getId(), price, amount);
    	    }
    	}

    	// Luôn cập nhật giỏ hàng vào session để hiển thị lên giao diện
    	session.setAttribute("TotalAmount", amount);
    	session.setAttribute("TotalPrice", price);
    	session.setAttribute("cart", cart);

    	// Chuyển đến trang hiển thị giỏ hàng
    	request.getRequestDispatcher("cart.jsp").forward(request, response);

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        CartDao cartDao = new CartDao();
    	User user = (User) session.getAttribute("auth");        
        
        List<CartItem> cart =  (List<CartItem>) session.getAttribute("cart");

        int amount =0;
        double price =0;
        
        
        if (cart == null) {
        	cart = new ArrayList<>(); // 🔥 Khởi tạo giỏ hàng
        	session.setAttribute("cart", cart); // Lưu vào session
        }else {
        	 for (CartItem cartItem : cart) {
        		 if(cartItem.getProduct().getId()==productId) {	
        			 cartItem.setQuantity(quantity);
        		 }
        		 amount+= cartItem.getQuantity();
      			 price += cartItem.getQuantity()*cartItem.getProduct().getPriceProduct();
      		}
		}
        // Nếu người dùng đã đăng nhập thì cập nhật giỏ hàng vào database
    	if (user != null) {
    	    Cart userCart = cartDao.getCartByUserId(user.getId());
    	    if (userCart != null) {
    	        cartDao.updateCart(userCart.getId(), price, amount);
    	    }
    	}
        session.setAttribute("TotalAmount", amount);
        session.setAttribute("TotalPrice", price);
        session.setAttribute("cart", cart);

        // Trả về JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonResponse = "{\"status\":\"success\", \"message\":\"Cập nhật giỏ hàng thành công!\" }";
        response.getWriter().write(jsonResponse);
    }

}
