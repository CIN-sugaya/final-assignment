package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(Integer id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        return optionalAdmin.orElse(null);
    }

    @Override
    public Admin createAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(Integer id, Admin admin) {
        Admin existingAdmin = getAdminById(id);
        if (existingAdmin != null) {
            existingAdmin.setFirstName(admin.getFirstName());
            existingAdmin.setLastName(admin.getLastName());
            existingAdmin.setEmail(admin.getEmail());
            existingAdmin.setPhone(admin.getPhone());
            existingAdmin.setPosition(admin.getPosition());
            existingAdmin.setPermission(admin.getPermission());
            existingAdmin.setStore(admin.getStore());

            if (!admin.getPassword().equals(existingAdmin.getPassword())) {
                existingAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }

            return adminRepository.save(existingAdmin);
        }
        return null;
    }

    @Override
    public void deleteAdmin(Integer id) {
        adminRepository.deleteById(id);
    }

    @Override
    public List<Admin> getAdminsByStoreId(Integer storeId) {
        return adminRepository.findByStoreId(storeId);
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public Optional<Admin> findById(Integer id) {
        return adminRepository.findById(id);
    }
}
