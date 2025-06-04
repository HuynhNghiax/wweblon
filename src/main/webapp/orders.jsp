<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Document</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
	crossorigin="anonymous" />
<link rel="icon" type="image/x-icon" href="/favicon.ico" />
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<!-- Custom CSS -->
<link rel="stylesheet" href="assets/css/admin.css" />

</head>

<body>
	<div class="d-flex" id="wrapper">
		<div id="nav" class="col-2"></div>
		<div class="container mt-4 col-10">
			<div class="d-flex justify-content-between align-items-center mb-4">
				<h2 class="mb-0">Danh sách đơn hàng</h2>

				<div class="d-flex gap-2">
					<!-- Nút lọc -->
					<button class="btn btn-outline-primary" id="filterBtn" data-bs-toggle="modal" data-bs-target="#filterModal">
						<i class="bi bi-funnel"></i> Lọc
					</button>

					<!-- Chọn số dòng mỗi trang -->
					<select class="form-select" id="limitSelect" style="width: auto;">
						<option value="5"  ${param.limit == '5' ? 'selected' : ''} selected>5 dòng/trang</option>
						<option value="10" ${param.limit == '10' ? 'selected' : ''}>10 dòng/trang</option>						
						<option value="15" ${param.limit == '15' ? 'selected' : ''}>15 dòng/trang</option>						
						<option value="20" ${param.limit == '20' ? 'selected' : ''}>20 dòng/trang</option>											
					</select>
				</div>
			</div>

			<div class="table-responsive">
				<table
					class="table table-bordered table-hover table-striped text-center align-middle border-dark shadow-sm rounded">
					<thead class="bg-dark-blue">
						<tr>
							<th class="text-light">Mã đơn hàng</th>
							<th class="text-light">Ngày đặt hàng</th>
							<th class="text-light">Thanh toán</th>
							<th class="text-light">Tổng số lượng</th>
							<th class="text-light">Tổng tiền</th>
							<th class="text-light">Trạng thái</th>
							<th class="text-light">Hành động</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entry" items="${orderMap}">
							<c:set var="order" value="${entry.key}" />
							<c:set var="details" value="${entry.value}" />

							<tr style="cursor: pointer;" class="table-row-main">
								<td>${order.id}</td>
								<td>${order.orderDate} </td>
								<td onclick="toggleDetails(${order.id})">${order.getPaymentMethod().getName()}</td>
								<td onclick="toggleDetails(${order.id})">${order.quantity}</td>
								<td onclick="toggleDetails(${order.id})">${order.totalPrice}</td>
								<td onclick="toggleDetails(${order.id})" id="status-${order.id}"
									data-order-id="${order.id}"><span class="badge text-dark">
										${order.status} </span></td>
								<td><c:if
										test="${order.status != 'PENDING' && order.status != 'CANCELLED' && order.status != 'COMPLETED'}">
										<button class="btn btn-primary btn-sm updateStatusBtn"
											data-order-id="${order.id}"
											data-current-status="${order.status}" data-bs-toggle="modal"
											data-bs-target="#updateStatusModal">Cập nhật</button>
									</c:if></td>
							</tr>

							<!-- Chi tiết đơn hàng -->
							<tr class="bg-light collapse" id="orderDetails-${order.id}">
								<td colspan="7" class="p-0 border-0">
									<table class="table table-sm table-bordered m-0 w-100">
										<tbody>
											<tr class="table-info">
												<td colspan="4"><strong>Khách hàng:</strong>
													${order.user.getFullname()} <br> <strong>Email:</strong>
													${order.user.email} <br> <strong>Địa chỉ:</strong>
													${order.user.address} <br> <strong>Số điện
														thoại:</strong> ${order.user.phone}</td>
											</tr>
											<c:forEach var="detail" items="${details}">
												<tr>
													<td class="text-center align-middle"><img
														src="${detail.product.image}" class="img-thumbnail"
														style="width: 80px;"></td>
													<td class="align-middle">${detail.product.nameProduct}</td>
													<td class="align-middle">${detail.quantity}</td>
													<td class="align-middle">${detail.price}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</td>
							</tr>

						</c:forEach>
					</tbody>
					<!-- Modal cập nhật trạng thái -->
					<div class="modal fade" id="updateStatusModal" tabindex="-1"
						aria-labelledby="updateStatusModalLabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<form method="post" id="updateStatusForm">
									<div class="modal-header">
										<h5 class="modal-title" id="updateStatusModalLabel">Cập
											nhật trạng thái đơn hàng</h5>
										<button type="button" class="btn-close"
											data-bs-dismiss="modal" aria-label="Đóng"></button>
									</div>
									<div class="modal-body">
										<input type="hidden" name="orderId" id="modalOrderId" />
										<div class="mb-3">
											<select class="form-select" name="newStatus"
												id="statusSelect">
												<!-- Option sẽ được thêm bằng JavaScript -->
											</select>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-bs-dismiss="modal" onclick="closeStatusModal()">Hủy</button>
										<button type="submit" class="btn btn-primary">Lưu</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</table>
				<nav aria-label="Page navigation" class="mt-4">
					<ul class="pagination justify-content-center">
						<c:if test="${currentPage > 1}">
							<li class="page-item"><a class="page-link"
								href="?page=${currentPage - 1}&limit=${limit}">&laquo;</a></li>
						</c:if>

						<c:forEach var="i" begin="1" end="${totalPages}">
							<li class="page-item ${i == currentPage ? 'active' : ''}"><a
								class="page-link" href="?page=${i}&limit=${limit}">${i}</a></li>
						</c:forEach>

						<c:if test="${currentPage < totalPages}">
							<li class="page-item"><a class="page-link"
								href="?page=${currentPage + 1}&limit=${limit}">&raquo;</a></li>
						</c:if>
					</ul>
				</nav>

				<!-- Modal lọc đơn hàng -->
				<div class="modal fade" id="filterModal" tabindex="-1"
					aria-labelledby="filterModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<form method="GET" class="modal-content" action = "/DoAnLTWeb/OrderFilterController">
							<div class="modal-header">
								<h5 class="modal-title" id="filterModalLabel">Lọc đơn hàng</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Đóng"></button>
							</div>
							<div class="modal-body">
								<!-- Trạng thái đơn hàng -->
								<div class="mb-3">
									<label for="status" class="form-label">Trạng thái đơn
										hàng</label> <select class="form-select" id="status" name="status">
										<option value="">Tất cả</option>
										<option value="PENDING">Chờ xử lý</option>
										<option value="COMPLETED">Hoàn thành</option>
										<option value="SHIPPED">Đã giao hàng</option>
										<option value="CANCELLED">Đã hủy</option>
									</select>
								</div>

								<!-- Ngày tạo đơn -->
								<div class="mb-3">
									<label for="fromDate" class="form-label">Từ ngày</label> <input
										type="date" class="form-control" id="fromDate" name="fromDate">
								</div>
								<div class="mb-3">
									<label for="toDate" class="form-label">Đến ngày</label> <input
										type="date" class="form-control" id="toDate" name="toDate">
								</div>

								<!-- Phương thức thanh toán -->
								<div class="mb-3">
									<label for="paymentMethod" class="form-label">Phương
										thức thanh toán</label> <select class="form-select" id="paymentMethod"
										name="paymentMethod">
										<option value="">Tất cả</option>
										<option value="1">Thanh toán khi nhận hàng</option>
										<option value="2">Chuyển khoản</option>
									</select>
								</div>
							</div>
							<div class="modal-footer">
								<button type="submit" class="btn btn-primary">Áp dụng
									lọc</button>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
	</div>

	<script>
        const nav = document.getElementById("nav");
        fetch("./assets/component/adminNav.jsp")
            .then((response) => response.text())
            .then((html) => (nav.innerHTML = html));

		function toggleDetails(orderId) {
			let detailsRow = document.getElementById("orderDetails-" + orderId);
			if (detailsRow) {
				detailsRow.classList.toggle("show");
			} else {
				console.error("Không tìm thấy phần tử orderDetails" + orderId);
			}
		}
		document.addEventListener("DOMContentLoaded", function () {
			  const forms = document.querySelectorAll("#updateStatusForm"); // Trả về NodeList

			  forms.forEach(form => { // forEach đúng cú pháp
			    form.addEventListener("submit", function (event) {
			      event.preventDefault(); // Ngăn chặn reload trang

			      var formData = new FormData(form); // Lấy dữ liệu từ form

			      $.ajax({
			        url: '/DoAnLTWeb/OrderController',
			        method: 'POST',
			        data: formData,
			        processData: false,
			        contentType: false,
			        success: function (data) {
			          try {
			            let jsonData = (typeof data === "string") ? JSON.parse(data) : data;

			            if (jsonData.status === "success") {
			              alert(jsonData.message);

			              const elementId = 'status-' + jsonData.orderId;
			              const statusCell = document.getElementById(elementId);
			              if (statusCell) {
			            		const span = statusCell.querySelector('span');
			            		span.textContent = jsonData.newStatus;

			              }


			              // Đóng modal nếu đang mở
			              const modal = bootstrap.Modal.getInstance(document.getElementById('updateStatusModal'));
			              if (modal) modal.hide();

			            } else {
			              alert(jsonData.message);
			            }
			          } catch (error) {
			            console.error('Lỗi khi chuyển đổi JSON:', error);
			          }
			        },
			        error: function (xhr, status, error) {
			          alert("Có lỗi xảy ra, vui lòng thử lại!");
			          console.error("Lỗi:", error);
			        }
			      });
			    });
			  });
			});

