package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Optional<Product> findById(Integer productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Optional<Product> getProductById(Integer productId) {  
        return productRepository.findById(productId);
    }

    @Override
    public Integer getStockQuantityForStore(Integer productId, Integer storeId) {  
        Optional<Integer> stockQuantity = storeProductRepository.findStockQuantityByStoreIdAndProductId(storeId, productId);
        return stockQuantity.orElse(0);
    }

    @Override
    public void increaseStockQuantity(Integer productId, Integer storeId, Integer quantity) {  
        Optional<StoreProduct> storeProductOpt = storeProductRepository.findByStoreIdAndProductId(storeId, productId);
        if (storeProductOpt.isPresent()) {
            StoreProduct storeProduct = storeProductOpt.get();
            storeProduct.setStockQuantity(storeProduct.getStockQuantity() + quantity);
            storeProductRepository.save(storeProduct);
        } else {
            StoreProduct newStoreProduct = new StoreProduct();
            Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productId));
            Store store = new Store();

            store.setId(storeId);
            newStoreProduct.setStore(store);
            newStoreProduct.setProduct(product);
            newStoreProduct.setStockQuantity(quantity);
            storeProductRepository.save(newStoreProduct);
        }
    }

    @Override
    public void updateStockQuantity(Integer productId, Integer storeId, Integer quantity) {  
        storeProductRepository.updateStockQuantity(storeId, productId, quantity);
    }

    @Override
    public Page<Product> searchProducts(String productName, Integer mainCategoryId, Integer subCategoryId, Integer subSubCategoryId, Pageable pageable) {  
        return productRepository.findByCriteria(productName, mainCategoryId, subCategoryId, subSubCategoryId, pageable);
    }
    
    @Override
    public Map<String, List<Product>> getProductsAndCategories() {
        List<Product> products = productRepository.findAll(); // ここでは、全ての製品を取得します
        Map<String, List<Product>> response = new HashMap<>();
        response.put("products", products);
        return response;
    }
}
