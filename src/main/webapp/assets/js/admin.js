// quan li sp

function loadProductData() {
    if ($.fn.dataTable.isDataTable('#productTable')) {
        $('#productTable').DataTable().clear().destroy();
    }
    // Static mapping for category and supplier names (replace with dynamic data if necessary)
    var categories = {
        1: 'Máy bơm nước',
        2: 'Máy bơm chân không',
        3: 'Máy bơm ly tâm',
        4: 'Máy bơm định lượng',
        5: 'Máy bơm hóa chất',
        6: 'Máy bơm chìm',
        7: 'Máy bơm nước sạch',
        8: 'Máy bơm công nghiệp'
    };

    var suppliers = {
        1: 'Công ty A',
        2: 'Công ty B',
        3: 'Công ty C',
        4: 'Công ty D',
        5: 'Công ty E',
        6: 'Công ty F',
        7: 'Công ty G',
        8: 'Công ty H',
        9: 'Công ty I',
        10: 'Công ty J'
    };
    $.ajax({
        url: 'http://localhost:8080/DoAnLTWeb/productController', // API của bạn
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data); // Kiểm tra dữ liệu trả về
            var tableBody = $('#productBody');
            tableBody.empty(); // Xóa dữ liệu cũ

            data.forEach(function (product) {

                // Get category and supplier names based on their IDs
                var categoryName = categories[product.idCategory] || 'N/A';
                var supplierName = suppliers[product.idSupplier] || 'N/A';

                var row = `<tr>
                        <td>${product.id}</td>
                        <td style="min-width: 70px;">${product.nameProduct}</td>
                        <td><img src="assets/imgs/maybom/${product.image}" alt="Product Image" style="width: 60px; height: 60px;"></td>
                        <td>${product.priceProduct}</td>
                        <td style="min-width: 150px;">${product.description}</td>
                        <td style="min-width: 90px;">${product.manufactureDate}</td>
                        <td>${product.power}</td>
                        <td>${product.pressure}</td>
                        <td>${product.flowRate}</td>
                        <td>${product.pipeDiameter}</td>
                        <td>${product.voltage}</td>
                        <td>${product.brand}</td>
                        <td>${product.warrantyMonths}</td>
                        <td>${product.stock}</td>               
                    
                        <td style="min-width: 100px;" data-category-id="${product.idCategory}">${categoryName}</td> <!-- Display category name -->
                        <td style="min-width: 70px;" data-supplier-id="${product.idSupplier}">${supplierName}</td> <!-- Display supplier name -->
                        <td>
                            <div class="d-flex gap-2 justify-content-center">
                                <button class="btn btn-sm btn-primary product-edit-btn" data-id="${product.id}">Sửa</button>
                                <button class="btn btn-sm btn-danger product-delete-btn" data-id="${product.id}">Xóa</button>
                            </div>
                        </td>
                    </tr>`;
                tableBody.append(row);
            });

            // Destroy bảng cũ nếu có
            if ($.fn.DataTable.isDataTable('#productTable')) {
                $('#productTable').DataTable().destroy();
            }

            // Khởi tạo DataTable với tiếng Việt
            $('#productTable').DataTable({
                "paging": true,
                "searching": true,
                "ordering": true,
                "lengthChange": true,
                "language": {
                    "lengthMenu": "Hiển thị _MENU_ sản phẩm mỗi trang",
                    "zeroRecords": "Không tìm thấy sản phẩm nào",
                    "info": "Hiển thị _START_ đến _END_ của _TOTAL_ sản phẩm",
                    "infoEmpty": "Không có sản phẩm nào",
                    "infoFiltered": "(lọc từ _MAX_ sản phẩm)",
                    "search": "Tìm kiếm:",
                    "paginate": {
                        "first": "Đầu",
                        "last": "Cuối",
                        "next": "Sau",
                        "previous": "Trước"
                    },
                }
            });
        },
        error: function (xhr, status, error) {
            console.error("Lỗi lấy dữ liệu sản phẩm: " + error);
            console.log(xhr.responseText);
        }
    });
}
document.addEventListener('DOMContentLoaded',loadProductData)

