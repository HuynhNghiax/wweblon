<%@ page import="com.example.doanltweb.dao.model.CartItem" %>
<%@ page import="com.example.doanltweb.dao.model.Cart" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Document</title>
    <link rel="stylesheet" href="assets/css/style.css"/>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
            rel="stylesheet"
    />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <link
            rel="stylesheet"
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
            crossorigin="anonymous"
    />
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="assets/css/giohang.css">
    <link rel="stylesheet" href="assets/css/headerAndFooter.css">
</head>

<body>
<header id="header"></header>
<nav id="nav"></nav>


<div class="container-fluid mt-3">
    <div class="mx-auto" style="max-width: 1300px;">
        <h2 class="mb-3">🛒 Giỏ hàng của bạn</h2>

        <div class="row">
            <!-- Bên trái: Danh sách sản phẩm -->
            <div class="col-lg-9 border rounded p-3 bg-light">
                <div class="table-responsive">
                    <table class="table  table-bordered text-center align-middle">
                        <thead class="table-light">
                        <tr>
                            <th class="bg-dark-blue text-light col-2">Hình ảnh</th>
                            <th class="bg-dark-blue text-light col-3">Tên sản phẩm</th>
                            <th class="bg-dark-blue text-light col-3">Đơn giá</th>
                            <th class="bg-dark-blue text-light col-2">Số lượng</th>
                            <th class="bg-dark-blue text-light col-2">Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${cart}" var="item">
                            <tr id="cart-item-${item.product.id}" class="cart-item"
                                data-price="${item.product.priceProduct}"
                                data-product-id="${item.product.id}">
                                <td><img src="assets/imgs/maybom/${item.product.image}" class="img-fluid anhhang"
                                         style="width: 80px; height: auto;"></td>
                                <td><h5 class="mb-1">${item.product.nameProduct}</h5></td>
                                <td class="price-cell">${item.product.priceProduct}</td>
                                <td class="product-quantity">
                                    <div class="quantity-wrapper d-flex justify-content-center align-items-center gap-2">
                                        <input id="quantity-edit-${item.id}" type="number"
                                               name="weight" class="quantity-form form-control"
                                               value="${item.quantity}" min="1" style="width: 70px;">
                                        <button type="button" class="btn-update btn btn-success btn-sm"
                                                data-id="${item.product.id}">
                                            <i class="bi bi-check"></i>
                                        </button>
                                    </div>
                                </td>
                                <td>
                                    <button class="btn btn-danger btn-sm" onclick="removeFromCart(${item.product.id})">
                                        Xóa
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>

            <!-- Bên phải: Mã giảm giá + Tổng cộng -->
            <div class="col-lg-3">
                <div class="border bg-body-secondary p-3 rounded">

                    <!-- Nếu có mã giảm giá đã áp dụng -->
                    <c:if test="${not empty VoucherApplied}">
                        <input type="hidden" id="voucher-type"
                               value="${VoucherApplied.discountValue <= 100 ? 'percent' : 'amount'}">
                        <input type="hidden" id="voucher-value" value="${VoucherApplied.discountValue}">
                    </c:if>

                    <!-- Tổng số lượng -->
                    <p class="mb-2" id="total-amount"><strong>Tổng số lượng:</strong> ${TotalAmount}</p>

                    <!-- Nhập mã giảm giá -->
                    <form action="/DoAnLTWeb/ApplyVoucherServlet" method="post" class="mb-3">
                        <label for="voucherCode" class="form-label"><strong>Mã giảm giá</strong></label>
                        <input type="text" class="form-control mb-2" name="voucherCode" placeholder="Nhập mã giảm giá">
                        <button type="submit" class="btn btn-primary w-100">Áp dụng</button>
                    </form>

                    <!-- Thông báo lỗi/thành công -->
                    <c:if test="${not empty voucherError}">
                        <div class="alert alert-danger">${voucherError}</div>
                    </c:if>
                    <c:if test="${not empty voucherSuccess}">
                        <div class="alert alert-success">${voucherSuccess}</div>
                    </c:if>

                    <!-- Tổng tiền -->
                    <p class="mb-3" id="total-price"><strong>Tổng tiền:</strong> ${TotalPrice} VND</p>

                    <!-- Thanh toán -->
                    <form action="/DoAnLTWeb/CheckoutServlet" method="get" class="text-center mt-3">
                        <input type="hidden" name="total-quantity" value="${TotalAmount}">
                        <input type="hidden" name="total-price" value="${TotalPrice}">

                        <c:if test="${not empty VoucherApplied}">
                            <input type="hidden" name="voucherType"
                                   value="${VoucherApplied.discountValue <= 100 ? 'percent' : 'amount'}">
                            <input type="hidden" name="voucherValue" value="${VoucherApplied.discountValue}">
                        </c:if>

                        <button type="submit" class="btn btn-success w-100">Tiến hành thanh toán</button>
                    </form>

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
    const header2 = document.getElementById("header2");
    const footer2 = document.getElementById("footer2");
    const nav = document.getElementById("nav");
    const tintuc = document.getElementById("tintuc");
    const chonmaybom = document.getElementById("chonmaybom");
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
<script src="assets/js/nav.js"></script>
<script src="assets/js/cart.js"></script>
</body>
</html>
