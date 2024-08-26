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
@Table(name = "store_product")
@Data
public class StoreProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_product_id")
    private Integer storeProductId;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store; // Storeエンティティへの参照

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Productエンティティへの参照

    @Column(name = "selling_price", nullable = false)
    private Double sellingPrice; // 販売価格

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity; // 在庫数

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt; // 作成日時

    @Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt; // 更新日時

    // ゲッターとセッター（Lombokの@Dataで自動生成されますが、必要に応じてカスタムすることもできます）
    
    // デフォルトコンストラクタと全てのフィールドを初期化するコンストラクタを追加することもできます。
}