// Hàm xử lý thêm sản phẩm
function addProduct(event) {
    event.preventDefault();  // Ngăn reload trang

    // Lấy form theo ID
    var form = document.getElementById('addProductForm');
    var formData = new FormData(form);
    const productData = Object.fromEntries(formData.entries());

    // Ép kiểu dữ liệu
    productData.nameProduct = String(productData.nameProduct);
    productData.image = String(productData.image);
    productData.priceProduct = parseFloat(productData.priceProduct);
    productData.description = String(productData.description);
    productData.manufactureDate = String(productData.manufactureDate);
    productData.power = String(productData.power);
    productData.pressure = parseFloat(productData.pressure);
    productData.flowRate = parseFloat(productData.flowRate);
    productData.pipeDiameter = parseFloat(productData.pipeDiameter);
    productData.voltage = parseInt(productData.voltage);
    productData.brand = String(productData.brand);
    productData.warrantyMonths = parseInt(productData.warrantyMonths);
    productData.stock = parseInt(productData.stock);
    productData.idCategory = parseInt(productData.idCategory);
    productData.idSupplier = parseInt(productData.idSupplier);

    console.log(productData); // Debug data

    // Gửi AJAX
    $.ajax({
        url: 'http://localhost:8080/DoAnLTWeb/productController',
        type: 'POST',
        data: JSON.stringify(productData),
        contentType: 'application/json',
        success: function (response) {
            alert(response.message);
            if (response.message === "Sản phẩm đã được thêm thành công!") {
                $('#addProductModal').modal('hide');
                // Giả sử có hàm loadProducts()
                loadProductData();
            }
        },
        error: function (xhr, status, error) {
            alert("Có lỗi xảy ra: " + error);
        }
    });
}
// sua san pham
// Hàm xử lý khi nhấn nút "Sửa" để hiển thị dữ liệu sản phẩm vào modal
$(document).on('click', '.product-edit-btn', function() {
    var id = $(this).data('id');  // Lấy id sản phẩm từ thuộc tính data-id
    showEditProductModal(id);  // Hiển thị modal sửa sản phẩm
});

// Hàm hiển thị dữ liệu sản phẩm trong modal sửa
function showEditProductModal(id) {
    // Lấy dữ liệu của sản phẩm từ dòng chứa nút sửa

    var row = $('button[data-id="'+id+'"]').closest('tr');  // Lấy dòng chứa nút sửa
    // Lấy giá trị từ các cột trong dòng đó và điền vào form modal
    $('#editProductId').val(id);
    $('#editProductName').val(row.find('td').eq(1).text());  // Lấy tên sản phẩm từ cột thứ 2
    $('#editProductImage').val(row.find('td').eq(2).find('img').attr('src'));  // Lấy hình ảnh từ cột thứ 3
    $('#editProductPrice').val(row.find('td').eq(3).text());  // Lấy giá từ cột thứ 4
    $('#editProductDescription').val(row.find('td').eq(4).text());  // Lấy mô tả từ cột thứ 5
    $('#editProductManufactureDate').val(row.find('td').eq(5).text());  // Lấy ngày sản xuất từ cột thứ 6
    $('#editProductPower').val(row.find('td').eq(6).text());  // Lấy công suất từ cột thứ 7
    $('#editProductPressure').val(row.find('td').eq(7).text());  // Lấy áp suất từ cột thứ 8
    $('#editProductFlowRate').val(row.find('td').eq(8).text());  // Lấy lưu lượng từ cột thứ 9
    $('#editProductPipeDiameter').val(row.find('td').eq(9).text());  // Lấy đường kính ống từ cột thứ 10
    $('#editProductVoltage').val(row.find('td').eq(10).text());  // Lấy điện áp từ cột thứ 11
    $('#editProductBrand').val(row.find('td').eq(11).text());  // Lấy thương hiệu từ cột thứ 12
    $('#editProductWarrantyMonths').val(row.find('td').eq(12).text());  // Lấy bảo hành từ cột thứ 13
    $('#editProductStock').val(row.find('td').eq(13).text());  // Lấy tồn kho từ cột thứ 14
    $('#editProductCategory').val(row.find('td').eq(14).data('category-id'));  // Lấy danh mục từ cột thứ 15
    $('#editProductSupplier').val(row.find('td').eq(15).data('supplier-id')); // Lấy nhà cung cấp từ cột thứ 16

    // Hiển thị modal
    $('#editProductModal').modal('show');
}

// Hàm xử lý sửa sản phẩm
function editProduct(event) {
    event.preventDefault();  // Ngăn reload trang

    // Lấy form theo ID
    var form = document.getElementById('editProductForm');
    var formData = new FormData(form);
    const productData = Object.fromEntries(formData.entries());
    var pid =parseInt(document.getElementById('editProductId').value);
    // Ép kiểu dữ liệu
    productData.id = parseInt(productData.id);  // ID sản phẩm cần sửa
    productData.nameProduct = String(productData.nameProduct);
    productData.image = String(productData.image);
    productData.priceProduct = parseFloat(productData.priceProduct);
    productData.description = String(productData.description);
    productData.manufactureDate = String(productData.manufactureDate);
    productData.power = String(productData.power);
    productData.pressure = parseFloat(productData.pressure);
    productData.flowRate = parseFloat(productData.flowRate);
    productData.pipeDiameter = parseFloat(productData.pipeDiameter);
    productData.voltage = parseInt(productData.voltage);
    productData.brand = String(productData.brand);
    productData.warrantyMonths = parseInt(productData.warrantyMonths);
    productData.stock = parseInt(productData.stock);
    productData.idCategory = parseInt(productData.idCategory);
    productData.idSupplier = parseInt(productData.idSupplier);

    console.log(productData); // Debug data

    // Gửi yêu cầu PUT để cập nhật sản phẩm

    $.ajax({
        url: 'http://localhost:8080/DoAnLTWeb/productController?id=' + pid,  // API xử lý PUT sửa sản phẩm
        type: 'PUT',
        data: JSON.stringify(productData),
        contentType: 'application/json',
        success: function(response) {
            alert(response.message);
            if (response.message === "Sản phẩm đã được cập nhật thành công!") {
                $('#editProductModal').modal('hide');  // Đóng modal sau khi sửa thành công
                loadProductData();  // Tải lại dữ liệu sản phẩm
            }
        },
        error: function(xhr, status, error) {
            console.error('Lỗi cập nhật :', error);
            alert("Có lỗi xảy ra: " + error);
        }
    });
}
// Hàm xử lý xóa sản phẩm

