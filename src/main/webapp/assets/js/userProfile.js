/**
 * 
 */
document.addEventListener("DOMContentLoaded", function () {
	const editBtn = document.getElementById("editBtn");
	const submitBtn = document.getElementById("submitBtn");
	const formInputs = document.querySelectorAll("#userForm input");
	const messageDiv = document.getElementById("message");
	const form = document.getElementById("userForm");

	// Khi nhấn "Edit"
	editBtn.addEventListener("click", function () {
		formInputs.forEach(input => input.removeAttribute("readonly"));
		editBtn.style.display = "none"; // Ẩn nút Edit
		submitBtn.style.display = "block"; // Hiện nút Submit
	});

	// Khi nhấn "Submit"
	form.addEventListener("submit", function (event) {
		event.preventDefault(); // Ngăn reload trang

		// Tạo đối tượng FormData
		const formData = new FormData(form);

		// Gửi dữ liệu bằng jQuery AJAX
		$.ajax({
			url: "http://localhost:8080/DoAnLTWeb/UserProfileServlet",
			method: "POST",
			data: formData,
			processData: false,
			contentType: false,
			success: function (data) {
				if (data.success) {
					messageDiv.innerHTML = '<div class="alert alert-success">' + data.message + '</div>';
					formInputs.forEach(input => input.setAttribute("readonly", "true"));
					editBtn.style.display = "block";
					submitBtn.style.display = "none";
				} else {
					messageDiv.innerHTML = '<div class="alert alert-danger">' + data.message + '</div>';
				}
			},
			error: function (xhr, status, error) {
				console.error("Lỗi khi gửi AJAX:", error);
				messageDiv.innerHTML = '<div class="alert alert-danger">Có lỗi xảy ra!</div>';
			}
		});
	});
});



function cancelOrder(orderId) {
	$.ajax({
		url: '/DoAnLTWeb/CancelOrderServlet',
		type: 'POST',
		contentType: "application/x-www-form-urlencoded",
		data: {
			orderId: orderId
		},
		success: function(response) {
	
				// Cập nhật trạng thái đơn hàng
				const elementId = 'status-' + orderId;
				const statusCell = document.getElementById(elementId);

				if (statusCell) {
					statusCell.textContent = "CANCELLED";
				} else {
					console.warn(`Không tìm thấy phần tử với id: ${elementId}`);
				}

				// Ẩn nút hủy
				const cancelButtonId = 'btn-' + orderId;
				const cancelButton = document.getElementById(cancelButtonId);

				if (cancelButton) {
					cancelButton.style.display = 'none';
				}

				alert(response.status);
		},
		error: function(response) {
		           alert(response.status);
		       }
	});
}
function completeOrder(orderId) {
	$.ajax({
		url: '/DoAnLTWeb/OrderController',
		type: 'POST',
		contentType: "application/x-www-form-urlencoded",
		data: {
			orderId: orderId,
			newStatus:"COMPLETED"
		},
		success: function(response) {
	
				// Cập nhật trạng thái đơn hàng
				const elementId = 'status-' + orderId;
				const statusCell = document.getElementById(elementId);

				if (statusCell) {
					statusCell.textContent = "COMPLETED";
				} else {
					console.warn(`Không tìm thấy phần tử với id: ${elementId}`);
				}

				// Ẩn nút hủy
				const completeBtnId = 'btn-complete-' + orderId;
				const completeButton = document.getElementById(completeBtnId);

				if (completeButton) {
					completeButton.style.display = 'none';
				}

				alert(response.status);
		},
		error: function(response) {
		           alert(response.status);
		       }
	});
}