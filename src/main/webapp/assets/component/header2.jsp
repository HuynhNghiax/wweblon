<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<div class="container d-flex justify-content-between align-items-center py-4">
    <a href="/DoAnLTWeb/index.jsp" class="head-title">AquaTech</a>
    <form action="Search" method="get" class="d-flex position-relative">
        <input id="searchInput" name="search" type="search" class="form-control me-2" placeholder="Search..." style="width: 300px">
        <input type="submit" value="Search" class="btn bg-dark-blue text-light">
        <div id="suggestions" class="position-absolute bg-white border rounded" style="top: 100%; width: 300px; z-index: 1000;"></div>
    </form>
</div>
