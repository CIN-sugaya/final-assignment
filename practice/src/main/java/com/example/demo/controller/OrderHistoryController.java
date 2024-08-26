package com.example.demo.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.OrderHistory;
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
        List<OrderHistory> orderHistories = orderHistoryService.findAll(); // エンティティの全データを取得

        // 必要なデータをマッピング
        List<Map<String, Object>> orderHistoryDtos = orderHistories.stream().map(orderHistory -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("productName", productService.findById(orderHistory.getProductId()).getProductName());
            dto.put("orderQuantity", orderHistory.getOrderQuantity());
            dto.put("adminLastName", adminService.findById(orderHistory.getAdminId()).getLastName());
            dto.put("totalAmount", orderHistory.getTotalPrice());
            dto.put("createdAt", orderHistory.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("orderHistories", orderHistoryDtos); // DTOリストをモデルに追加

        return "product/order-history-list"; // テンプレート名
    }
}