$(document).off('click', '.product-delete-btn').on('click', '.product-delete-btn', function () {
    var productId = $(this).data('id');

    if (confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')) {
        $.ajax({
            url: 'http://localhost:8080/DoAnLTWeb/productController?id=' + productId,
            type: 'DELETE',
            success: function (response) {
                alert(response.message);
                loadProductData();
            },
            error: function (xhr, status, error) {
                console.log(xhr);
                let errorMessage;
                try {
                    errorMessage = JSON.parse(xhr.responseText).message || 'Có lỗi xảy ra khi xóa sản phẩm';
                } catch (e) {
                    errorMessage = 'Có lỗi xảy ra khi xóa sản phẩm';
                }
                alert(errorMessage);
            }
        });
    }
});

// --------------khuyen mãi--------------------
function loadSaleData() {
    if ($.fn.dataTable.isDataTable('#promotionTable')) {
        $('#promotionTable').DataTable().clear().destroy();
    }

    // Static mapping for status names (you can modify or make this dynamic if necessary)
    var statuses = {
        1: 'Đang áp dụng',
        0: 'Không áp dụng'
    };

    $.ajax({
        url: 'http://localhost:8080/DoAnLTWeb/SaleController', // API của bạn
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data); // Kiểm tra dữ liệu trả về
            var tableBody = $('#promotionBody');
            tableBody.empty(); // Xóa dữ liệu cũ

            data.forEach(function (sale) {

                // Get status name based on status ID
                var statusName = statuses[sale.status] || 'N/A';

                var row = `<tr>
                        <td>${sale.id}</td>
                        <td style="min-width: 50px;">${sale.promotion}</td>
                        <td style="min-width: 130px;">${sale.description}</td>
                        <td>${statusName}</td>
                        <td style="min-width: 100px;">${sale.startDate}</td>
                        <td style="min-width: 100px;">${sale.endDate}</td>
                        <td>${sale.idProduct}</td>
                        <td>
                            <div class="d-flex gap-2 justify-content-center">
                                <button class="btn btn-sm btn-primary promotion-edit-btn" data-id="${sale.id}">Sửa</button>
                                <button class="btn btn-sm btn-danger promotion-delete-btn" data-id="${sale.id}">Xóa</button>
                            </div>
                        </td>
                    </tr>`;
                tableBody.append(row);
            });

            // Destroy bảng cũ nếu có
            if ($.fn.DataTable.isDataTable('#promotionTable')) {
                $('#promotionTable').DataTable().destroy();
            }

            // Khởi tạo DataTable với tiếng Việt
            $('#promotionTable').DataTable({
                "paging": true,
                "searching": true,
                "ordering": true,
                "lengthChange": true,
                "language": {
                    "lengthMenu": "Hiển thị _MENU_ khuyến mãi mỗi trang",
                    "zeroRecords": "Không tìm thấy khuyến mãi nào",
                    "info": "Hiển thị _START_ đến _END_ của _TOTAL_ khuyến mãi",
                    "infoEmpty": "Không có khuyến mãi nào",
                    "infoFiltered": "(lọc từ _MAX_ khuyến mãi)",
                    "search": "Tìm kiếm:",
                    "paginate": {
                        "first": "Đầu",
                        "last": "Cuối",
                        "next": "Sau",
                        "previous": "Trước"
                    },
                }
            });
        },
        error: function (xhr, status, error) {
            console.error("Lỗi lấy dữ liệu khuyến mãi: " + error);
            console.log(xhr.responseText);
        }
    });
}

document.addEventListener('DOMContentLoaded', loadSaleData);

