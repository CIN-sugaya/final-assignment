package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.OrderHistory;

public interface OrderHistoryService {

    void save(OrderHistory orderHistory);

    List<OrderHistory> getAllOrderHistories();

    OrderHistory getOrderHistoryById(Long id);
    
    List<OrderHistory> findAll();

    // 他のビジネスロジックに関連するメソッドを定義できます
}
