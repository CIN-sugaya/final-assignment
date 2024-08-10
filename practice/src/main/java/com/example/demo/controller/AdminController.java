package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Admin;
import com.example.demo.service.AdminService;
import com.example.demo.service.PermissionService;
import com.example.demo.service.PositionService;


@Controller
public class AdminController {

    private final AdminService adminService;
    private final PositionService positionService;
    private final PermissionService permissionService;

    @Autowired
    public AdminController(AdminService adminService, PositionService positionService, PermissionService permissionService) {
        this.adminService = adminService;
        this.positionService = positionService;
        this.permissionService = permissionService;
    }

    @GetMapping("/admin/login")
    public String login() {
        return "admin/login"; // テンプレートファイル名
    }
    
    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard"; // ダッシュボードページのテンプレートファイル名
    }

    @GetMapping("/admin/admin-list")
    public String showAdminList(Model model) {
        // 管理者データを取得してモデルに追加
        model.addAttribute("admins", adminService.getAllAdmins());
        return "admin/admin-list"; // Thymeleafテンプレートのパス
    }
    
    @GetMapping("/admin/admin-details")
    public String showAdminDetails(@RequestParam("adminId") Long adminId, Model model) {
        Admin admin = adminService.getAdminById(adminId);
        if (admin != null) {
            model.addAttribute("admin", admin);
            return "admin/admin-details"; // 管理者詳細画面のテンプレート
        } else {
            return "redirect:/admin/admin-list"; // 管理者が存在しない場合、一覧にリダイレクト
        }
    }
    
    @GetMapping("/admin/admin-edit")
    public String showAdminEdit(@RequestParam("adminId") Long adminId, Model model) {
        Admin admin = adminService.getAdminById(adminId);
        if (admin != null) {
            model.addAttribute("admin", admin);
            model.addAttribute("positions", positionService.getAllPositions()); // 全役職リスト
            model.addAttribute("permissions", permissionService.getAllPermissions()); // 全権限リスト
            return "admin/admin-edit"; // 管理者編集画面のテンプレート
        } else {
            return "redirect:/admin/admin-list"; // 管理者が存在しない場合、一覧にリダイレクト
        }
    }
    
    @PostMapping("/admin/update")
    public String updateAdmin(@RequestParam("adminId") Long adminId, @ModelAttribute Admin admin) {
        adminService.updateAdmin(adminId, admin);
        return "redirect:/admin/admin-details?adminId=" + adminId;
    }



}
