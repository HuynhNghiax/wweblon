<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Document</title>
    <link rel="stylesheet" href="assets/css/style.css"/>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
            rel="stylesheet"/>
    <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          crossorigin="anonymous"/>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="assets/css/Userprofilestyle.css">

    <link rel="stylesheet" href="assets/css/headerAndFooter.css">
</head>

<body style="background-color: rgb(242, 244, 247)">
<header id="header"></header>
<nav id="nav"></nav>

<div class="container mt-5">
    <ul class="nav nav-tabs mt-4" id="myTab" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="profile-tab" data-bs-toggle="tab" data-bs-target="#profile"
                    type="button" role="tab" aria-controls="profile" aria-selected="true">Hồ sơ
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="orders-tab" data-bs-toggle="tab" data-bs-target="#orders"
                    type="button" role="tab" aria-controls="orders" aria-selected="false">Đơn hàng
            </button>
        </li>
    </ul>

    <div class="tab-content" id="myTabContent">


        <!-- Hồ sơ -->
        <div class="tab-pane fade show active" id="profile" role="tabpanel" aria-labelledby="profile-tab">
            <div class="card mt-3">
                <div class="card-body">
                    <h3 class="card-title text-center"><strong>Hồ sơ của bạn</strong></h3>
                    <div class="container mt-5">
                        <div class="row">
                            <div class="col-lg-6 mx-auto">
                                <input type="hidden" id="userId" name="userId" value="${auth.id}">
                                <div class="text-center mb-4">
                                    <div class="avatar mx-auto mb-3">
                                    <img id="avatarImg" src="${auth.avatar}" alt="User Avatar" class="profile-img" width="120" height="120">

                                    </div>

                                </div>
                                <div class="card-body">
                                    <!-- Form chi tiết người dùng -->
                                    <form id="userForm" action="UserProfileServlet"
                                          method="post">
                                        <!-- Input ẩn userId -->
                                        <input type="hidden" id="userId" name="userId" value="${auth.id}">

                                        <!-- Input URL avatar nằm trong form -->
                                        <div class="mb-3" id="avatarInputWrapper" style="display: none;">
                                            <label class="form-label fw-bold">URL ảnh đại diện</label>
                                            <input type="text" class="form-control" name="avatar" id="avatarUrl" value="${auth.avatar}">
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label fw-bold">Họ và tên</label> <input
                                                type="text" class="form-control" name="fullname"
                                                id="fullname" value="${auth.fullname}" readonly>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label fw-bold">Email</label> <input
                                                type="email" class="form-control" name="email" id="email"
                                                value="${auth.email}" readonly>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label fw-bold">Địa chỉ</label> <input
                                                type="text" class="form-control" name="address"
                                                id="address" value="${auth.address}" readonly>
                                        </div>

                                        <div class="mb-3">
                                            <label class="form-label fw-bold">Số điện thoại</label> <input
                                                type="text" class="form-control" name="phone" id="phone"
                                                value="${auth.phone}" readonly>
                                        </div>
                                        <!-- Hiển thị thông báo -->
                                        <div id="message" class="mt-3"></div>
                                        <!-- Nút hành động -->
                                        <div class="d-grid gap-2 mt-4">

                                            <!-- Nút Edit -->

                                            <button type="button" id="editBtn"
                                                    class="btn bg-dark-blue px-4 text-light">Cập nhật thông tin
                                            </button>

                                            <!-- Nút Submit (Ẩn ban đầu) -->
                                            <button type="submit" id="submitBtn"
                                                    class="btn btn-success px-4" style="display: none;">Lưu thông tin
                                            </button>

                                        </div>
                                    </form>

                                    <button
                                            class="btn btn-primary mt-3 w-100" id="togglePasswordFormBtn">Đổi mật khẩu
                                    </button>


                                    <!-- Form đổi mật khẩu (ẩn ban đầu) -->
                                    <div class="card p-4 mt-4" id="changePasswordFormContainer"
                                         style="display: none;">
                                        <h5 class="fw-bold mb-3">Đổi mật khẩu</h5>
                                        <form id="changePasswordForm">
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Mật khẩu hiện tại</label>
                                                <input type="password" class="form-control" name="currentPassword"
                                                       required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Mật khẩu mới</label>
                                                <input type="password" class="form-control" name="newPassword"
                                                       required>
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label fw-bold">Xác nhận mật khẩu mới</label>
                                                <input type="password" class="form-control" name="confirmPassword"
                                                       required>
                                            </div>
                                            <button type="submit" class="btn btn-primary mt-3 w-100">Đổi mật khẩu</button>
                                            <div id="changePasswordMessage" class="mt-3 mb-3"></div>
                                        </form>
                                    </div>

<%--                                    <!-- Hiển thị message -->--%>
                                    <div id="message" class="mt-3"></div>
