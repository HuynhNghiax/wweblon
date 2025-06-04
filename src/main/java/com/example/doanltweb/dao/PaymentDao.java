package com.example.doanltweb.dao;

import java.util.HashMap;
import java.util.Map;

import org.jdbi.v3.core.Jdbi;

import com.example.doanltweb.dao.db.JDBIConnect;
import com.example.doanltweb.dao.model.Payment;

public class PaymentDao {
	static Map<Integer, Payment> data = new HashMap<>();

	public Payment getPaymentbyid(int id) {
	    Jdbi jdbi = JDBIConnect.get();
	    return jdbi.withHandle(handle ->
	        handle.createQuery("SELECT * FROM payment WHERE id = :id")
	            .bind("id", id)
	            .map((rs, ctx) -> {
	                Payment payment = new Payment();
	                payment.setId(id);
	                payment.setName(rs.getString("payName"));
	                return payment;
	            })
	            .findOne()
	            .orElse(null)
	    );
	}

	    
}
