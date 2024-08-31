package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.SubSubCategory;
import com.example.demo.repository.SubSubCategoryRepository;

@Service
public class SubSubCategoryService {

    private final SubSubCategoryRepository subSubCategoryRepository;

    @Autowired
    public SubSubCategoryService(SubSubCategoryRepository subSubCategoryRepository) {
        this.subSubCategoryRepository = subSubCategoryRepository;
    }

    // すべての小カテゴリを取得
    public List<SubSubCategory> getAllSubSubCategories() {
        return subSubCategoryRepository.findAll();
    }

    // IDで小カテゴリを取得
    public SubSubCategory getSubSubCategoryById(Integer id) {
        return subSubCategoryRepository.findById(id).orElse(null);  // Optionalを使用して、nullを返す
    }

    // 小カテゴリを保存
    public SubSubCategory saveSubSubCategory(SubSubCategory subSubCategory) {
        return subSubCategoryRepository.save(subSubCategory);
    }

    // 小カテゴリを削除
    public void deleteSubSubCategory(Integer id) {
        subSubCategoryRepository.deleteById(id);
    }
}
