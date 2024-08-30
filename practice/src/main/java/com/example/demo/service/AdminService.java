package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Admin;

public interface AdminService {
    List<Admin> getAllAdmins();
    Admin getAdminById(Integer id);  // LongからIntegerに変更
    Admin createAdmin(Admin admin);
    Admin updateAdmin(Integer id, Admin admin);  // LongからIntegerに変更
    void deleteAdmin(Integer id);  // LongからIntegerに変更
    List<Admin> getAdminsByStoreId(Integer storeId);  // LongからIntegerに変更
    Optional<Admin> findById(Integer id); 
    Optional<Admin> findByEmail(String email);  // こちらを残す
}
