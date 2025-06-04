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
  </head>

  <body>
    <header id="header"></header>
    <nav id="nav"></nav>
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="border p-3">


            <div class="row pt-3">
              <div class="col-md-3">
                <div id="chonmaybom"></div>
                <div id="tintuc"></div>
              </div>
              <!-- phan thay doi -->
              <div class="col-md-9">
                <h4 class="text-primary">LIÊN HỆ</h4>
                <form action="contact" method="post">
                  <div class="mb-3">
                    <label for="name" class="form-label">Tên của bạn</label>
                    <input type="text" class="form-control" id="name" required />
                  </div>
                  <div class="mb-3">
                    <label for="email" class="form-label">Email của bạn</label>
                    <input type="email" class="form-control" id="email" required />
                  </div>
                  <div class="mb-3">
                    <label for="phone" class="form-label">Số điện thoại</label>
                    <input type="tel" class="form-control" id="phone" />
                  </div>
                  <div class="mb-3">
                    <label for="subject" class="form-label">Tiêu đề</label>
                    <input type="text" class="form-control" id="subject" />
                  </div>
                  <div class="mb-3">
                    <label for="message" class="form-label">Nội dung</label>
                    <textarea class="form-control" id="message" rows="3"></textarea>
                  </div>
                  <button type="submit" class="btn btn-primary">Gửi đi</button>
                </form>
                <p class="mt-3 text-center pt-3">
                  <b>Công ty TNHH Đầu tư Thương mại Hà Nội Thịnh Vượng</b><br />
                  Địa chỉ: 268 Trường Chinh - Đống Đa - Hà Nội<br />
                  Điện thoại: 09.6205 0033 - 0972 952 882<br />
                  Email: maybomhanoi@gmail.com<br />
                  Website: www.maybomhanoi.com
                </p>
              </div>

              <footer id="footer2"></footer>
              <div id="bought-product"></div>
            </div>
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
    <script src="assets/js/boughtProduct.js"></script>
  </body>
</html>
