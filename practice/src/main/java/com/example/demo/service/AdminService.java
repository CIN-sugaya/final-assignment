package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Admin;

public interface AdminService {
    List<Admin> getAllAdmins();
    Admin getAdminById(Integer id);  // LongからIntegerに変更
    Admin createAdmin(Admin admin);
    Admin updateAdmin(Integer id, Admin admin);  // LongからIntegerに変更
    Admin findByEmail(String email);
    void deleteAdmin(Integer id);  // LongからIntegerに変更
    List<Admin> getAdminsByStoreId(Integer storeId);  // LongからIntegerに変更
    Optional<Admin> findById(Integer id); 
}
