package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Store;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.StoreService;

@Controller
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }
    @GetMapping("/store/store-management")
    public String showStoreManagement(Model model) {
        // 現在の管理者を取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Admin currentAdmin = userDetails.getAdmin();
            
            // 管理者が属する店舗の情報を取得
            Long storeId = currentAdmin.getStore().getId();
            Store store = storeService.getStoreById(storeId);
            model.addAttribute("store", store);
        }

        return "store/store-management"; 
    }


    @GetMapping("/store/store-edit")
    public String showStoreEdit(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Admin currentAdmin = userDetails.getAdmin();

            // ログインしているユーザーの storeId を取得
            Long storeId = currentAdmin.getStore().getId();

            // 管理者権限のチェック
            boolean isAdmin = currentAdmin.getPermission().getId() == 1;
            model.addAttribute("isAdmin", isAdmin);

            // 編集する店舗情報を取得
            Store store = storeService.getStoreById(storeId);
            model.addAttribute("store", store);
            
            

            return "store/store-edit"; // 店舗編集画面のテンプレート
        }

        // 認証情報がない場合、エラーページまたはログインページにリダイレクト
        return "redirect:/login"; // 例としてログインページにリダイレクト
    }
}
