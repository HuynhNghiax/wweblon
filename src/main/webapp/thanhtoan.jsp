<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
    <link rel="stylesheet" href="assets/css/style.css" />
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
     <link rel="stylesheet" href="assets/css/headerAndFooter.css">
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  </head>

  <body>
    <header id="header"></header>
    <nav id="nav"></nav>
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="border p-3">
            

            <div class="row pt-3">
              
              <!-- phan thay doi -->
              <div class="col-md-12">
                <div class="text-primary">
                   <h2 style="color: #162e5c !important;">Thông Tin Đơn Hàng và Vận Chuyển</h2>
                   <form id="orderForm"  method="post" style="color: #162e5c !important;">
                    <div class="mb-3">
                       <label class="form-label">Họ và Tên</label>
                       <input type="text" class="form-control" id="fullName" value="${auth.fullname}" required>
                     </div>
                     <div class="mb-3">
                       <label class="form-label">Số Điện Thoại</label>
                       <input type="text" class="form-control" id="phone" value="${auth.phone}" required>
                     </div>
                     <div class="mb-3">
                       <label class="form-label">Email</label>
                       <input type="email" class="form-control" id="email"  value="${auth.email}" required>
                     </div>
                     <div class="mb-3">
                       <label class="form-label">Địa Chỉ Giao Hàng</label>
                       <textarea class="form-control" id="address" rows="3" required>${auth.address}</textarea>
                     </div>
                     <div class="mb-3">
                       <label class="form-label">Phương Thức Thanh Toán</label>
                      <select class="form-select" name="paymentMethod" id="paymentMethod">
                         <option value="1">Thanh toán khi nhận hàng</option>
                         <option value="2">Chuyển khoản ngân hàng</option>
                       </select>
                     </div>
                     <div class="d-flex justify-content-between">
  						<button type="submit" class="btn btn-success">Xác nhận đơn hàng</button>
 						<a href="CartServlet" class="btn btn-secondary">Trở về giỏ hàng</a>
					</div>
                   </form>
                  <c:if test="${not empty message}">
                      <div style="color: red; font-weight: bold; margin: 10px 0;">
                          ${message}
                      </div>
                  </c:if>
            </div>

            <footer id="footer2"></footer>
            <div id="bought-product"></div>
          </div>
        </div>
      </div>
    </div>
    </div>
    </div>
    <footer id="footer"></footer>
    <script>
      const header = document.getElementById("header");
      const footer2 = document.getElementById("footer2");
      const nav = document.getElementById("nav");
      const tintuc = document.getElementById("tintuc");
      const chonmaybom = document.getElementById("chonmaybom");
      const boughtProduct = document.getElementById("bought-product");
      fetch("./assets/component/boughtProduct.jsp")
      	.then((response) => response.text())
      	.then((html) => (boughtProduct.innerHTML = html));
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
	<script src="assets/js/boughtProduct.js"></script>
  </body>
</html>
