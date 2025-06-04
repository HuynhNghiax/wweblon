package com.example.doanltweb.dao;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.jdbi.v3.core.Jdbi;

import com.example.doanltweb.dao.db.JDBIConnect;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.StockIn;

public class StockInDao {
	ProductDao productDao;
	
	public boolean newStockIn(int quantity ,Product product) {
	    Jdbi jdbi = JDBIConnect.get();
	    
	    // Sử dụng createUpdate để thực hiện INSERT
	    int rowsAffected = jdbi.withHandle(handle -> 
	        handle.createUpdate("INSERT INTO stock_in (productId, quantity, stock_date) VALUES (:prodId, :quantity, :date)")
	        	.bind("prodId", product.getId())
	            .bind("quantity", quantity)      
	            .bind("date",  LocalDateTime.now()) 
	            .execute()  // Thực thi câu lệnh SQL
	    );

	    // Trả về true nếu ít nhất một dòng đã bị ảnh hưởng
	    return rowsAffected > 0;
	}

	public List<StockIn> getAllRecord() {
	    Jdbi jdbi = JDBIConnect.get();
	    productDao = new ProductDao();
	    return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM stock_in")
	            .map((rs, ctx) -> {
	                int id = rs.getInt("id");
	                int prodId = rs.getInt("productId");
	                int quantity = rs.getInt("quantity");
	                String date =  rs.getDate("stock_date").toString();	                
	                Product product = productDao.getById(prodId);              
	                StockIn stockIn = new StockIn(id, date, product, quantity);
	                return stockIn;
	            })
	            .list());
	}
}
