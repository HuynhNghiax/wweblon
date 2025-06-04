package com.example.doanltweb.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.Map;


//import org.apache.commons.fileupload.servlet.ServletFileUpload;


import com.example.doanltweb.dao.OrderDao;
import com.example.doanltweb.dao.UserDao;
import com.example.doanltweb.dao.model.Order;
import com.example.doanltweb.dao.model.OrderDetail;
import com.example.doanltweb.dao.model.User;
import com.google.gson.*;
import org.json.JSONObject;

@MultipartConfig
public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Xử lý các yêu cầu GET nếu có (chẳng hạn hiển thị thông tin người dùng)
		HttpSession session = request.getSession();

    User user = (User) session.getAttribute("auth");
		OrderDao orderDao = new OrderDao();

		Map<Order,List<OrderDetail>> orders = orderDao.getOrderWithDetails(user.getId());
		request.setAttribute("orderMap", orders);
		System.out.println(orders);
		request.getRequestDispatcher("Userprofile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set Content-Type là JSON
		String action = request.getParameter("action");

		if ("changePassword".equals(action)) {
			handleChangePassword(request, response);
		} else {

			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			JsonObject jsonResponse = new JsonObject();
			Gson gson = new Gson();

			// Lấy thông tin người dùng từ session
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("auth");

			try {
				// Lấy các tham số từ request
				String fullname = request.getParameter("fullname");
				String email = request.getParameter("email");
				String address = request.getParameter("address");
				String phone = request.getParameter("phone");
				String avatar = request.getParameter("avatar");

				// Kiểm tra nếu có tham số bị thiếu

				if (fullname == null || email == null || address == null || phone == null || avatar == null) {
					jsonResponse.addProperty("success", false);
					jsonResponse.addProperty("message", "Các trường thông tin không thể để trống!");
					out.print(gson.toJson(jsonResponse));
					return;
				}

				// Cập nhật thông tin người dùng
				UserDao userDao = new UserDao();
				boolean isUpdated = userDao.updateUser(fullname, email, address, phone, avatar, user.getId());

				if (isUpdated) {
					// Cập nhật lại đối tượng user trong session
					user.setFullname(fullname);
					user.setEmail(email);
					user.setAddress(address);
					user.setPhone(phone);
					user.setAvatar(avatar);
					session.setAttribute("auth", user); // Cập nhật lại session
				}

				// Trả về kết quả
				jsonResponse.addProperty("success", isUpdated);
				jsonResponse.addProperty("message", isUpdated ? "Cập nhật thành công!" : "Cập nhật thất bại, thử lại sau!");

			} catch (Exception e) {
				e.printStackTrace();
				jsonResponse.addProperty("success", false);
				jsonResponse.addProperty("message", "Lỗi xử lý dữ liệu!");
			}

			// Chuyển đổi phản hồi thành JSON và trả về client
			String jsonString = gson.toJson(jsonResponse);
			System.out.println("JSON Response: " + jsonString);
			out.print(jsonString);
			out.flush();
		}
	}

	private void handleChangePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		User auth = (User) session.getAttribute("auth");

		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();

		// Giả sử bạn có UserDAO để truy cập dữ liệu người dùng
		UserDao userDAO = new UserDao();

		if (!newPassword.equals(confirmPassword)) {
			json.put("success", false);
			json.put("message", "Mật khẩu mới và xác nhận không khớp.");
		} else if (!userDAO.checkPassword(auth.getId(), currentPassword)) {
			json.put("success", false);
			json.put("message", "Mật khẩu hiện tại không đúng.");
		} else {
			boolean updated = userDAO.updatePassword(auth.getId(), newPassword);
			if (updated) {
				json.put("success", true);
				json.put("message", "Đổi mật khẩu thành công!");
			} else {
				json.put("success", false);
				json.put("message", "Đổi mật khẩu thất bại.");
			}
		}

		out.print(json.toString());
		out.flush();
	}
}