<%--                                    <!-- Nút logout -->--%>
                                    <a href="LogoutController" class="btn btn-outline-danger w-100">
                                        Đăng xuất
                                    </a>
                                </div>

                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <!-- Đơn hàng -->
        <div class="tab-pane fade" id="orders" role="tabpanel" aria-labelledby="orders-tab">
            <div class="container mt-2">
                <h2 class="mb-2 text-center">Đơn hàng của bạn</h2>
                <div class="table-responsive">
                    <table class="table table-bordered text-center align-middle">
                        <thead class="table-light">
                        <tr>
                            <th class="bg-dark-blue text-light">Ngày đặt hàng</th>
                            <th class="bg-dark-blue text-light">Tổng số lượng</th>
                            <th class="bg-dark-blue text-light">Tổng tiền</th>
                            <th class="bg-dark-blue text-light">Trạng thái</th>
                            <th class="bg-dark-blue text-light">Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="entry" items="${orderMap}">
                            <c:set var="order" value="${entry.key}"/>
                            <c:set var="details" value="${entry.value}"/>
                            <tr style="cursor: pointer;" class="table-row-main">
                                <td>${order.orderDate}</td>
<%--                                <td onclick="toggleDetails(${order.id})">${order.user.fullname}</td>--%>
                                <td onclick="toggleDetails(${order.id})">${order.quantity}</td>
                                <td onclick="toggleDetails(${order.id})">${order.totalPrice}</td>
                                <td onclick="toggleDetails(${order.id})" data-order-id="${order.id}">
                                    <span id="status-${order.id}" class="badge text-dark">${order.status}</span>
                                </td>
                                <td>
                                    <c:if test="${order.status == 'PENDING' || order.status == 'VERIFIED'}">
                                        <button id="btn-${order.id}" class="btn btn-danger btn-sm"
                                                onclick="cancelOrder(${order.id})">Hủy đơn
                                        </button>
                                    </c:if>
                                    <c:if test="${order.status == 'PENDING'}">
                                        <button id="btn-confirm-${order.id}" onclick="showOtpModal(${order.id})"
                                                class="btn btn-success btn-sm">Xác nhận
                                        </button>
                                    </c:if>
                                    <c:if test="${order.status != 'PENDING'&&order.status != 'COMPLETED'}">
                                        <button id="btn-complete-${order.id}" onclick="completeOrder(${order.id})"
                                                class="btn btn-success btn-sm">Đã nhận hàng
                                        </button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <!-- Modal OTP -->
                    <div class="modal fade" id="otpModal" tabindex="-1" aria-labelledby="otpModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="otpModalLabel">Xác nhận đơn hàng</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Đóng"></button>
                                </div>
                                <div class="modal-body">
                                    <form id="verifyOtpForm">
                                        <input type="hidden" class="form-control" name="orderId" id="orderId">
                                        <div class="mb-3">
                                            <label for="otp" class="form-label">Nhập OTP:</label>
                                            <input type="text" class="form-control" id="otp" name="otp" required>
                                        </div>
                                        <div id="verifyMessage" class="mt-2"></div>
                                        <div class="d-grid gap-2 mt-3">
                                            <button type="submit" class="btn btn-primary">Xác nhận</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <footer id="footer2"></footer>
</div>

<footer id="footer"></footer>
<script>
    const header = document.getElementById("header");
    const footer = document.getElementById("footer");
    const footer2 = document.getElementById("footer2");
    const nav = document.getElementById("nav");
    fetch("./assets/component/header.jsp")
        .then((response) => response.text())
        .then((html) => (header.innerHTML = html));
    fetch("./assets/component/footer.jsp")
        .then((response) => response.text())
        .then((html) => (footer.innerHTML = html));
    fetch("./assets/component/footer2.jsp")
        .then((response) => response.text())
        .then((html) => (footer2.innerHTML = html));
    fetch("./assets/component/nav.jsp")
        .then((response) => response.text())
        .then((html) => (nav.innerHTML = html));
</script>
<script>
    document.getElementById("togglePasswordFormBtn").addEventListener("click", function () {
        const formContainer = document.getElementById("changePasswordFormContainer");
        if (formContainer.style.display === "none") {
            formContainer.style.display = "block";
            this.textContent = "Ẩn đổi mật khẩu";
        } else {
            formContainer.style.display = "none";
            this.textContent = "Đổi mật khẩu";
        }
    });
</script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const editBtn = document.getElementById("editBtn");
        const submitBtn = document.getElementById("submitBtn");
        const avatarInputWrapper = document.getElementById("avatarInputWrapper");
        const avatarUrlInput = document.getElementById("avatarUrl");
        const avatarImg = document.getElementById("avatarImg");

        if (editBtn) {
            editBtn.addEventListener("click", function () {
                // Hiển thị nút Submit
                if (submitBtn) submitBtn.style.display = "inline-block";

                // Bật các input
                ["fullname", "email", "address", "phone"].forEach(function (id) {
                    const input = document.getElementById(id);
                    if (input) input.removeAttribute("readonly");
                });

                // Hiển thị input URL avatar
                if (avatarInputWrapper) avatarInputWrapper.style.display = "block";
            });
        }

        // Xem trước ảnh đại diện khi thay đổi URL
        if (avatarUrlInput && avatarImg) {
            avatarUrlInput.addEventListener("input", function () {
                avatarImg.src = this.value;
            });
        }
    });
