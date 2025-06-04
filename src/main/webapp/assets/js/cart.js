// Hàm cập nhật tổng số lượng và tổng tiền lên UI
function updateTotals(totalQuantity, totalPrice) {
    const totalAmountElem = document.getElementById("total-amount");
    const totalPriceElem = document.getElementById("total-price");

    if (totalAmountElem && totalPriceElem) {
        totalAmountElem.innerHTML = '<strong>Tổng số lượng:</strong> ' + totalQuantity;
        totalPriceElem.innerHTML = '<strong>Tổng tiền:</strong> ' + totalPrice.toLocaleString() + ' đ';
    }
}

// Hàm tính tổng số lượng và tổng tiền, áp dụng giảm giá nếu có
function recalcTotals() {
    let totalQuantity = 0;
    let totalPrice = 0;

    const cartItems = document.querySelectorAll(".cart-item");

    if (cartItems.length === 0) {
        updateTotals(0, 0);
        return;
    }

    cartItems.forEach(item => {
        const qtyInput = item.querySelector("input.quantity-form");
        const quantity = parseInt(qtyInput.value);
        const price = parseFloat(item.dataset.price);

        if (!isNaN(quantity) && !isNaN(price)) {
            totalQuantity += quantity;
            totalPrice += quantity * price;
        }
    });

    // Kiểm tra có voucher không
    const voucherTypeElem = document.getElementById("voucher-type");
    const voucherValueElem = document.getElementById("voucher-value");

    if (voucherTypeElem && voucherValueElem) {
        const type = voucherTypeElem.value;
        const value = parseFloat(voucherValueElem.value);

        if (type === "percent") {
            const discount = totalPrice * value / 100;
            totalPrice -= discount;
        } else if (type === "amount") {
            totalPrice -= value;
        }

        if (totalPrice < 0) totalPrice = 0;
    }

    updateTotals(totalQuantity, totalPrice);
}

// Gán sự kiện 'change' cho các input số lượng
document.querySelectorAll("input.quantity-form").forEach(input => {
    input.addEventListener("change", recalcTotals);
});

// Gán sự kiện click cho các nút cập nhật giỏ hàng
document.querySelectorAll(".btn-update").forEach(btn => {
    btn.addEventListener("click", function () {
        const productId = this.dataset.id;
        const quantityInput = this.closest(".quantity-wrapper").querySelector("input.quantity-form");

        if (!quantityInput) {
            alert(" Không tìm thấy input số lượng!");
            return;
        }

        const newQuantity = parseInt(quantityInput.value);
        if (isNaN(newQuantity) || newQuantity < 1) {
            alert("Số lượng không hợp lệ!");
            return;
        }

        $.ajax({
            url: "/DoAnLTWeb/CartServlet",
            type: "POST",
            contentType: "application/x-www-form-urlencoded",
            data: {
                productId: productId,
                quantity: newQuantity,
                action: "update"
            },
            success: function(responseData) {
                if (responseData.status === "success") {
                    alert("Cập nhật giỏ hàng thành công!");
                    recalcTotals();
                } else {
                    alert(responseData.message);
                }
            },
            error: function(xhr, status, error) {
                alert(" Có lỗi xảy ra, vui lòng thử lại!");
                console.error("Lỗi:", error);
            }
        });
    });
});

// Hàm xoá sản phẩm khỏi giỏ hàng
function removeFromCart(productId) {
    $.ajax({
        url: '/DoAnLTWeb/RemoveFromCartServlet',
        type: 'POST',
        contentType: "application/x-www-form-urlencoded",
        data: { productId: productId },
        success: function(response) {
            $('#cart-item-' + productId).remove();
            recalcTotals();
            alert(response);
        },
        error: function(xhr, status, error) {
            console.error("Lỗi khi xoá:", error);
            alert(" Có lỗi xảy ra khi xoá sản phẩm!");
        }
    });
}

// Gọi khi trang load
document.addEventListener("DOMContentLoaded", function () {
    recalcTotals();
});
