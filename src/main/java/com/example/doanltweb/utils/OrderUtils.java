package com.example.doanltweb.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.doanltweb.dao.OrderDao;
import com.example.doanltweb.dao.model.Order;
import com.example.doanltweb.dao.model.OrderDetail;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.StockIn;

public class OrderUtils {
	OrderDao orderDao = new OrderDao();

	public String genOTP() {
		return"";
	}
	
	public boolean sendMail(String orderInfo) {
		return false;
	}
	
	public void updateStatus(OrderDao orderDao) {
		List<Order> orders = orderDao.getAllOrder(); // hoặc getOrdersByUser()

		for (Order order : orders) {
		    if (order.getStatus().equals("PENDING")) {
		    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		    	String orderDateStr = order.getOrderDate();
		    	LocalDateTime createdAt = LocalDateTime.parse(orderDateStr, formatter);

		    	LocalDateTime now = LocalDateTime.now();

		    	if (createdAt.plusDays(1).isBefore(now)) {
		    	    order.setStatus("CANCELLED");
		    	    orderDao.updateStatus(order.getId(), "CANCELLED");
		    	}


		    }
		}
	}

	public Map<Integer,Integer> orderRecord(){
		List<OrderDetail> details = orderDao.getAllDetail();
		Map<Integer,Integer> orderRecord = new HashMap<Integer,Integer>();
		for (OrderDetail detail : details) {
			Product product = detail.getProduct();
			int currentQty = orderRecord.getOrDefault(product.getId(), 0);
			orderRecord.put(product.getId(), currentQty + detail.getQuantity());
		}
		return orderRecord;
	}
}
