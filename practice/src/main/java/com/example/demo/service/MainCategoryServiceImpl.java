package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.MainCategory;
import com.example.demo.repository.MainCategoryRepository;

@Service
public class MainCategoryServiceImpl implements MainCategoryService {

    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    @Override
    public List<MainCategory> getAllMainCategories() {
        return StreamSupport.stream(mainCategoryRepository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    @Override
    public Optional<MainCategory> getMainCategoryById(Integer id) {  // Optional<MainCategory>に変更
        return mainCategoryRepository.findById(id);
    }

    @Override
    public MainCategory saveMainCategory(MainCategory mainCategory) {
        return mainCategoryRepository.save(mainCategory);
    }

    @Override
    public void deleteMainCategory(Integer id) {
        mainCategoryRepository.deleteById(id);
    }
}

