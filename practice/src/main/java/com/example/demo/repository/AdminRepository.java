package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    // storeIdの型をLongからIntegerに変更
    List<Admin> findByStoreId(Integer storeId);

    // メールアドレスで検索するメソッド（オプション）
    Optional<Admin> findByEmail(String email);
}
