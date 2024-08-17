package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Store;
import com.example.demo.repository.StoreRepository;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public Store getStoreById(Long storeId) {
        return storeRepository.findById(storeId).orElse(null);
    }

    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public Store updateStore(Store store) {
        return storeRepository.save(store); // 変更を保存
    }
    public void saveStore(Store store) {
        storeRepository.save(store);
    }
    
}
