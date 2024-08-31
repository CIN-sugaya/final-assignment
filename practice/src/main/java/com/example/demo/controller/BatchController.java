package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.service.ProductService;
import com.example.demo.service.StoreService;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private final ProductService productService;
    private final StoreService storeService;

    @Autowired
    public BatchController(ProductService productService, StoreService storeService) {
        this.productService = productService;
        this.storeService = storeService;
    }

    // 商品とカテゴリの一覧データを取得
    @GetMapping("/products/categories")
    public ResponseEntity<Map<String, List<Product>>> getProductsAndCategories() {
        Map<String, List<Product>> response = productService.getProductsAndCategories();
        return ResponseEntity.ok(response);
    }

    // 店舗と発注履歴の一覧データを取得
    @GetMapping("/stores/orders")
    public ResponseEntity<Map<String, List<Store>>> getStoresAndOrders() {
        Map<String, List<Store>> response = storeService.getStoresAndOrders();
        return ResponseEntity.ok(response);
    }
}