//them khuyến mãi
$(document).ready(function () {
    $('#addPromotionForm').off('submit').on('submit', function (e) {
        e.preventDefault(); // Ngăn form reload trang

        const startDate = new Date($('#startDate').val());
        const endDate = new Date($('#endDate').val());

        // Kiểm tra ngày kết thúc phải sau ngày bắt đầu
        if (endDate <= startDate) {
            alert(" Ngày kết thúc phải sau ngày bắt đầu!");
            return; // Dừng submit
        }

        // Lấy dữ liệu từ form
        const saleData = {
            promotion: parseFloat($('#promotionValue').val()),
            description: $('#description').val(),
            status: parseInt($('#status').val()),
            startDate: $('#startDate').val(),
            endDate: $('#endDate').val(),
            idProduct: parseInt($('#idProduct').val())
        };

        // Gửi dữ liệu lên servlet qua POST
        $.ajax({
            url: 'http://localhost:8080/DoAnLTWeb/SaleController',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(saleData),
            success: function (response) {
                alert(" Thêm khuyến mãi thành công!");
                $('#addPromotionForm')[0].reset();
                $('#addPromotionModal').modal('hide');
                loadSaleData();
            },
            error: function (xhr, status, error) {
                console.error("Lỗi khi thêm khuyến mãi:", error);
                alert(" Thêm khuyến mãi thất bại!");
            }
        });
    });
});

//sửa khuyến mãi

$(document).on('click', '.promotion-edit-btn', function (event) {
    event.stopPropagation();
    const row = $(this).closest('tr');
    const id = $(this).data('id');

    // Lấy dữ liệu từng cột
    const promotion = row.find('td:eq(1)').text();
    const description = row.find('td:eq(2)').text();
    const statusText = row.find('td:eq(3)').text().trim();
    const startDate = row.find('td:eq(4)').text();
    const endDate = row.find('td:eq(5)').text();
    const idProduct = row.find('td:eq(6)').text();

    // Chuyển status từ text sang số
    const status = (statusText === 'Đang áp dụng') ? 1 : 0;

    // Format ngày để đưa vào input type="datetime-local"
    const formatDateTime = (dateTimeString) => {
        const date = new Date(dateTimeString);
        return date.toISOString().slice(0, 16); // yyyy-MM-ddTHH:mm
    };

    // Gán dữ liệu vào form
    $('#editPromotionId').val(id);
    $('#editPromotionValue').val(promotion);
    $('#editDescription').val(description);
    $('#editStatus').val(status);
    $('#editStartDate').val(formatDateTime(startDate));
    $('#editEndDate').val(formatDateTime(endDate));
    $('#editIdProduct').val(idProduct);

    // Hiển thị modal
    $('#editPromotionModal').modal('show');
});
// Khi modal đóng, xóa backdrop và khôi phục cuộn trang
$('#editPromotionModal').on('hidden.bs.modal', function () {
    $('.modal-backdrop').remove();
    $('body').css('overflow', 'auto');  // Khôi phục khả năng cuộn trang
});

