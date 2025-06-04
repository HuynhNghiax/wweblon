<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<div class="bg-dark-blue" id="sidebar-wrapper">
        <div class="sidebar-heading text-center py-4 primary-text fs-4 fw-bold text-uppercase border-bottom"><i
                class="fas fa-user-secret me-2"></i>Quản Lý
        </div>
        <div class="list-group list-group-flush my-3">
            <a href="admin.jsp" class="list-group-item list-group-item-action bg-transparent second-text active"
                aria-controls="home" aria-selected="true"><i
                    class="fas fa-tachometer-alt me-2"></i>Quản lý mua bán</a>
            <a href="#id2" class="list-group-item list-group-item-action bg-transparent second-text fw-bold"
                aria-controls="home" aria-selected="true"><i
                    class="fas fa-project-diagram me-2"></i>Quản lý sản phẩm</a>
            <a href="${pageContext.request.contextPath}/OrderController" class="list-group-item list-group-item-action bg-transparent second-text fw-bold"
                aria-controls="home" aria-selected="true">
               <i class="bi bi-box-seam me-2"></i>Quản lý đơn hàng</a>
            <a href="${pageContext.request.contextPath}/StockInController" class="list-group-item list-group-item-action bg-transparent second-text fw-bold"
                aria-controls="home" aria-selected="true">
               <i class="bi bi-box-arrow-in-down me-2"></i>Quản lý kho</a>
            <a href="#id3" class="list-group-item list-group-item-action bg-transparent second-text fw-bold"
                aria-controls="home" aria-selected="true"><i
                    class="fas fa-chart-line me-2"></i>Quản lý người dùng</a>
            <a href="/DoAnLTWeb/LogoutController" class="list-group-item list-group-item-action bg-transparent second-text fw-bold"
               aria-selected="true"><i class="fa-solid fa-power-off"></i> Thoát</a>
        </div>
    </div>