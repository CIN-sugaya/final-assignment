package com.example.demo.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Admin;
import com.example.demo.entity.OrderHistory;
import com.example.demo.entity.Product;
import com.example.demo.entity.Store;
import com.example.demo.entity.StoreProduct;
import com.example.demo.repository.OrderHistoryRepository;
import com.example.demo.repository.StoreProductRepository;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.AdminService;
import com.example.demo.service.MainCategoryService;
import com.example.demo.service.ManufacturerService;
import com.example.demo.service.ProductService;
import com.example.demo.service.StoreService;
import com.example.demo.service.SubCategoryService;
import com.example.demo.service.SubSubCategoryService;

@Controller
public class ProductController {

    private final ProductService productService;
    private final MainCategoryService mainCategoryService;
    private final SubCategoryService subCategoryService;
    private final SubSubCategoryService subSubCategoryService;
    private final ManufacturerService manufacturerService;
    private final AdminService adminService;
    private final OrderHistoryRepository orderHistoryRepository;
    private final StoreProductRepository storeProductRepository;

    public ProductController(ProductService productService,
                             MainCategoryService mainCategoryService,
                             SubCategoryService subCategoryService,
                             SubSubCategoryService subSubCategoryService,
                             ManufacturerService manufacturerService,
                             AdminService adminService,
                             StoreService storeService,
                             OrderHistoryRepository orderHistoryRepository,
                             StoreProductRepository storeProductRepository) {
        this.productService = productService;
        this.mainCategoryService = mainCategoryService;
        this.subCategoryService = subCategoryService;
        this.subSubCategoryService = subSubCategoryService;
        this.manufacturerService = manufacturerService;
        this.adminService = adminService;
        this.orderHistoryRepository = orderHistoryRepository;
        this.storeProductRepository = storeProductRepository;
    }

    @GetMapping("product/product-list")
    public String showProductList(Model model,
                                  @RequestParam(value = "productName", required = false) String productName,
                                  @RequestParam(value = "mainCategoryId", required = false) Integer mainCategoryId,
                                  @RequestParam(value = "subCategoryId", required = false) Integer subCategoryId,
                                  @RequestParam(value = "subSubCategoryId", required = false) Integer subSubCategoryId,
                                  @PageableDefault(size = 10) Pageable pageable) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Admin currentAdmin = userDetails.getAdmin();

            if (currentAdmin.getStore() == null || currentAdmin.getStore().getId() == 0) {
                model.addAttribute("errorMessage", "ストア情報が見つかりません。");
                return "error/error-page"; // エラーページを表示
            }

            Integer storeId = currentAdmin.getStore().getId();
            boolean isAdmin = currentAdmin.getPermission().getId() == 1;
            model.addAttribute("isAdmin", isAdmin);

            Page<Product> products = productService.searchProducts(productName, mainCategoryId, subCategoryId, subSubCategoryId, pageable);
            
            model.addAttribute("products", products);
            model.addAttribute("mainCategories", mainCategoryService.getAllMainCategories());
            model.addAttribute("subCategories", subCategoryService.getAllSubCategories());
            model.addAttribute("subSubCategories", subSubCategoryService.getAllSubSubCategories());
            model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
        }

        return "product/product-list";
    }

    @GetMapping("/product/detail")
    public String showProductDetail(@RequestParam("productId") Integer productId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Admin currentAdmin = userDetails.getAdmin();
            Integer storeId = currentAdmin.getStore().getId(); // storeIdを取得

            Optional<Product> optionalProduct = productService.getProductById(productId);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                Integer stockQuantity = productService.getStockQuantityForStore(productId, storeId); // storeIdを追加

                model.addAttribute("product", product);
                model.addAttribute("stockQuantity", stockQuantity);
            } else {
                model.addAttribute("errorMessage", "商品が見つかりませんでした。");
                return "error/error-page"; // エラーページを指定してください
            }
        }

        return "product/product-detail";
    }

    @GetMapping("/product/order")
    public String showOrderPage(@RequestParam("productId") Integer productId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Admin currentAdmin = userDetails.getAdmin();
            Integer storeId = currentAdmin.getStore().getId(); // storeIdを取得

            Optional<Product> optionalProduct = productService.getProductById(productId);

            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                Integer stockQuantity = productService.getStockQuantityForStore(productId, storeId); // storeIdを追加

                model.addAttribute("product", product);
                model.addAttribute("stockQuantity", stockQuantity);

                return "product/product-order"; // 商品発注ページを表示
            } else {
                model.addAttribute("errorMessage", "商品が見つかりませんでした。");
                return "error/error-page"; // エラーページを指定してください
            }
        }

        model.addAttribute("errorMessage", "認証されていないユーザーです。");
        return "error/error-page"; // エラーページを指定してください
    }

    @PostMapping("/product/order")
    public String orderProduct(@RequestParam("productId") Integer productId,
                               @RequestParam("orderQuantity") Integer orderQuantity,
                               Model model, Principal principal) {

        Optional<Product> optionalProduct = productService.getProductById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // 商品情報をモデルに追加
            model.addAttribute("product", product);

            // Optionalを使用してAdminを取得する
            Optional<Admin> optionalAdmin = adminService.findByEmail(principal.getName());
            if (optionalAdmin.isPresent()) {
                Admin admin = optionalAdmin.get();  // OptionalからAdminを取得
                Store store = admin.getStore();

                BigDecimal suggestedRetailPrice = BigDecimal.valueOf(product.getSuggestedRetailPrice());
                BigDecimal totalPrice = suggestedRetailPrice.multiply(BigDecimal.valueOf(orderQuantity));

                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setAdmin(admin);  // Adminエンティティ全体を設定
                orderHistory.setProduct(product);  // Productエンティティ全体を設定
                orderHistory.setStore(store);  // Storeエンティティ全体を設定
                orderHistory.setOrderQuantity(orderQuantity);
                orderHistory.setTotalPrice(totalPrice);
                orderHistory.setCreatedAt(LocalDateTime.now());  // 現在の日時を設定

                orderHistoryRepository.save(orderHistory);

                Optional<StoreProduct> existingStoreProduct = storeProductRepository.findByStoreIdAndProductId(store.getId(), product.getProductId());

                if (existingStoreProduct.isPresent()) {
                    StoreProduct storeProduct = existingStoreProduct.get();
                    storeProduct.setStockQuantity(storeProduct.getStockQuantity() + orderQuantity);
                    storeProductRepository.save(storeProduct);
                } else {
                    StoreProduct newStoreProduct = new StoreProduct();
                    newStoreProduct.setStore(store);  // Storeエンティティを直接設定
                    newStoreProduct.setProduct(product);  // Productエンティティを直接設定
                    newStoreProduct.setSellingPrice(suggestedRetailPrice.doubleValue());
                    newStoreProduct.setStockQuantity(orderQuantity);
                    storeProductRepository.save(newStoreProduct);
                }

                model.addAttribute("orderQuantity", orderQuantity);
                model.addAttribute("totalPrice", totalPrice);
                model.addAttribute("message", "注文が正常に処理されました。");
                return "product/order-confirmation"; // 注文確認ページを表示

            } else {
                model.addAttribute("errorMessage", "管理者情報が見つかりませんでした。");
                return "error/error-page"; // エラーページを指定してください
            }
        } else {
            model.addAttribute("errorMessage", "商品が見つかりませんでした。");
            return "error/error-page"; // エラーページを指定してください
        }
    }

}
