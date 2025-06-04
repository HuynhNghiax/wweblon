$(document).ready(function () {
    $.ajax({
      url: "/DoAnLTWeb/LastBoughtProductServlet",
      method: "GET",
      dataType: "json",
	  success: function (data) {
	    const content = $('#carousel-content');
	    content.empty();

	    if (data.length === 0) {
	      content.html('<div class="text-center text-muted p-4">Bạn chưa mua sản phẩm nào.</div>');
	      return;
	    }

	    let productsHtml = '';
	    $.each(data, function (i, product) {
	      productsHtml += `
	        <div class="col-md-3 text-center">
	          <img src="assets/imgs/maybom/${product.image}" class="img-fluid mb-2" alt="${product.nameProduct}" style="max-height: 150px; object-fit: contain;">
	          <h6 class="mt-2">${product.nameProduct}</h6>
	          <p class="text-muted">${product.priceProduct.toLocaleString()} VNĐ</p>
	        </div>`;
	    });

	    const item = `
	      <div class="carousel-item active">
	        <div class="row justify-content-center">
	          ${productsHtml}
	        </div>
	      </div>`;
	    
	    content.append(item);
	  },
      error: function (xhr) {
        let message = 'Đã xảy ra lỗi khi tải sản phẩm.';
        if (xhr.status === 401) {
          message = 'Bạn cần đăng nhập để xem các sản phẩm đã mua.';
        }
        $('#carousel-content').html(`<div class="text-danger text-center p-4">${message}</div>`);
      }
    });
  });