
package com.example.doanltweb.controller;

import java.io.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.example.doanltweb.dao.ProductDao;
import com.example.doanltweb.dao.StockInDao;
import com.example.doanltweb.dao.model.Product;
import com.example.doanltweb.dao.model.StockIn;
import com.example.doanltweb.dao.model.Supplier;
import com.example.doanltweb.service.ProductService;
import com.example.doanltweb.service.SupplierService;
import com.example.doanltweb.utils.OrderUtils;
import com.example.doanltweb.utils.StockInUtils;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(name = "ProductServlet", value = "/trangchu")
public class ProductServlet extends HttpServlet {
    StockInUtils stockInUtils = new StockInUtils();
    OrderUtils orderUtils = new OrderUtils();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ProductService productService = new ProductService();
        List<Product> all = productService.getAllProducts();

        Map<Integer,Integer> stockInMap = stockInUtils.stockInRecord();
        Map<Integer, Integer> detailMap = orderUtils.orderRecord();
        Map<Product, Integer> remainingMap = new HashMap<>();
        for (Product product : all) {
            int imported = stockInMap.getOrDefault(product.getId(), 0);
            int sold = detailMap.getOrDefault(product.getId(), 0);
            int remaining = imported - sold;
            product.setStock(remaining);
        }

        request.setAttribute("stockRemainList", remainingMap);
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Chỉ lấy tối đa 5 sản phẩm
        int limit = Math.min(all.size(), 4);

        for (int i = 0; i < limit; i++) {
            Product p = all.get(i);
            out.println("<div class='col-md-3'>");

            out.println("<div class='card position-relative'>");
            out.println("<div class='discount-badge'>-10%</div>");
            out.println("<a href='chitietsanpham?id=" + p.getId() + "' style='text-decoration: none'>");
            out.println("<img src='assets/imgs/maybom/" + p.getImage() + "' class='card-img-top' alt='" + p.getNameProduct() + "' />");
            out.println("</a>");
            out.println("<div class='card-body themaybom' style='height: 200px'>");
            out.println("<h6 class='card-title'>" + p.getNameProduct() + "</h6>");
            if (p.getStock() > 0) {
                out.println("<p class='new-price'>Giá bán: " + NumberFormat.getInstance(new Locale("vi", "VN")).format((long)p.getPriceProduct()) + " vnđ</p>");
                out.println("<a href='chitietsanpham?id=" + p.getId() + "' class='btn btn-primary btn-sm'>Xem ngay</a>");
                out.println("<button class='btn btn-danger btn-sm ms-4' onclick='addToCart(" + p.getId() + ")'>Mua ngay</button>");
            } else {
                out.println("<p class='text-danger'>Hết hàng</p>");
            }

            out.println("</div></div></div>");

        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

    }
}
