package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.SubSubCategory;

@Repository
public interface SubSubCategoryRepository extends JpaRepository<SubSubCategory, Long> {
}
