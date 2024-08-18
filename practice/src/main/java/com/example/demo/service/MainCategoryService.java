package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MainCategory;
import com.example.demo.repository.MainCategoryRepository;

@Service
public class MainCategoryService {

    private final MainCategoryRepository mainCategoryRepository;

    @Autowired
    public MainCategoryService(MainCategoryRepository mainCategoryRepository) {
        this.mainCategoryRepository = mainCategoryRepository;
    }

    public List<MainCategory> getAllMainCategories() {
        return mainCategoryRepository.findAll();
    }

    public Optional<MainCategory> getMainCategoryById(Long id) {
        return mainCategoryRepository.findById(id);
    }

    public MainCategory saveMainCategory(MainCategory mainCategory) {
        return mainCategoryRepository.save(mainCategory);
    }

    public void deleteMainCategory(Long id) {
        mainCategoryRepository.deleteById(id);
    }
}
