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
              <!-- phan thay doi -->
              <div class="col-md-12">
                <div class="container-fluid d-flex justify-content-center">
                    <div class="forgot-password-container">
                      <%
                                          String userId = (String) request.getAttribute("userId");
                                          String message = (String) request.getAttribute("message");
                                          if (userId == null || userId.isEmpty()) {
                                              userId = request.getParameter("user_id"); // fallback
                                          }
                                      %>
                                      <form action="verify-otp" method="post" class="d-flex align-items-center">
                                          <input type="hidden" name="user_id" value="<%= userId %>">
                                          <label for="otp" class="me-2">OTP:</label>
                                          <input type="text" name="otp" id="otp" required class="form-control w-auto me-2" style="width: 200px;">
                                          <button type="submit" class="btn btn-primary">Verify OTP</button>
                                      </form>
                                      <% if (message != null) { %>
                                          <p style="color: <%= message.contains("thành công") ? "green" : "red" %>;">
                                              <%= message %>
                                          </p>
                                      <% } %>
                      <a href="index.jsp" class="text-muted">← Quay về trang chủ</a>
                    </div>
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

  </body>
</html>
