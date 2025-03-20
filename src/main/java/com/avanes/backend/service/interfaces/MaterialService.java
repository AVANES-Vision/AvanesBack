package com.avanes.backend.service.interfaces;

import com.avanes.backend.models.Material;
import java.util.List;

public interface MaterialService {
    Material saveMaterial(Material material);
    Material updateMaterial(Material material);
    Material getMaterialById(Integer materialId);
    List<Material> getAllMaterials();
}
