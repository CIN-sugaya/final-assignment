package com.example.demo.service;

import java.util.List;
import java.util.Optional;

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

    public List<SubSubCategory> getAllSubSubCategories() {
        return subSubCategoryRepository.findAll();
    }

    public Optional<SubSubCategory> getSubSubCategoryById(Long id) {
        return subSubCategoryRepository.findById(id);
    }

    public SubSubCategory saveSubSubCategory(SubSubCategory subSubCategory) {
        return subSubCategoryRepository.save(subSubCategory);
    }

    public void deleteSubSubCategory(Long id) {
        subSubCategoryRepository.deleteById(id);
    }
}
