package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.OrderHistory;
import com.example.demo.entity.Store;
import com.example.demo.repository.OrderHistoryRepository;
import com.example.demo.repository.StoreRepository;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    public StoreService(StoreRepository storeRepository, OrderHistoryRepository orderHistoryRepository) {
        this.storeRepository = storeRepository;
        this.orderHistoryRepository = orderHistoryRepository;
    }

    public Store getStoreById(Integer storeId) {
        return storeRepository.findById(storeId).orElse(null);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store updateStore(Store store) {
        return storeRepository.save(store);
    }

    public void saveStore(Store store) {
        storeRepository.save(store);
    }

    public Map<String, List<Store>> getStoresAndOrders() {
        List<Store> stores = storeRepository.findAll();
        Map<String, List<Store>> response = new HashMap<>();

        for (Store store : stores) {
            // 修正：findByStore_Id メソッドを使用
            List<OrderHistory> orderHistories = orderHistoryRepository.findByStore_Id(store.getId());
            store.setOrderHistories(orderHistories); // このメソッドがエンティティにあることを確認してください
        }

        response.put("stores", stores);
        return response;
    }
}
