package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.entity.StoreProduct;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.StoreProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreProductRepository storeProductRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, StoreProductRepository storeProductRepository) {
        this.productRepository = productRepository;
        this.storeProductRepository = storeProductRepository;
    }

    @Override
    public Optional<Product> getProductById(Integer productId) {  
        return productRepository.findById(productId);
    }

    @Override
    public Integer getStockQuantityForStore(Integer productId, Integer storeId) {  
        // ストアと商品IDに基づいて在庫数を取得します
        Optional<Integer> stockQuantity = storeProductRepository.findStockQuantityByStoreIdAndProductId(storeId, productId);
        return stockQuantity.orElse(0);
    }

    @Override
    public void increaseStockQuantity(Integer productId, Integer storeId, Integer quantity) {  
        // ストアと商品IDに基づいて在庫数を更新するロジック
        Optional<StoreProduct> storeProductOpt = storeProductRepository.findByStoreIdAndProductId(storeId, productId);
        if (storeProductOpt.isPresent()) {
            StoreProduct storeProduct = storeProductOpt.get();
            storeProduct.setStockQuantity(storeProduct.getStockQuantity() + quantity);
            storeProductRepository.save(storeProduct);
        } else {
            // 新しいStoreProductエンティティを作成して保存します
            StoreProduct newStoreProduct = new StoreProduct();
            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));
            Store store = new Store(); // Store IDが正しいと仮定して使用

            store.setId(storeId);
            newStoreProduct.setStore(store);
            newStoreProduct.setProduct(product);
            newStoreProduct.setStockQuantity(quantity);
            storeProductRepository.save(newStoreProduct);
        }
    }

    @Override
    public void updateStockQuantity(Integer productId, Integer storeId, Integer quantity) {  
        // ストアと商品IDに基づいて在庫数を直接設定するロジック
        storeProductRepository.updateStockQuantity(storeId, productId, quantity);
    }

    @Override
    public Page<Product> searchProducts(String productName, Integer mainCategoryId, Integer subCategoryId, Integer subSubCategoryId, Pageable pageable) {  
        // 商品を検索するためのクエリを実行します
        return productRepository.findByCriteria(productName, mainCategoryId, subCategoryId, subSubCategoryId, pageable);
    }
}
