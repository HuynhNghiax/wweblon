<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "sql" uri = "http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix = "x" uri = "http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!-- Bắt đầu header  -->
<div id="header">
	<div
		class=" d-flex justify-content-between align-items-center bg-dark-blue ">
		<div class="container list-display-none  text-light">
			<ul class="list-head2 mb-0 d-flex justify-content-between px-0">
				<li class="list-item-head2 "><a href="index.jsp"
					class="nav-link text-light">Trang chủ</a></li>

				<li class="list-item-head2 shop-active position-relative"><a
					class="nav-link shop text-light">Các dòng máy <i
						class="bi-caret-down-fill"></i></a>
					<ul id="categoryList" class="wrap-shop-list bg-dark-blue">

					</ul>
				</li>
				<li class="list-item-head2"><a href="gioithieu.jsp"
					class="nav-link text-light">Giới thiệu dịch vụ</a>
				</li>
				<li class="list-item-head2"><a href="new.jsp"
					class="nav-link text-light">Tin tức</a>
				</li>
				<li class="list-item-head2"><a href="lienhe.jsp"
					class="nav-link text-light">Liên hệ</a>
				</li>

				<li class="list-item-head2">
					<a href="/DoAnLTWeb/CartServlet" class="nav-link icon-cart text-light"> <i class="bi-cart 	"></i></a>
				</li>

				<c:if test="${not empty sessionScope.auth}">
					<c:choose>
						<c:when test="${sessionScope.auth.idPermission == 1}">
							<li class="list-item-head2"><a href="admin.jsp"
								class="nav-link text-light"> ${sessionScope.auth.username} </a></li>
						</c:when>
						<c:otherwise>
							<li class="list-item-head2"><a
								href="/DoAnLTWeb/UserProfileServlet"
								class="nav-link text-light"> ${sessionScope.auth.username} </a></li>
						</c:otherwise>
					</c:choose>
					<li class="list-item-head2"><a href="/DoAnLTWeb/LogoutController"
						class="nav-link text-light">Đăng xuất</a></li>
				</c:if>

				<c:if test="${empty sessionScope.auth}">
					<li class="list-item-head2"><a href="login.jsp"
						class="nav-link text-light">Đăng nhập</a></li>
				</c:if>

			</ul>
		</div>
	</div>
</div>
<!-- Kết thức header  -->
