package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE " +
           "(:productName IS NULL OR p.productName LIKE %:productName%) AND " +
           "(:mainCategoryId IS NULL OR p.subSubCategory.subCategory.mainCategory.id = :mainCategoryId) AND " +
           "(:subCategoryId IS NULL OR p.subSubCategory.subCategory.id = :subCategoryId) AND " +
           "(:subSubCategoryId IS NULL OR p.subSubCategory.id = :subSubCategoryId)")
    Page<Product> findByCriteria(@Param("productName") String productName,
                                 @Param("mainCategoryId") Long mainCategoryId,
                                 @Param("subCategoryId") Long subCategoryId,
                                 @Param("subSubCategoryId") Long subSubCategoryId,
                                 Pageable pageable);
}
