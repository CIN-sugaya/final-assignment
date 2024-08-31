package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.MainCategory;
import com.example.demo.entity.SubCategory;
import com.example.demo.entity.SubSubCategory;
import com.example.demo.service.MainCategoryService;
import com.example.demo.service.SubCategoryService;
import com.example.demo.service.SubSubCategoryService;

@Controller
public class CategoryController {

    @Autowired
    private MainCategoryService mainCategoryService;

    @Autowired
    private SubCategoryService subCategoryService;
    
    @Autowired
    private SubSubCategoryService subSubCategoryService;  // SubSubCategoryServiceをAutowiredで注入


    // 大カテゴリ一覧を表示
    @GetMapping("/category/main-category-list")
    public String showMainCategoryList(Model model) {
        model.addAttribute("mainCategories", mainCategoryService.getAllMainCategories());
        return "category/main-category-list";  // Thymeleafテンプレート名
    }
    
    @GetMapping("/category/main-category-detail")
    public String showMainCategoryDetail(@RequestParam("id") Integer id, Model model) {
        // OptionalからMainCategoryを取得する
        Optional<MainCategory> optionalMainCategory = mainCategoryService.getMainCategoryById(id);
        
        if (optionalMainCategory.isPresent()) {
            MainCategory mainCategory = optionalMainCategory.get();
            model.addAttribute("mainCategory", mainCategory);
            return "category/main-category-detail";  // 詳細画面のテンプレート
        } else {
            model.addAttribute("errorMessage", "大カテゴリが見つかりませんでした。");
            return "error/error-page";  // エラーページのテンプレート
        }
    }
    
    // 中カテゴリ詳細を表示
    @GetMapping("/category/sub-category-detail")
    public String showSubCategoryDetail(@RequestParam("id") Integer id, Model model) {
        SubCategory subCategory = subCategoryService.getSubCategoryById(id);
        if (subCategory != null) {
            model.addAttribute("subCategory", subCategory);
            model.addAttribute("subSubCategories", subCategory.getSubSubCategories()); // 小カテゴリ一覧を追加
            return "category/sub-category-detail";  // 詳細画面のテンプレート
        } else {
            model.addAttribute("errorMessage", "中カテゴリが見つかりませんでした。");
            return "error/error-page";  // エラーページのテンプレート
        }
    }
    
    // 小カテゴリ詳細を表示
    @GetMapping("/category/sub-sub-category-detail")
    public String showSubSubCategoryDetail(@RequestParam("id") Integer id, Model model) {
        SubSubCategory subSubCategory = subSubCategoryService.getSubSubCategoryById(id);
        if (subSubCategory != null) {
            model.addAttribute("subSubCategory", subSubCategory);
            return "category/sub-sub-category-detail";  // 詳細画面のテンプレート
        } else {
            model.addAttribute("errorMessage", "小カテゴリが見つかりませんでした。");
            return "error/error-page";  // エラーページのテンプレート
        }
    }
}
