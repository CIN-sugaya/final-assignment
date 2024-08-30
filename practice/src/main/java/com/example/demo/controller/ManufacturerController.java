package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Admin;
import com.example.demo.service.ManufacturerService;

@Controller
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    // メーカー一覧を表示
    @GetMapping("/manufacturer/manufacturer-list")
    public String showManufacturerList(Model model) {
        // 現在の認証情報を取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin currentAdmin = (Admin) authentication.getPrincipal(); // 現在のユーザー（Admin）を取得
        
        // 管理者権限のチェック
        boolean isAdmin = currentAdmin.getPermission().getId() == 1;
        model.addAttribute("isAdmin", isAdmin);
        
        // 全メーカーを取得してモデルに追加
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());

        return "manufacturer/manufacturer-list"; // テンプレート名
    }
}
