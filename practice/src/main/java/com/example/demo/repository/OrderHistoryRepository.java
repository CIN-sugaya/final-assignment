package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.OrderHistory;
import java.util.Optional;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    // Long型に変更
    Optional<OrderHistory> findByProductIdAndStoreId(Long productId, Long storeId);
}
