document.addEventListener("DOMContentLoaded", function () {
    $.ajax({
        url: "/DoAnLTWeb/CategoryServlet",
        method: "GET",
        dataType: "json",
        success: function (data) {
            console.log("Dữ liệu nhận được:", data);

            let categoryList = document.getElementById("categoryList");
            if (!categoryList) {
                console.error("Không tìm thấy phần tử categoryList!");
                return;
            }

            categoryList.innerHTML = ""; // Xóa dữ liệu cũ nếu có

            data.forEach(category => {
                let li = document.createElement("li");
                li.className = "shop-list";
                li.innerHTML = '<a href="/DoAnLTWeb/ProductByCategory?categoryId=' + category.id + '" class="text-light">' +
                    category.categoryName +
                    '</a>';
                categoryList.appendChild(li);
            });

            console.log("Danh sách đã được cập nhật:", categoryList.innerHTML);
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi gọi API:", error);
        }
    });
});
