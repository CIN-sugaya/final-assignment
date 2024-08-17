package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Admin;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.AdminService;
import com.example.demo.service.PermissionService;
import com.example.demo.service.PositionService;
import com.example.demo.service.StoreService;

@Controller
public class AdminController {

    private final AdminService adminService;
    private final PositionService positionService;
    private final PermissionService permissionService;
    private final StoreService storeService;

    @Autowired
    public AdminController(AdminService adminService, PositionService positionService, PermissionService permissionService, StoreService storeService) {
        this.adminService = adminService;
        this.positionService = positionService;
        this.permissionService = permissionService;
        this.storeService = storeService;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Admin currentAdmin = userDetails.getAdmin();

            // 管理者権限のチェック
            boolean isAdmin = currentAdmin.getPermission().getId() == 1;
            model.addAttribute("isAdmin", isAdmin);

            // ログインしている管理者のストアIDを取得
            Long storeId = currentAdmin.getStore().getId();

            // ストアIDが同じ管理者のみを取得
            model.addAttribute("admins", adminService.getAdminsByStoreId(storeId));
        }

        return "admin/admin-list"; // 管理者リスト画面のテンプレート
    }

    @GetMapping("/admin/admin-details")
    public String showAdminDetails(@RequestParam("adminId") Long adminId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Admin currentAdmin = userDetails.getAdmin();

            // 管理者権限のチェック
            boolean isAdmin = currentAdmin.getPermission().getId() == 1;
            model.addAttribute("isAdmin", isAdmin);
        }
        
        Admin admin = adminService.getAdminById(adminId);
        if (admin != null) {
            model.addAttribute("admin", admin);
            return "admin/admin-details"; // 管理者詳細画面のテンプレート
        } else {
            return "redirect:/admin/admin-list"; // 管理者が存在しない場合、一覧にリダイレクト
        }
    }

    @PostMapping("/admin/delete")
    public String deleteAdmin(@RequestParam("adminId") Long adminId, Authentication authentication) {
        if (authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            adminService.deleteAdmin(adminId);
            return "redirect:/admin/admin-list";
        }
        return "redirect:/admin/admin-details?adminId=" + adminId;
    }

    
    @GetMapping("/admin/admin-edit")
    public String showAdminEdit(@RequestParam("adminId") Long adminId, Model model) {
        Admin admin = adminService.getAdminById(adminId);
        if (admin != null) {
            model.addAttribute("admin", admin);
            model.addAttribute("positions", positionService.getAllPositions()); // 全役職リスト
            model.addAttribute("permissions", permissionService.getAllPermissions()); // 全権限リスト
            model.addAttribute("stores", storeService.getAllStores()); // 店舗リストをモデルに追加
            return "admin/admin-edit"; // 管理者編集画面のテンプレート
        } else {
            return "redirect:/admin/admin-list"; // 管理者が存在しない場合、一覧にリダイレクト
        }
    }
    
    @PostMapping("/admin/update")
    public String updateAdmin(@RequestParam("adminId") Long adminId, 
                              @RequestParam("positionId") Long positionId, 
                              @RequestParam("permissionId") Long permissionId, 
                              @RequestParam("storeId") Long storeId,  // storeId の追加
                              @ModelAttribute Admin admin) {
        admin.setPosition(positionService.getPositionById(positionId));
        admin.setPermission(permissionService.getPermissionById(permissionId));
        admin.setStore(storeService.getStoreById(storeId));  // store オブジェクトの設定
        adminService.updateAdmin(adminId, admin);
        return "redirect:/admin/admin-details?adminId=" + adminId;
    }
    
    @GetMapping("/admin/admin-register")
    public String showAdminRegister(Model model) {
        model.addAttribute("admin", new Admin());
        model.addAttribute("positions", positionService.getAllPositions());
        model.addAttribute("permissions", permissionService.getAllPermissions());
        model.addAttribute("stores", storeService.getAllStores()); // 店舗リストをモデルに追加
        return "admin/admin-register"; // 新規登録画面のテンプレートファイル名
    }


 // 新規管理者登録処理
    @PostMapping("/admin/register")
    public String registerAdmin(@RequestParam("positionId") Long positionId, 
                                @RequestParam("permissionId") Long permissionId, 
                                @RequestParam("storeId") Long storeId,  // storeId の追加
                                @ModelAttribute Admin admin) {
        admin.setPosition(positionService.getPositionById(positionId));
        admin.setPermission(permissionService.getPermissionById(permissionId));
        admin.setStore(storeService.getStoreById(storeId));  // store オブジェクトの設定
        adminService.createAdmin(admin);
        return "redirect:/admin/admin-list"; // 登録後に管理者一覧ページにリダイレクト
    }
    
    @GetMapping("/admin/profile")
    public String showProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Admin currentAdmin = userDetails.getAdmin();
            model.addAttribute("admin", currentAdmin);
        }

        return "admin/profile"; // プロフィール画面のテンプレート
    }


}