$('#editPromotionForm').off('submit').on('submit', function (e) {
    e.preventDefault();

    const startDate = new Date($('#editStartDate').val());
    const endDate = new Date($('#editEndDate').val());

    if (endDate <= startDate) {
        alert("Ngày kết thúc phải sau ngày bắt đầu!");
        return;
    }

    const updatedSale = {
        id: parseInt($('#editPromotionId').val()),
        promotion: parseFloat($('#editPromotionValue').val()),
        description: $('#editDescription').val(),
        status: parseInt($('#editStatus').val()),
        startDate: $('#editStartDate').val(),
        endDate: $('#editEndDate').val(),
        idProduct: parseInt($('#editIdProduct').val())
    };

    $.ajax({
        url: 'http://localhost:8080/DoAnLTWeb/SaleController',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(updatedSale),
        success: function (response) {
            alert("Cập nhật khuyến mãi thành công!");
            $('#editPromotionModal').modal('hide');
            loadSaleData(); // Reload lại danh sách khuyến mãi
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi cập nhật khuyến mãi:", error);
            alert("Cập nhật khuyến mãi thất bại!");
        }
    });
});
// xoa
$(document).off('click', '.promotion-delete-btn').on('click', '.promotion-delete-btn', function () {
    const id = $(this).data('id');

    if (confirm("Bạn có chắc chắn muốn xóa khuyến mãi này không?")) {
        $.ajax({
            url: `http://localhost:8080/DoAnLTWeb/SaleController?id=${id}`,
            type: 'DELETE',
            success: function (response) {
                alert(response.message || "Xóa thành công!");
                loadSaleData(); // Tải lại danh sách
            },
            error: function (xhr, status, error) {
                console.error("Lỗi khi xóa khuyến mãi:", error);
                alert("Xóa khuyến mãi thất bại hoặc không tìm thấy!");
            }
        });
    }
});
// user
function loadUserData() {
    if ($.fn.dataTable.isDataTable('#userTable')) {
        $('#userTable').DataTable().clear().destroy();
    }

    // Mapping quyền (idPermission)
    const permissionMap = {
        1: "Admin",
        2: "Khách hàng",
        3: "Nhân viên",
        4: "Cộng tác viên" // Thêm quyền mới
    };

    $.ajax({
        url: 'http://localhost:8080/DoAnLTWeb/UserManagerController',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            const tableBody = $('#userBody');
            tableBody.empty();

            data.forEach(user => {
                const permission = permissionMap[user.idPermission] || 'Không rõ';
                const verified = user.isVerified === 1 ? 'Hoạt động' : 'Không hoạt động';
                const avatar = user.avatar
                    ? `<img src="${user.avatar}" alt="Avatar" width="40" height="40" style="border-radius: 50%;">`
                    : '—';

                const row = `
                    <tr>
                        <td>${user.id}</td>
                        <td style="min-width: 90px;">${avatar}</td>
                        <td style="min-width: 100px;">${user.username}</td>
                        <td>${user.fullname || ''}</td>
                        <td style="min-width: 145px;">${user.email || ''}</td>
                        <td>${user.phone || ''}</td>
                        <td style="min-width: 70px;">${user.address || ''}</td>
                        <td>${permission}</td>
                        <td style="min-width: 100px;">${verified}</td>
                        <td>
                            <div class="d-flex gap-2 justify-content-center">
                                <button class="btn btn-sm btn-primary user-edit-btn" data-id="${user.id}">Sửa</button>
                                <button class="btn btn-sm btn-danger user-delete-btn" data-id="${user.id}">Xóa</button>
                            </div>
                        </td>
                    </tr>
                `;
                tableBody.append(row);
            });
            // Destroy bảng cũ nếu có
            if ($.fn.DataTable.isDataTable('#userTable')) {
                $('#userTable').DataTable().destroy();
            }

            $('#userTable').DataTable({
                paging: true,
                searching: true,
                ordering: true,
                lengthChange: true,
                language: {
                    lengthMenu: "Hiển thị _MENU_ người dùng mỗi trang",
                    zeroRecords: "Không tìm thấy người dùng nào",
                    info: "Hiển thị _START_ đến _END_ của _TOTAL_ người dùng",
                    infoEmpty: "Không có người dùng nào",
                    infoFiltered: "(lọc từ _MAX_ người dùng)",
                    search: "Tìm kiếm:",
                    paginate: {
                        first: "Đầu",
                        last: "Cuối",
                        next: "Sau",
                        previous: "Trước"
                    }
                }
            });
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi lấy danh sách người dùng:", error);
            console.log(xhr.responseText);
        }
    });
}

document.addEventListener('DOMContentLoaded', loadUserData);
// sửa
$(document).on('click', '.user-edit-btn', function (event) {
    event.stopPropagation();
    const row = $(this).closest('tr');
    const id = $(this).data('id');

    // Lấy text của quyền và trạng thái
    const permissionText = row.find('td:eq(7)').text().trim();
    const verifiedText = row.find('td:eq(8)').text().trim();


    // Mapping quyền (idPermission)
    const permissionMap = {
        "Admin": 1,
        "Khách hàng": 2,
        "Nhân viên": 3,
        "Cộng tác viên": 4
    };

    // Chuyển quyền và trạng thái xác thực từ text sang số
    const idPermission = permissionMap[permissionText] || 2;  // Mặc định Khách hàng

    const isVerified = verifiedText === 'Hoạt động' ? 1 : 0;
    // Gán dữ liệu vào form (chỉ 3 trường)
    $('#editUserId').val(id);
    $('#editPermission').val(idPermission);
    $('#editVerified').val(isVerified);

    // Hiển thị modal
    $('#editUserModal').modal('show');
});

$('#editUserForm').off('submit').on('submit', function (e) {
    e.preventDefault();

    const updatedUser = {
        id: parseInt($('#editUserId').val()),
        idPermission: parseInt($('#editPermission').val()),
        isVerified: parseInt($('#editVerified').val())
    };

    $.ajax({
        url: 'http://localhost:8080/DoAnLTWeb/UserManagerController',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(updatedUser),
        success: function (response) {
            alert("Cập nhật vai trò và trạng thái thành công!");
            $('#editUserModal').modal('hide');
            loadUserData();
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi cập nhật:", error);
            alert("Cập nhật thất bại!");
        }
    });
});
// Gỡ sự kiện cũ trước khi gán lại để tránh lặp
$(document).off('click', '.user-delete-btn').on('click', '.user-delete-btn', function (e) {
    e.preventDefault();
    const userId = $(this).data('id');

    if (confirm("Bạn có chắc chắn muốn xóa?")) {
        $.ajax({
            url: 'http://localhost:8080/DoAnLTWeb/UserManagerController',
            type: 'DELETE',
            contentType: 'application/json',
            data: JSON.stringify({ id: userId }),
            success: function (response) {
                alert("Người dùng đã được xóa.");
                loadUserData(); // Tải lại bảng dữ liệu
            },
            error: function (xhr, status, error) {
                console.error("Lỗi khi xóa người dùng:", error);
                alert("Xóa người dùng thất bại!");
            }
        });
    }
});

