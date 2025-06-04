package com.example.doanltweb.controller;

import com.example.doanltweb.service.EmailService;
import com.example.doanltweb.utils.Config;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.doanltweb.dao.CartDao;
import com.example.doanltweb.dao.OrderDao;
import com.example.doanltweb.dao.model.Cart;
import com.example.doanltweb.dao.model.CartItem;
import com.example.doanltweb.dao.model.User;
import com.example.doanltweb.utils.CartUtils;

@MultipartConfig
@WebServlet(name = "checkout", value = "/checkout")
public class CheckoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static String generateOTP() {
		Random random = new Random();
		StringBuilder otp = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			otp.append(random.nextInt(10));
		}
		return otp.toString();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("thanhtoan.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("auth");

		// Kiểm tra đăng nhập
		if (user == null) {
			request.setAttribute("message", "Vui lòng đăng nhập để thanh toán.");
			request.getRequestDispatcher("thanhtoan.jsp").forward(request, response);
			return;
		}

		CartDao cartDao = new CartDao();
		OrderDao orderDao = new OrderDao();

		// Merge giỏ hàng từ session vào DB
		CartUtils.mergeSessionCartToDb(user.getId(), session);
		Cart cart = cartDao.getCartByUserId(user.getId());

		// Kiểm tra giỏ hàng trống
		if (cart == null || cart.getTotalAmount() == 0) {
			request.setAttribute("message", "Giỏ hàng của bạn đang trống.");
			request.getRequestDispatcher("thanhtoan.jsp").forward(request, response);
			return;
		}

		// Lấy thông tin mã giảm giá (nếu có)
		String voucherType = request.getParameter("voucherType");
		String voucherValueStr = request.getParameter("voucherValue");

		double totalPrice = cart.getTotalPrice();

		if (voucherType != null && voucherValueStr != null) {
			try {
				double voucherValue = Double.parseDouble(voucherValueStr);
				if ("percent".equals(voucherType)) {
					totalPrice -= totalPrice * voucherValue / 100;
				} else if ("amount".equals(voucherType)) {
					totalPrice -= voucherValue;
				}
				if (totalPrice < 0) {
					totalPrice = 0;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		String paymentMethodParam = request.getParameter("paymentMethod");
		int paymentMethod = (paymentMethodParam != null) ? Integer.parseInt(paymentMethodParam) : 1;
		String bankCode = request.getParameter("bankCode");

		String otp = generateOTP();

		// Trường hợp thanh toán VNPAY
		if (paymentMethod == 2) {
			if (cart == null || cart.getTotalAmount() == 0) {
				request.setAttribute("message", "Giỏ hàng của bạn đang trống.");
				request.getRequestDispatcher("thanhtoan.jsp").forward(request, response);
				return;
			}

			int orderId = orderDao.createOrderNoOtp(user.getId(), totalPrice, paymentMethod, cart.getTotalAmount(), cart.getId());
			if (orderId < 1) {
				request.setAttribute("message", "Tạo đơn hàng thất bại, vui lòng thử lại.");
				request.getRequestDispatcher("thanhtoan.jsp").forward(request, response);
				return;
			}

			// Xóa voucher đã dùng
			session.removeAttribute("VoucherApplied");
			session.removeAttribute("TotalPrice");

			cartDao.clearCart(cart.getId());
			session.setAttribute("cart", new ArrayList<CartItem>());

			// Tạo tham số VNPAY
			String vnp_Version = "2.1.0";
			String vnp_Command = "pay";
			String orderType = "other";
			long amount = (long) (totalPrice * 100);
			String vnp_TxnRef = String.valueOf(orderId);
			String vnp_IpAddr = Config.getIpAddress(request);
			String vnp_TmnCode = Config.vnp_TmnCode;

			Map<String, String> vnp_Params = new HashMap<>();
			vnp_Params.put("vnp_Version", vnp_Version);
			vnp_Params.put("vnp_Command", vnp_Command);
			vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
			vnp_Params.put("vnp_Amount", String.valueOf(amount));
			vnp_Params.put("vnp_CurrCode", "VND");
			vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
			vnp_Params.put("vnp_OrderInfo", "Thanh toán đơn hàng: " + vnp_TxnRef);
			vnp_Params.put("vnp_OrderType", orderType);
			vnp_Params.put("vnp_Locale", request.getParameter("language") != null ? request.getParameter("language") : "vn");
			vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
			vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
			if (bankCode != null && !bankCode.isEmpty()) {
				vnp_Params.put("vnp_BankCode", bankCode);
			}

			Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String vnp_CreateDate = formatter.format(cld.getTime());
			vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
			cld.add(Calendar.MINUTE, 15);
			String vnp_ExpireDate = formatter.format(cld.getTime());
			vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

			List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
			Collections.sort(fieldNames);
			StringBuilder hashData = new StringBuilder();
			StringBuilder query = new StringBuilder();
			for (int i = 0; i < fieldNames.size(); i++) {
				String fieldName = fieldNames.get(i);
				String fieldValue = vnp_Params.get(fieldName);
				if (fieldValue != null && !fieldValue.isEmpty()) {
					hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					if (i < fieldNames.size() - 1) {
						hashData.append('&');
						query.append('&');
					}
				}
			}
			String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
			query.append("&vnp_SecureHash=").append(vnp_SecureHash);
			String paymentUrl = Config.vnp_PayUrl + "?" + query.toString();

			response.sendRedirect(paymentUrl);
			return;
		} else {
			// Thanh toán COD hoặc các hình thức khác
			boolean order = orderDao.createOrder(user.getId(), totalPrice, paymentMethod, cart.getTotalAmount(), cart.getId(), otp);
			if (order) {
				cartDao.clearCart(cart.getId());
				session.setAttribute("cart", new ArrayList<CartItem>());
				session.removeAttribute("VoucherApplied");
				session.removeAttribute("TotalPrice");

				new Thread(() -> EmailService.sendOTP(user.getEmail(), otp)).start();

				request.setAttribute("message", "Đặt hàng thành công! Mã OTP đã được gửi vào email.");
				request.getRequestDispatcher("thanhtoan.jsp").forward(request, response);
				return;
			} else {
				request.setAttribute("message", "Đặt hàng thất bại, vui lòng thử lại.");
				request.getRequestDispatcher("thanhtoan.jsp").forward(request, response);
				return;
			}
		}
	}
}
