<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          integrity="sha512-Kc323vGBEqzTmouAECnVceyQqyqdsSiqLQISBL29aUW4U/M7pSPA/gEUZQqv1cwx4OnYxTxve5UMg5GT6L4JJg=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
   	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link rel="stylesheet" href="assets/css/headerAndFooter.css">
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body style="background-color: rgb(242 244 247)">
<header id="header"></header>
<nav id="nav"></nav>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="border p-3">

                <!-- phan thay doi -->
                <div class="col-md-12">
                    <div class="container">
                        <p>Bạn đã có tài khoản ? <a href="login.jsp">Đăng nhập</a> tại đây</p>
                        <div class="text-center">
                            <h2 class="text-primary">Đăng Kí</h2>
                            <p class="text-muted mb-5">Hãy đăng kí để nhận được nhiều thông báo về khuyến mãi và nhiều hơn
                                nữa</p>
                        </div>
                        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
                        <form action="register" method="post">
                            <!-- Hàng 1 -->
                            <div class="row">
                                <div class="col">
                                    <div class="mb-3">
                                        <p>Họ và tên</p>
                                        <input type="text" id="fullname" name="fullname" class="form-control" required>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-3">
                                        <p>Số điện thoại</p>
                                        <input type="text" id="phone" name="phone" class="form-control" required>
                                    </div>
                                </div>
                            </div>

                            <!-- Hàng 2 -->
                            <div class="row">
                                <div class="col">
                                    <div class="mb-3">
                                        <p>Tên đăng nhập</p>
                                        <input type="text" id="username" name="username" class="form-control" required>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="mb-3">
                                        <p>Email</p>
                                        <input type="email" id="email" name="email" class="form-control">
                                    </div>
                                </div>
                            </div>

                            <!-- Hàng 3 -->
                            <div class="row">
                                <div class="col-6">
                                    <div class="mb-3">
                                        <p>Mật khẩu</p>
                                        <input type="password" id="password" name="password" class="form-control" required>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="mb-3">
                                        <p>Nhập lại mật khẩu</p>
                                        <input type="password" id="confirm_password" name="confirm_password" class="form-control">
                                    </div>
                                </div>
                            </div>

                            <!-- Hàng 4 -->
                            <div class="row">
                                <div class="col-6">
                                    <div class="mb-3">
                                        <p>Địa chỉ</p>
                                        <input type="text" id="address" name="address" class="form-control">
                                    </div>
                                </div>
                            </div>
                            <!-- Google reCAPTCHA -->
                            <div class="g-recaptcha" data-sitekey="6LcIsA4rAAAAAKTypNS-94XJI8a-Hq34isJYFdDQ"></div>
                            <!-- Đồng ý điều khoản -->
                            <div class="row mt-2 justify-content-between">
                                <div class="col-6">
                                    <input type="checkbox" name="agree" required>
                                    <label>Tôi đồng ý với <a href="chinhsachbaohanh.jsp">Điều khoản sử dụng</a></label>
                                </div>
                            </div>


                            <!-- Nút đăng ký -->
                            <div class="row justify-content-center">
                                <div class="col-3">
                                    <button type="submit" class="btn  w-100 mb-3 text-light bg-dark-blue">Đăng ký</button>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            <footer id="footer2"></footer>
            </div>
        </div>
    </div>
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
    fetch("./assets/component/header2.jsp")
        .then((response) => response.text())
        .then((html) => (header2.innerHTML = html));
    fetch("./assets/component/nav.jsp")
        .then((response) => response.text())
        .then((html) => (nav.innerHTML = html));
    fetch("./assets/component/tintuc.jsp")
        .then((response) => response.text())
        .then((html) => (tintuc.innerHTML = html));
    fetch("./assets/component/chonmaybom.jsp")
        .then((response) => response.text())
        .then((html) => (chonmaybom.innerHTML = html));
</script>
<script src="assets/js/nav.js"></script>
</body>
</html>
