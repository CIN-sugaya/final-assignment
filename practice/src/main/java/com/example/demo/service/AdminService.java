package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Admin;

public interface AdminService {
    List<Admin> getAllAdmins();
    Admin getAdminById(Long id);
    Admin createAdmin(Admin admin);
    Admin updateAdmin(Long id, Admin admin);
    void deleteAdmin(Long id);
    
    // 追加: ストアIDでフィルタリングするメソッドの宣言
    List<Admin> getAdminsByStoreId(Long storeId);
}
