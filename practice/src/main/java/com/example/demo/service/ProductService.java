package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> searchProducts(String productName, Long mainCategoryId, Long subCategoryId, Long subSubCategoryId, Pageable pageable) {
        return productRepository.findByCriteria(productName, mainCategoryId, subCategoryId, subSubCategoryId, pageable);
    }
}
