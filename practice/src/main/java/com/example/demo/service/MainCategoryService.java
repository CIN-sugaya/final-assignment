package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.MainCategory;

public interface MainCategoryService {
    List<MainCategory> getAllMainCategories();
    Optional<MainCategory> getMainCategoryById(Integer id);  // Optional<MainCategory>に変更
    MainCategory saveMainCategory(MainCategory mainCategory);
    void deleteMainCategory(Integer id);
}

