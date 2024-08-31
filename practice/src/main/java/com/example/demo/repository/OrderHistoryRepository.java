package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderHistory;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Integer> {

    // 商品IDと店舗IDでOrderHistoryを検索するメソッド
    Optional<OrderHistory> findByProduct_IdAndStore_Id(Integer productId, Integer storeId);

    // 店舗IDでOrderHistoryのリストを取得するメソッド
    List<OrderHistory> findByStore_Id(Integer storeId);

}
