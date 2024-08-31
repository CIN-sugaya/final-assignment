package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.OrderHistory;

public interface OrderHistoryService {

    void save(OrderHistory orderHistory);

    List<OrderHistory> getAllOrderHistories();

    OrderHistory getOrderHistoryById(Integer id); 
    
    List<OrderHistory> findAll();
    
    List<OrderHistory> findByStoreId(Integer storeId);

    // 他のビジネスロジックに関連するメソッドを定義できます
}
