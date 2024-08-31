package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Admin;
import com.example.demo.entity.OrderHistory;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.service.AdminService;
import com.example.demo.service.OrderHistoryService;
import com.example.demo.service.ProductService;

@Controller
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHistoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/product/order-history-list")
    public String showOrderHistoryList(Model model) {
        // 現在の認証情報を取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName(); // ログインしているユーザーの名前を取得

        // ログインしているユーザーのAdminエンティティを取得
        Admin currentAdmin = adminService.findByEmail(currentUserName)
                .orElseThrow(() -> new RuntimeException("ログインしている管理者が見つかりません"));

        // 自分の店舗の発注履歴のみを取得
        List<OrderHistory> orderHistories = orderHistoryService.findByStoreId(currentAdmin.getStore().getId());

        // 必要なデータをマッピング
        List<Map<String, Object>> orderHistoryDtos = orderHistories.stream().map(orderHistory -> {
            Map<String, Object> dto = new HashMap<>();

            // ProductとAdminの情報を取得
            Product product = orderHistory.getProduct();
            String productName = product != null ? product.getProductName() : "商品名が見つかりません";

            dto.put("productName", productName);
            dto.put("orderQuantity", orderHistory.getOrderQuantity());

            Admin admin = orderHistory.getAdmin();
            String adminLastName = admin != null ? admin.getLastName() : "N/A";

            dto.put("adminLastName", adminLastName);
            dto.put("totalAmount", orderHistory.getTotalPrice());
            dto.put("createdAt", orderHistory.getCreatedAt());

            // 店舗情報の追加
            Store store = orderHistory.getStore();
            String storeName = store != null ? store.getStoreName() : "店舗情報が見つかりません";
            dto.put("storeName", storeName); // 店舗名をDTOに追加

            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("orderHistories", orderHistoryDtos); // DTOリストをモデルに追加

        return "product/order-history-list"; // テンプレート名
    }
}
