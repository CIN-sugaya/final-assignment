package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MainCategory;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
}
