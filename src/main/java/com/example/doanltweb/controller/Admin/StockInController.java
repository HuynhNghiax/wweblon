package com.example.doanltweb.controller.Admin;

import com.example.doanltweb.controller.CancelOrderServlet;
import com.example.doanltweb.dao.model.User;
import com.example.doanltweb.utils.OrderUtils;
import com.example.doanltweb.utils.StockInUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.doanltweb.dao.ProductDao;
import com.example.doanltweb.dao.StockInDao;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.StockIn;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

@MultipartConfig
@WebServlet("/StockInController")
public class StockInController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StockInDao stockInDao = new StockInDao();
    ProductDao productDao = new ProductDao();   
	StockInUtils stockInUtils = new StockInUtils();
	OrderUtils orderUtils = new OrderUtils();
	private static final Logger logger = LogManager.getLogger(StockInController.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<StockIn> records = stockInDao.getAllRecord();
		Map<Integer,Integer> stockInMap = stockInUtils.stockInRecord();
		Map<Integer,Integer> detailMap = orderUtils.orderRecord();
		Map<Product, Integer> remainingMap = new HashMap<>();
		for (Map.Entry<Integer, Integer> entry : stockInMap.entrySet()) {
			Product product = productDao.getById(entry.getKey());
			int totalImported = entry.getValue();
			int totalSold = detailMap.getOrDefault(product.getId(), 0);
			int remaining = totalImported - totalSold;

			if (remaining > 0) {
				remainingMap.put(product, remaining);
			}
		}
		request.setAttribute("stockRemainList", remainingMap);
		request.setAttribute("records", records);
		request.getRequestDispatcher("stockIn.jsp").forward(request, response);
	}

	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8"); // đảm bảo nhận tiếng Việt
	    
	    try {
	        // Lấy dữ liệu từ form
	        int productId = Integer.parseInt(request.getParameter("productId"));
	        int quantity = Integer.parseInt(request.getParameter("quantity"));

	        Product product = productDao.getById(productId);
	        boolean s =stockInDao.newStockIn(quantity,product); 

	        response.setStatus(HttpServletResponse.SC_OK); // 200
	        response.getWriter().write("Thành công");
			//log
			String ip = request.getRemoteAddr();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("auth");
			ThreadContext.put("user_id", String.valueOf(user.getId()));
			ThreadContext.put("ip", ip);
			ThreadContext.put("resource", "Stock in");
			ThreadContext.put("data_in", "username=" + user.getUsername());
			ThreadContext.put("data_out", "SUCCESS");

			logger.info("Stock in successful");
	    } catch (Exception e) {
	    	 e.printStackTrace();
	    	 response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
		     response.getWriter().write(e.getMessage());
	    }
	}


}
