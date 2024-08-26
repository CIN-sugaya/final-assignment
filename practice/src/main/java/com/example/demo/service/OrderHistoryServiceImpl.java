package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.OrderHistory;
import com.example.demo.repository.OrderHistoryRepository;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    private final OrderHistoryRepository orderHistoryRepository;

    @Autowired
    public OrderHistoryServiceImpl(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Override
    public void save(OrderHistory orderHistory) {
        orderHistoryRepository.save(orderHistory);
    }

    @Override
    public List<OrderHistory> getAllOrderHistories() {
        return orderHistoryRepository.findAll();
    }

    @Override
    public OrderHistory getOrderHistoryById(Long id) {
        Optional<OrderHistory> optionalOrderHistory = orderHistoryRepository.findById(id);
        return optionalOrderHistory.orElse(null);
    }

    @Override
    public List<OrderHistory> findAll() {
        return orderHistoryRepository.findAll();
    }
    // その他のビジネスロジックメソッドを実装することができます
}