// voucher
function loadVoucherData() {
    const tableSelector = '#voucherTable';
    const table = $(tableSelector);

    // Kiểm tra xem DataTable đã tồn tại chưa và nếu có thì hủy nó trước khi khởi tạo lại
    if ($.fn.DataTable.isDataTable(tableSelector)) {
        table.DataTable().clear().destroy();
    }

    $.ajax({
        url: 'http://localhost:8080/DoAnLTWeb/VoucherController',
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            const tableBody = $('#voucherBody');
            tableBody.empty();

            const statuses = {
                1: 'Hoạt động',
                0: 'Không hoạt động'
            };

            // Hàm formatDate để định dạng ngày tháng
            function formatDate(dateString) {
                if (!dateString) return ''; // Trả về chuỗi trống nếu không có ngày
                const date = new Date(dateString);
                if (isNaN(date)) return ''; // Nếu không phải ngày hợp lệ
                // Định dạng ngày theo kiểu Việt Nam (dd/MM/yyyy)
                return date.toLocaleDateString('vi-VN');
            }

            // Duyệt qua dữ liệu voucher và hiển thị lên bảng
            data.forEach(function (voucher) {
                // const statusName = statuses[voucher.status] || 'Không xác định';
                let status = voucher.status;
                let statusName = statuses[status] || 'Không xác định';

                // const startDateObj = new Date(voucher.startDate);
                const endDateObj = new Date(voucher.endDate);

                const startDate = formatDate(voucher.startDate);
                const endDate = formatDate(voucher.endDate);

                // Nếu voucher đã hết hạn thì cập nhật trạng thái thành Không hoạt động
                const today = new Date();
                if (endDateObj < today) {
                    status = 0;
                    statusName = statuses[status];
                }

                // Kiểm tra nếu giá trị discountValue là phần trăm (giá trị nhỏ hơn 100)
                let discountDisplay = '';
                const discountValue = parseFloat(voucher.discountValue);

// Nếu <= 100 → chắc chắn là phần trăm
                if (discountValue <= 100) {
                    discountDisplay = `${discountValue}%`;
                }
// Nếu >= 1000 → chắc chắn là tiền VND
                else if (discountValue >= 1000) {
                    discountDisplay = `${discountValue.toLocaleString('vi-VN')} VND`;
                }
// Nếu trong khoảng 100 < value < 1000 → hiển thị cảnh báo hoặc giả định là phần trăm
                else {
                    discountDisplay = `<span class="text-danger">Không hợp lệ</span>`;
                    status = 0; // Chuyển trạng thái thành "Không hoạt động"
                    statusName = statuses[status];

                    console.warn(`Giá trị ${discountValue} không hợp lệ. Phải <= 100 (phần trăm) hoặc >= 1000 (VND).`);
                }


                const row = `
        <tr>
            <td>${voucher.id}</td>
            <td style="min-width: 100px;">${voucher.code}</td>
             <td>${discountDisplay}</td>
            <td>${voucher.minOrderValue.toLocaleString('vi-VN')} VND</td>
            <td>${voucher.usageLimit !== null ? voucher.usageLimit : 'Không giới hạn'}</td>
            <td>${voucher.usedCount}</td>
            <td>${voucher.maxUsagePerUser}</td>
            <td>${startDate} - ${endDate}</td>
            <td>${statusName}</td>
            <td>
                <div class="d-flex gap-2 justify-content-center">
                    <button class="btn btn-sm btn-primary voucher-edit-btn" data-id="${voucher.id}">Sửa</button>
                    <button class="btn btn-sm btn-danger voucher-delete-btn" data-id="${voucher.id}">Xóa</button>
                </div>
            </td>
        </tr>
    `;
                tableBody.append(row);
            });

            // Khởi tạo DataTable sau khi đã thêm dữ liệu vào bảng
            table.DataTable({
                destroy: true,
                paging: true,
                searching: true,
                ordering: true,
                lengthChange: true,
                language: {
                    lengthMenu: "Hiển thị _MENU_ voucher mỗi trang",
                    zeroRecords: "Không tìm thấy voucher nào",
                    info: "Hiển thị _START_ đến _END_ của _TOTAL_ voucher",
                    infoEmpty: "Không có voucher nào",
                    infoFiltered: "(lọc từ _MAX_ voucher)",
                    search: "Tìm kiếm:",
                    paginate: {
                        first: "Đầu",
                        last: "Cuối",
                        next: "Sau",
                        previous: "Trước"
                    },
                }
            });
        },
        error: function (xhr, status, error) {
            console.error("Lỗi khi lấy dữ liệu voucher:", error);
        }
    });
}

document.addEventListener('DOMContentLoaded', loadVoucherData);

// thêm voucher