</script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const editBtn = document.getElementById("editBtn");
        const submitBtn = document.getElementById("submitBtn");
        const formInputs = document.querySelectorAll("#userForm input");
        const messageDiv = document.getElementById("message");
        const form = document.getElementById("userForm");

        // Khi nhấn "Edit"
        editBtn.addEventListener("click", function () {
            formInputs.forEach(input => input.removeAttribute("readonly"));
            editBtn.style.display = "none";   // Ẩn nút Edit
            submitBtn.style.display = "block"; // Hiện nút Submit
        });

        // Khi nhấn "Submit"
        form.addEventListener("submit", function (event) {
            event.preventDefault(); // Ngăn chặn reload trang

            // Lấy dữ liệu từ form
            const formData = new FormData(form);

            // Gửi dữ liệu qua AJAX
            fetch("http://localhost:8080/DoAnLTWeb/UserProfileServlet", {
                method: "POST",
                body: formData
            })
                .then(response => response.json()) // Đọc phản hồi JSON từ server
                .then(data => {
                    if (data.success) {
                        messageDiv.innerHTML = '<div class="alert alert-success">' + data.message + '</div>';

                        // Chuyển form về trạng thái chỉ đọc
                        formInputs.forEach(input => input.setAttribute("readonly", "true"));
                        editBtn.style.display = "block";
                        submitBtn.style.display = "none";
                    } else {
                        messageDiv.innerHTML = '<div class="alert alert-danger">' + data.message + '</div>';
                    }
                })
                .catch(error => {
                    console.error("Lỗi khi gửi AJAX:", error);
                    messageDiv.innerHTML = `<div class="alert alert-danger">Có lỗi xảy ra!</div>`;
                });
        });
    });
</script>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const changePasswordForm = document.getElementById("changePasswordForm");
        const changePasswordMessage = document.getElementById("changePasswordMessage");
        const messageDiv = document.getElementById("changePasswordMessage");
        if (changePasswordForm) {
            changePasswordForm.addEventListener("submit", function (event) {
                event.preventDefault();

                const formData = new FormData(changePasswordForm);

                fetch("http://localhost:8080/DoAnLTWeb/UserProfileServlet?action=changePassword", {
                    method: "POST",
                    body: formData
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log("DATA:", data);
                        if (data.success) {
                            messageDiv.innerHTML = '<div class="alert alert-success">' + data.message + '</div>';

                        } else {
                            messageDiv.innerHTML = '<div class="alert alert-danger">' + data.message + '</div>';
                        }
                        changePasswordForm.reset();
                    })
                    .catch(error => {
                        console.error("Lỗi:", error);
                        changePasswordMessage.innerHTML = `<div class="alert alert-danger">Có lỗi xảy ra!</div>`;
                    });
            });
        }
    });
</script>


<script src="assets/js/nav.js"></script>
<script src="assets/js/userProfile.js"></script>
<script>
    function toggleDetails(orderId) {
        let detailsRow = document.getElementById("orderDetails-" + orderId);
        if (detailsRow) {
            detailsRow.classList.toggle("show");
        } else {
            console.error("Không tìm thấy phần tử orderDetails" + orderId);
        }
    }

    function showOtpModal(orderId) {
        $('#otpModal').modal('show');
        $('#otpModalLabel').text('Xác nhận đơn hàng #' + orderId); // Đổi title cho đẹp
        $('#orderId').val(orderId); // Gán orderId vào hidden input
    }


    function closeForm(orderId) {
        // Đóng modal OTP tương ứng với mỗi đơn hàng
        $('#otpModal-' + orderId).modal('hide');
    }

</script>
<script>
    $(document).ready(function () {
        $('#verifyOtpForm').submit(function (event) {
            event.preventDefault();

            var orderId = $('#orderId').val();
            var otp = $('#otp').val();
            var messageDiv = $('#verifyMessage');

            $.ajax({
                url: '/DoAnLTWeb/VerifyOrderServlet',
                type: 'POST',
                data: {
                    orderId: orderId,
                    otp: otp
                },
                success: function (data) {
                    if (typeof data === 'string') {
                        data = JSON.parse(data);
                    }
                    if (data.success) {
                        messageDiv.html('<div class="alert alert-success">' + data.message + '</div>');
                        setTimeout(function () {
                            $('#otpModal').modal('hide');
                            messageDiv.html('');
                        }, 1000);
                        // Cập nhật trạng thái đơn hàng
                        const elementId = 'status-' + orderId;
                        const statusCell = document.getElementById(elementId);

                        if (statusCell) {
                            statusCell.textContent = "VERIFIED";
                            $('#btn-confirm-' + orderId).hide();
                        } else {
                            console.warn(`Không tìm thấy phần tử với id: ${elementId}`);
                        }
                    } else {
                        messageDiv.html('<div class="alert alert-danger">' + data.message + '</div>');
                    }
                },
                error: function () {
                    messageDiv.html('<div class="alert alert-danger">Lỗi kết nối server!</div>');
                }
            });
        });
    });

</script>
</body>
</html>
