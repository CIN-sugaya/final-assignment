package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Product;
import com.example.demo.service.MainCategoryService;
import com.example.demo.service.ManufacturerService;
import com.example.demo.service.ProductService;
import com.example.demo.service.SubCategoryService;
import com.example.demo.service.SubSubCategoryService;

@Controller
public class ProductController {

    private final ProductService productService;
    private final MainCategoryService mainCategoryService;
    private final SubCategoryService subCategoryService;
    private final SubSubCategoryService subSubCategoryService;
    private final ManufacturerService manufacturerService;

    @Autowired
    public ProductController(ProductService productService,
                             MainCategoryService mainCategoryService,
                             SubCategoryService subCategoryService,
                             SubSubCategoryService subSubCategoryService,
                             ManufacturerService manufacturerService) {
        this.productService = productService;
        this.mainCategoryService = mainCategoryService;
        this.subCategoryService = subCategoryService;
        this.subSubCategoryService = subSubCategoryService;
        this.manufacturerService = manufacturerService;
    }

    @GetMapping("product/product-list")
    public String showProductList(Model model,
                                  @RequestParam(value = "productName", required = false) String productName,
                                  @RequestParam(value = "mainCategoryId", required = false) Long mainCategoryId,
                                  @RequestParam(value = "subCategoryId", required = false) Long subCategoryId,
                                  @RequestParam(value = "subSubCategoryId", required = false) Long subSubCategoryId,
                                  @PageableDefault(size = 10) Pageable pageable) { // Pageableを追加
            
        Page<Product> products = productService.searchProducts(productName, mainCategoryId, subCategoryId, subSubCategoryId, pageable);
        model.addAttribute("products", products);
        model.addAttribute("mainCategories", mainCategoryService.getAllMainCategories());
        model.addAttribute("subCategories", subCategoryService.getAllSubCategories());
        model.addAttribute("subSubCategories", subSubCategoryService.getAllSubSubCategories());
        model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());

        return "product/product-list";
    }
}