$(document).ready(function () {
    $('#addVoucherForm').off('submit').on('submit', function (e) {
        e.preventDefault();

        const startDateRaw = $('#voucherStartDate').val();
        const endDateRaw = $('#voucherEndDate').val();

        if (!startDateRaw || !endDateRaw) {
            alert("Ngày bắt đầu và ngày kết thúc không được để trống!");
            return;
        }

        const startDate = new Date(startDateRaw);
        const endDate = new Date(endDateRaw);

        if (isNaN(startDate.getTime()) || isNaN(endDate.getTime())) {
            alert("Định dạng ngày không hợp lệ!");
            return;
        }

        if (endDate < startDate) {
            alert("Ngày kết thúc phải sau ngày bắt đầu!");
            return;
        }

        const discountValue = parseFloat($('#discountValue').val());
        const minOrderValue = parseFloat($('#minOrderValue').val());
        const usageLimit = $('#usageLimit').val() ? parseInt($('#usageLimit').val()) : null;
        const maxUsagePerUser = parseInt($('#maxUsagePerUser').val());
        const status = parseInt($('#status').val());
        const code = $('#voucherCode').val().trim();

        if (!code || isNaN(discountValue) || isNaN(minOrderValue) || isNaN(maxUsagePerUser)) {
            alert("Vui lòng điền đúng các trường bắt buộc!");
            return;
        }

        const voucherData = {
            code: code,
            discountValue: discountValue,
            minOrderValue: minOrderValue,
            usageLimit: usageLimit,
            maxUsagePerUser: maxUsagePerUser,
            status: status,
            startDate: startDateRaw, // 'yyyy-MM-dd'
            endDate: endDateRaw
        };

        $.ajax({
            url: 'http://localhost:8080/DoAnLTWeb/VoucherController',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(voucherData),
            success: function (response) {
                alert("Thêm voucher thành công!");
                $('#addVoucherForm')[0].reset();
                $('#addVoucherModal').modal('hide');
                loadVoucherData();
            },
            error: function (xhr) {
                const msg = xhr.responseJSON?.error || "Thêm voucher thất bại!";
                alert(msg);
                console.error("Lỗi:", xhr.responseText);
            }
        });
    });
});


// Mở modal và điền dữ liệu của voucher vào form khi người dùng nhấn nút "Sửa"
$(document).off('click', '.voucher-edit-btn').on('click', '.voucher-edit-btn', function (event) {
    event.stopPropagation();
    const row = $(this).closest('tr');
    const id = $(this).data('id');

    // Lấy dữ liệu từng cột
    const code = row.find('td:eq(1)').text().trim();
    const discountValue = row.find('td:eq(2)').text().trim();
    const minOrderValue = row.find('td:eq(3)').text().trim();
    const usageLimit = row.find('td:eq(4)').text().trim();
    const usedCount = row.find('td:eq(5)').text().trim();
    const maxUsagePerUser = row.find('td:eq(6)').text().trim(); // Lượt dùng mỗi người
    const dateRange = row.find('td:eq(7)').text().trim(); // startDate - endDate
    const status = row.find('td:eq(8)').text().trim();

    // Kiểm tra xem có phân tách ngày không
    const dateParts = dateRange.split(" - ");
    const startDate = dateParts[0] ? dateParts[0].trim() : '';
    const endDate = dateParts[1] ? dateParts[1].trim() : '';

    // Kiểm tra status và chuyển sang số
    const statusValue = (status === 'Đang hoạt động') ? 1 : 0;

    // Điền dữ liệu vào form modal
    $('#editVoucherId').val(id);
    $('#editVoucherCode').val(code);
    $('#editDiscountValue').val(
        discountValue
            .replace('%', '')
            .replace(' VND', '')
            .replace(/\./g, '') // Xóa dấu chấm ngăn cách nghìn
            .trim()
    );

    $('#editMinOrderValue').val(
        minOrderValue
            .replace(' VND', '')
            .replace(/\./g, '') // Xóa dấu chấm ngăn cách nghìn
            .replace(',', '')   // Dự phòng nếu có dấu phẩy
            .trim()
    );

    $('#editUsageLimit').val(usageLimit === 'Không giới hạn' ? '' : usageLimit); // Nếu không giới hạn thì bỏ trống
    $('#editUsedCount').val(usedCount);
    $('#editMaxUsagePerUser').val(maxUsagePerUser);  // Cập nhật Lượt dùng mỗi người
    $('#editVoucherStatus').val(statusValue);

    function convertToDateInputFormat(dateStr) {
        // Giả sử dateStr là "01/05/2025" hoặc "1/5/2025"
        const parts = dateStr.split('/');
        if (parts.length === 3) {
            const day = parts[0].padStart(2, '0');
            const month = parts[1].padStart(2, '0');
            const year = parts[2];
            return `${year}-${month}-${day}`;
        }
        return '';
    }

    $('#editVoucherStartDate').val(convertToDateInputFormat(startDate));
    $('#editVoucherEndDate').val(convertToDateInputFormat(endDate));

    // Hiển thị modal
    $('#editVoucherModal').modal('show');
});

