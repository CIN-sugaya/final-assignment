package com.example.demo.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;
    
    @ManyToOne
    @JoinColumn(name = "sub_sub_category_id", nullable = false)
    private SubSubCategory subSubCategory;
    
    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;
    
    @Column(name = "product_name", nullable = false)
    private String productName;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "product_image")
    private String productImage;
    
    @Column(name = "purchase_price", nullable = false)
    private Double purchasePrice;
    
    @Column(name = "suggested_retail_price", nullable = false)
    private Double suggestedRetailPrice;
    
    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    
    @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
}
