package com.avanes.backend.service;

import com.avanes.backend.models.Material;
import com.avanes.backend.repository.MaterialRepository;
import com.avanes.backend.service.interfaces.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Material saveMaterial(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public Material updateMaterial(Material material) {
        return materialRepository.save(material);
    }

    @Override
    public Material getMaterialById(Integer materialId) {
        return materialRepository.findById(materialId).orElse(null);
    }

    @Override
    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }
}