// Xử lý sự kiện submit form sửa voucher
$('#editVoucherForm').off('submit').on('submit', function (e) {
    e.preventDefault();

    const startDateRaw = $('#editVoucherStartDate').val();
    const endDateRaw = $('#editVoucherEndDate').val();

    // Kiểm tra ngày bắt đầu và ngày kết thúc không để trống
    if (!startDateRaw || !endDateRaw) {
        alert("Ngày bắt đầu và ngày kết thúc không được để trống!");
        return;
    }

    const startDate = new Date(startDateRaw + "T00:00:00");
    const endDate = new Date(endDateRaw + "T23:59:59");

    // Kiểm tra định dạng ngày
    if (isNaN(startDate.getTime()) || isNaN(endDate.getTime())) {
        alert("Định dạng ngày không hợp lệ!");
        return;
    }

    // Kiểm tra ngày kết thúc phải sau ngày bắt đầu
    if (endDate < startDate) {
        alert("Ngày kết thúc phải sau ngày bắt đầu!");
        return;
    }

    // Lấy các giá trị từ form
    const id = parseInt($('#editVoucherId').val());
    const code = $('#editVoucherCode').val().trim();
    const discountValue = parseFloat($('#editDiscountValue').val());
    const minOrderValue = parseFloat($('#editMinOrderValue').val());
    const usageLimit = $('#editUsageLimit').val() ? parseInt($('#editUsageLimit').val()) : null;
    const usedCount = parseInt($('#editUsedCount').val());
    const maxUsagePerUser = parseInt($('#editMaxUsagePerUser').val());
    const status = parseInt($('#editVoucherStatus').val());

    // Tạo đối tượng voucher với dữ liệu đã nhập
    const updatedVoucher = {
        id: id,
        code: code,
        discountValue: discountValue,
        minOrderValue: minOrderValue,
        usageLimit: usageLimit,
        usedCount: usedCount,
        maxUsagePerUser: maxUsagePerUser,
        status: status,
        startDate: startDateRaw, // 'yyyy-MM-dd'
        endDate: endDateRaw
    };

    // Gửi dữ liệu sửa voucher lên server
    $.ajax({
        url: 'http://localhost:8080/DoAnLTWeb/VoucherController', // URL tới controller sửa voucher
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(updatedVoucher),
        success: function (response) {
            alert("Cập nhật voucher thành công!");
            $('#editVoucherModal').modal('hide'); // Đóng modal
            loadVoucherData(); // Tải lại danh sách voucher sau khi cập nhật
        },
        error: function (xhr) {
            const msg = xhr.responseJSON?.error || "Cập nhật voucher thất bại!";
            alert(msg);
            console.error("Lỗi:", xhr.responseText);
        }
    });
});

//Xóa voucher
$(document).off('click', '.voucher-delete-btn').on('click', '.voucher-delete-btn', function () {
    const id = $(this).data('id'); // Lấy ID của voucher từ data-id
	
    // Xác nhận xóa
    if (confirm("Bạn có chắc chắn muốn xóa voucher này không?")) {
        $.ajax({
            url: `http://localhost:8080/DoAnLTWeb/VoucherController?id=${id}`,
            type: 'DELETE', // Gửi yêu cầu DELETE tới VoucherController
            success: function (response) {
                alert(response.message || "Xóa voucher thành công!");
                loadVoucherData(); // Tải lại danh sách voucher sau khi xóa
            },
            error: function (xhr, status, error) {
                console.error("Lỗi khi xóa voucher:", error);
                alert("Xóa voucher thất bại hoặc không tìm thấy!");
            }
        });
    }
});
//---------------------- trang quản lý mua bán-----------------------
$(document).ready(function(){
    $.ajax({
        url: 'http://localhost:8080/DoAnLTWeb/dashboard-data', // nhớ thay đổi cho phù hợp
        method: 'GET',
        dataType: 'json',
        success: function(data) {
            console.log("Dữ liệu nhận được:", data);
            $('#totalPumps').text(data.totalPumps);
            $('#totalSales').text(data.totalSales.toLocaleString());

            var tbody = $('#orderTable tbody');
            tbody.empty();
            $.each(data.orders, function(index, order){
                var row = `<tr>
                    <td>${order.id}</td>
                    <td>${order.idUser}</td>
                    <td>${order.orderDate}</td>
                    <td>${order.quantity}</td>
                    <td>${order.totalPrice.toLocaleString()} VND</td>
                    <td>${order.status}</td>
                </tr>`;
                tbody.append(row);
            });
        },
        error: function(xhr, status, error){
            console.error("Lỗi khi tải dữ liệu: ", error);
        }
    });
});

http://localhost:8080/DoAnLTWeb/UserManagerController'












