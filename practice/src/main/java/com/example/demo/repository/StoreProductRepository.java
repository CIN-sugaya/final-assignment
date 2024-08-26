package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.StoreProduct;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, Integer> {

    @Query("SELECT sp.stockQuantity FROM StoreProduct sp WHERE sp.store.id = :storeId AND sp.product.id = :productId")
    Optional<Integer> findStockQuantityByStoreIdAndProductId(@Param("storeId") Integer storeId, @Param("productId") Integer productId);

    @Query("SELECT sp FROM StoreProduct sp WHERE sp.store.id = :storeId AND sp.product.id = :productId")
    Optional<StoreProduct> findByStoreIdAndProductId(@Param("storeId") Integer storeId, @Param("productId") Integer productId);

    @Modifying
    @Transactional
    @Query("UPDATE StoreProduct sp SET sp.stockQuantity = :newQuantity WHERE sp.store.id = :storeId AND sp.product.id = :productId")
    void updateStockQuantity(@Param("storeId") Integer storeId, @Param("productId") Integer productId, @Param("newQuantity") Integer newQuantity);
}
