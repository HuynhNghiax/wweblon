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
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
      crossorigin="anonymous"
    />
  </head>

  <body>
    <header id="header"></header>
    <div class="container">
      <div class="row">
        <div class="col-md-12">
          <div class="border p-3">
            <div id="header2"></div>
            <nav id="nav"></nav>

            <div class="row pt-3">
              <div class="col-md-3">
                <div id="chonmaybom"></div>
                <div id="tintuc"></div>
              </div>
              <!-- phan thay doi -->
              <div class="col-md-9">
                <div class="text-primary">
                  <h2>Chính sách bảo hành</h2>
                </div>

                <!-- Nội dung bảo hành -->
                <div class="content">
                  <p>
                    Tất cả các sản phẩm chúng tôi cung cấp là chính hãng. Sản
                    phẩm sẽ được bảo hành theo quy định của nhà sản xuất. Khách
                    hàng có thể lựa chọn các phương án để được bảo hành sản
                    phẩm:
                  </p>
                  <ul>
                    <li>
                      <strong>1.</strong> Mang sản phẩm và phiếu bảo hành đến
                      bảo hành tại địa chỉ được chỉ định của nhà sản xuất.
                    </li>
                    <li>
                      <strong>2.</strong> Mang sản phẩm, phiếu bảo hành và hóa
                      đơn mua hàng đến chúng tôi.
                    </li>
                    <li>
                      <strong>3.</strong> Sử dụng dịch vụ bảo hành tại nhà của
                      chúng tôi (sẽ tính phí dịch vụ).
                    </li>
                  </ul>

                  <!-- Điều kiện bảo hành -->
                  <h5>Một số điều kiện bảo hành chung của các nhà sản xuất:</h5>
                  <ul>
                    <li>
                      Sản phẩm còn trong hạn bảo hành tại thời điểm khách hàng
                      yêu cầu,
                    </li>
                    <li>
                      Phiếu bảo hành đầy đủ thông tin: kiểu máy, số serial, ngày
                      sản xuất, tên khách hàng sử dụng, địa chỉ, ngày mua.
                    </li>
                    <li>
                      Lỗi hỏng hóc do chất lượng linh kiện hay lỗi kỹ thuật
                      trong quy trình sản xuất của nhà sản xuất.
                    </li>
                  </ul>

                  <!-- Trường hợp từ chối bảo hành -->
                  <h5>
                    Sản phẩm thuộc một trong những điều kiện sau sẽ bị từ chối
                    bảo hành:
                  </h5>
                  <ul>
                    <li>
                      Sản phẩm bị hư hỏng do thiên nhiên (lũ lụt, hỏa hoạn, sấm
                      sét...) hoặc do côn trùng động vật gây nên.
                    </li>
                    <li>
                      Sản phẩm bị hư do vận chuyển, sử dụng, không tuân thủ
                      hướng dẫn, nguồn điện không ổn định, không đúng nguồn
                      điện, sử dụng nguồn nước nhiễm phèn, cát, côn trùng…
                    </li>
                    <li>
                      Motor cháy do điện nguồn, nước, lắp đặt không đúng hoặc
                      nước vào motor.
                    </li>
                    <li>Lắp đặt nơi ẩm thấp</li>
                    <li>
                      Các bộ phận trên máy đã được tháo dỡ sửa chữa ở các nơi
                      không phải là nhà phân phối hoặc sản xuất sản phẩm. Sản
                      phẩm không có tem bảo hành khi sửa chữa lần đầu tiên.
                    </li>
                  </ul>
                </div>
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
