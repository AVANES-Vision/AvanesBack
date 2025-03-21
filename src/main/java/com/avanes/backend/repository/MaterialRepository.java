package com.avanes.backend.repository;

import com.avanes.backend.models.Material;
import com.avanes.backend.models.Modules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    List<Material> findByMaterialId(Long materialId);
}