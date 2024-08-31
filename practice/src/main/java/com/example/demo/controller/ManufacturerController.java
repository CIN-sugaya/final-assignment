package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Manufacturer;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.ManufacturerService;
@Controller
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    // メーカー一覧を表示
    @GetMapping("/manufacturer/manufacturer-list")
    public String showManufacturerList(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = false;
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Admin currentAdmin = userDetails.getAdmin();
            isAdmin = currentAdmin.getPermission().getId() == 1; // 管理者権限のチェック
        }

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());

        return "manufacturer/manufacturer-list"; 
    }
   
    // メーカー詳細を表示
    @GetMapping("/manufacturer/detail")
    public String showManufacturerDetail(@RequestParam("id") Integer id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = false;
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Admin currentAdmin = userDetails.getAdmin();

            // 管理者権限のチェック
            isAdmin = currentAdmin.getPermission().getId() == 1;
        }

        model.addAttribute("isAdmin", isAdmin);

        Manufacturer manufacturer = manufacturerService.getManufacturerById(id);
        if (manufacturer != null) {
            model.addAttribute("manufacturer", manufacturer);
            return "manufacturer/manufacturer-detail"; // メーカー詳細画面のテンプレート
        } else {
            model.addAttribute("errorMessage", "メーカーが見つかりませんでした。");
            return "error/error-page"; // メーカーが存在しない場合、エラーページを表示
        }
    }
    
    // メーカー編集画面を表示
    @GetMapping("/manufacturer/edit")
    public String showEditManufacturerForm(@RequestParam("id") Integer id, Model model) {
        Manufacturer manufacturer = manufacturerService.getManufacturerById(id);
        if (manufacturer == null) {
            model.addAttribute("errorMessage", "メーカーが見つかりませんでした。");
            return "error/error-page";
        }

        model.addAttribute("manufacturer", manufacturer);

        // 管理者権限チェック
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = false;
        if (authentication != null && authentication.getPrincipal() instanceof Admin) {
            Admin currentAdmin = (Admin) authentication.getPrincipal();
            isAdmin = currentAdmin.getPermission().getId() == 1;
        }

        model.addAttribute("isAdmin", isAdmin);

        return "manufacturer/manufacturer-edit";
    }

    // メーカー情報を更新
    @PostMapping("/manufacturer/update")
    public String updateManufacturer(@RequestParam("id") Integer id,
                                     @RequestParam("manufacturerName") String manufacturerName, Model model) {
        Manufacturer manufacturer = manufacturerService.getManufacturerById(id);
        if (manufacturer == null) {
            model.addAttribute("errorMessage", "メーカーが見つかりませんでした。");
            return "error/error-page";
        }

        // バリデーションチェック
        if (manufacturerName == null || manufacturerName.isEmpty()) {
            model.addAttribute("errorMessage", "メーカー名は必須です。");
            model.addAttribute("manufacturer", manufacturer);
            return "manufacturer/manufacturer-edit";
        }

        manufacturer.setManufacturerName(manufacturerName);
        manufacturerService.updateManufacturer(manufacturer);

        return "redirect:/manufacturer/detail?id=" + id; // 編集後、詳細画面に遷移
    }
    
    @PostMapping("/manufacturer/delete")
    public String deleteManufacturer(@RequestParam("id") Integer id, Model model) {
        Manufacturer manufacturer = manufacturerService.getManufacturerById(id);
        if (manufacturer == null) {
            model.addAttribute("errorMessage", "メーカーが見つかりませんでした。");
            return "error/error-page";
        }

        manufacturerService.deleteManufacturer(id);
        return "redirect:/manufacturer/manufacturer-list"; // 削除後にメーカー一覧画面にリダイレクト
    }

    // メーカー登録画面を表示
    @GetMapping("/manufacturer/register")
    public String showManufacturerRegisterForm(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "manufacturer/manufacturer-register"; // manufacturer-register.htmlというテンプレートを表示
    }

    // メーカー情報を登録
    @PostMapping("/manufacturer/register")
    public String registerManufacturer(@RequestParam("manufacturerName") String manufacturerName, Model model) {
        // バリデーションチェック
        if (manufacturerName == null || manufacturerName.isEmpty()) {
            model.addAttribute("errorMessage", "メーカー名は必須です。");
            return "manufacturer/manufacturer-register";
        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerName(manufacturerName);
        manufacturerService.saveManufacturer(manufacturer); // メーカー情報を保存

        return "redirect:/manufacturer/manufacturer-list"; // 登録後にメーカー一覧画面に遷移
    }
}
