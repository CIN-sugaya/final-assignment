package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.OrderHistory;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    // メソッド名を修正して、関連するエンティティのフィールド名を使用する
	Optional<OrderHistory> findByProduct_IdAndStore_Id(Integer productId, Integer storeId);

    // こちらは適切に定義されています
	 List<OrderHistory> findByStore_Id(Integer storeId);
}
