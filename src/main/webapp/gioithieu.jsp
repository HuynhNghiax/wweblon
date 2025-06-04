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
                <div class="text-center">
                  <h2 class="text-primary">Giới thiệu</h2>
                  <h3 class="text-primary font-weight-bold">
                    CÔNG TY TNHH ĐẦU TƯ THƯƠNG MẠI HÀ NỘI THỊNH VƯỢNG
                  </h3>
                </div>

                <!-- Nội dung mô tả -->
                <div class="content mt-3">
                  <h5><em>Lĩnh vực hoạt động:</em></h5>
                  <p>
                    Chúng tôi hoạt động trong lĩnh vực nhập khẩu và phân phối
                    các sản phẩm về máy bơm nước: Bơm hút đẩy, bơm tăng áp, bơm
                    biến tần, bơm thả chìm, bơm giếng khoan phục vụ nhu cầu dân
                    dụng và công nghiệp. Nhiều thương hiệu được tin dùng như
                    <strong
                      >Hitachi, Wilo, Pentax, Hanil, Ebara, Panasonic, Shimizu,
                      Lepono, App, Forerun, Mastra, Sealand, Sena, Selton,
                      Kangaroo, Peroni, Shinil, Shining, Grundfos...</strong
                    >
                  </p>
                  <p>
                    Đội ngũ nhân viên và quản lý kinh nghiệm lâu năm luôn được
                    lựa chọn kỹ càng trước khi trải qua quá trình huấn luyện và
                    cập nhật thường xuyên những thành tựu mới nhất về dịch vụ
                    khách hàng, dịch vụ kỹ thuật và sản phẩm. Chúng tôi xem khả
                    năng làm hài lòng khách hàng là thước đo thành công của
                    chính mình.
                  </p>
                  <h5><em>Chúng tôi cam kết:</em></h5>
                  <ul>
                    <li>
                      <strong
                        >Mang lại sản phẩm chất lượng cao với giá tốt nhất đến
                        với khách hàng.</strong
                      >
                    </li>
                    <li>
                      <strong>Đặt dịch vụ khách hàng lên hàng đầu.</strong>
                    </li>
                  </ul>
                </div>

                <!-- Hình ảnh các thương hiệu -->
                <div class="brand-logos text-center mt-4">
                  <img
                    src="assets\imgs\nhataitro\may-bom-nuoc.jpg"
                    alt="APP Logo"
                    class="img-fluid"
                  />
                </div>
              </div>
            </div>

            <footer id="footer2"></footer>
            <div id="bought-product"></div>
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
