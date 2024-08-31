package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Product;

public interface ProductService {
    Optional<Product> findById(Integer productId);
    Optional<Product> getProductById(Integer productId);
    Integer getStockQuantityForStore(Integer productId, Integer storeId);
    void increaseStockQuantity(Integer productId, Integer storeId, Integer quantity);
    void updateStockQuantity(Integer productId, Integer storeId, Integer quantity);
    Page<Product> searchProducts(String productName, Integer mainCategoryId, Integer subCategoryId, Integer subSubCategoryId, Pageable pageable);
    Map<String, List<Product>> getProductsAndCategories();
}