document.querySelectorAll('.updateStatusBtn').forEach(button => {
  button.addEventListener('click', function () {
    const orderId = this.getAttribute('data-order-id');
    const currentStatus = this.getAttribute('data-current-status');
    document.getElementById('modalOrderId').value = orderId;

    const statusSelect = document.getElementById('statusSelect');
    statusSelect.innerHTML = ''; // Xóa các option cũ

    if (currentStatus === 'PENDING') {
      const cancelOption = new Option('Hủy đơn', 'CANCELLED');
      statusSelect.add(cancelOption);
    } else {
      const options = [
        { text: 'Đang xử lý', value: 'PROCESSING' },
        { text: 'Đã giao hàng', value: 'SHIPPED' },
        { text: 'Hoàn thành', value: 'COMPLETED' }
      ];
      options.forEach(opt => {
        const option = new Option(opt.text, opt.value);
        statusSelect.add(option);
      });
    }
  });
});

document.getElementById('limitSelect').addEventListener('change', function () {
    const selectedLimit = this.value;
    const params = new URLSearchParams(window.location.search);
    params.set("limit", selectedLimit);
    params.set("page", 1); // quay về trang đầu khi thay đổi limit
    window.location.href = window.location.pathname + "?" + params.toString();
});

</script>
</body>
</html>
