package com.example.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.entity.Product;

public interface ProductService {

    // 商品IDで商品を取得
    Optional<Product> getProductById(Integer productId);  

    // ストアと商品IDに基づいて在庫数を取得
    Integer getStockQuantityForStore(Integer productId, Integer storeId);  // storeId 引数を追加

    // 在庫数を増加させる
    void increaseStockQuantity(Integer productId, Integer storeId, Integer quantity);  

    // 在庫数を更新する
    void updateStockQuantity(Integer productId, Integer storeId, Integer quantity);  // storeId 引数を追加

    // 商品を検索するメソッド（名前やカテゴリでの検索など）
    Page<Product> searchProducts(String productName, Integer mainCategoryId, Integer subCategoryId, Integer subSubCategoryId, Pageable pageable);  
   
    Product findById(Integer id);

}
