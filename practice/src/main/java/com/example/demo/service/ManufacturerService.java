package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Manufacturer;

public interface ManufacturerService {
    List<Manufacturer> getAllManufacturers();  // 全メーカーを取得
    Manufacturer getManufacturerById(Integer id);  // IDでメーカーを取得
    void deleteManufacturer(Integer id);  // メーカーを削除
    void updateManufacturer(Manufacturer manufacturer);
    void saveManufacturer(Manufacturer manufacturer);
}
