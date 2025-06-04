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
public class RemoveFromCartServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		try {

			HttpSession session = request.getSession();
			int productId = Integer.parseInt(request.getParameter("productId"));
			ProductDao productDao = new ProductDao();

			// Kiểm tra giỏ hàng đã tồn tại chưa
			List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
			for (CartItem cartItem : cart) {
				if (cartItem.getProduct().getId() == productId) {
					cart.remove(cartItem);
					break;
				}
			}

			session.setAttribute("cart", cart);

			response.setStatus(HttpServletResponse.SC_OK); // 200
			response.getWriter().write("Thành công");

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
			response.getWriter().write(e.getMessage());
		}
	}

}
