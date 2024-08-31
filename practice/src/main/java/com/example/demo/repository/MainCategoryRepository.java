package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.MainCategory;

@Repository
public interface MainCategoryRepository extends CrudRepository<MainCategory, Integer> {  // IDの型をIntegerに変更
}
