<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
    <link rel="stylesheet" href="assets/css/admin.css" />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
      crossorigin="anonymous"
    />
    <title>admin</title>
</head>

<body>
    <div class="d-flex" id="wrapper">
        <!-- Sidebar -->
        <div class="bg-white" id="sidebar-wrapper">
            <div class="sidebar-heading text-center py-4 primary-text fs-4 fw-bold text-uppercase border-bottom"><i
                    class="fas fa-user-secret me-2"></i>Quản Lý</div>
            <div class="list-group list-group-flush my-3">
                <a href="admin.jsp" class="list-group-item list-group-item-action bg-transparent second-text fw-bold "><i
                        class="fas fa-tachometer-alt me-2"></i>Trang tổng quan</a>
                <a href="cacsanpham.jsp" class="list-group-item list-group-item-action bg-transparent second-text fw-bold"><i
                        class="fas fa-project-diagram me-2"></i>Dự án</a>
                <a href="#" class="list-group-item list-group-item-action bg-transparent second-text fw-bold"><i
                        class="fas fa-chart-line me-2"></i>Phân tích</a>
                <a href="baocao.html" class="list-group-item list-group-item-action bg-transparent second-text active "><i
                        class="fas fa-paperclip me-2"></i>Báo cáo</a>
                <a href="#" class="list-group-item list-group-item-action bg-transparent second-text fw-bold"><i
                        class="fas fa-shopping-cart me-2"></i>Quản lý cửa hàng</a>
                <a href="cacsanpham.jsp" class="list-group-item list-group-item-action bg-transparent second-text fw-bold"><i
                        class="fas fa-gift me-2"></i>Các sản phẩm</a>
                <a href="#" class="list-group-item list-group-item-action bg-transparent text-danger fw-bold"><i
                        class="fas fa-power-off me-2"></i>Đăng xuất</a>
            </div>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">
            <nav class="navbar navbar-expand-lg navbar-light bg-transparent py-4 px-4">
                <div class="d-flex align-items-center">
                    <i class="fas fa-align-left primary-text fs-4 me-3" id="menu-toggle"></i>
                    <h2 class="fs-2 m-0">Trang báo cáo</h2>
                </div>

                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle second-text fw-bold" href="#" id="navbarDropdown"
                                role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-user me-2"></i>Nghĩa dz
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="#">Thông tin cá nhân</a></li>
                                <li><a class="dropdown-item" href="#">Các cài đặt</a></li>
                                <li><a class="dropdown-item" href="#">Đăng xuất</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>    
            </nav>
            <div class="container-fluid px-4 bg-white m-3">
                <div class="row my-5">
                    <div class="container my-4">
                        <div class="container mt-5">
                            <h1 class="mb-4">Danh Sách Báo Cáo Người Dùng</h1>
                            
                            <table class="table table-bordered">
                              <thead class="thead-dark">
                                <tr>
                                  <th scope="col">Mã Báo Cáo</th>
                                  <th scope="col">Tên Người Dùng</th>
                                  <th scope="col">Ngày Báo Cáo</th>
                                  <th scope="col">Nội Dung</th>
                                  <th scope="col">Trạng Thái</th>
                                </tr>
                              </thead>
                              <tbody>
                                <tr>
                                  <td>BC001</td>
                                  <td>Nguyễn Văn A</td>
                                  <td>2024-11-30</td>
                                  <td>Vấn đề về đăng nhập</td>
                                  <td>
                                    <select class="form-control">
                                    <option class="badge bg-warning" >Đang xử lý</option>										
                                    <option class="badge bg-success" selected>Hoàn thành</option>
                                    <option class="badge bg-danger" >Cần xử lý ngay</option>	
                                    </select></td>
                                </tr>
                                <tr>
                                  <td>BC002</td>
                                  <td>Trần Thị B</td>
                                  <td>2024-11-29</td>
                                  <td>Không thể tải ảnh</td>
                                  <td>
                                    <select class="form-control">
                                    <option class="badge bg-warning" selected>Đang xử lý</option>										
                                    <option class="badge bg-success" >Hoàn thành</option>
                                    <option class="badge bg-danger" >Cần xử lý ngay</option>	
                                    </select></td>
                                </tr>
                                <tr>
                                  <td>BC003</td>
                                  <td>Lê Minh C</td>
                                  <td>2024-11-28</td>
                                  <td>Lỗi giao diện người dùng</td>
                                  <td>
                                    <select class="form-control">
                                    <option class="badge bg-warning" >Đang xử lý</option>										
                                    <option class="badge bg-success" >Hoàn thành</option>
                                    <option class="badge bg-danger" selected>Cần xử lý ngay</option>	
                                    </select></td>
                                </tr>
                                <!-- Thêm các báo cáo khác ở đây -->
                              </tbody>
                            </table>
                          </div>
                    </div>
                </div>

            </div>
        </div>

    </div>
    <!-- /#page-content-wrapper -->
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>