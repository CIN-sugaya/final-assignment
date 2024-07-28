package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Admin;

public interface AdminService {
    List<Admin> getAllAdmins();
    Admin getAdminById(int id);
    Admin createAdmin(Admin admin);
    Admin updateAdmin(int id, Admin admin);
    void deleteAdmin(int id);
}
