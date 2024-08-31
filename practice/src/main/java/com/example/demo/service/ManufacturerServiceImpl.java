package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Manufacturer;
import com.example.demo.repository.ManufacturerRepository;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Autowired
    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer getManufacturerById(Integer id) {
        return manufacturerRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteManufacturer(Integer id) {
        manufacturerRepository.deleteById(id);
    }
    
    @Override
    public void updateManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }
    
    @Override
    public void saveManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }
}
