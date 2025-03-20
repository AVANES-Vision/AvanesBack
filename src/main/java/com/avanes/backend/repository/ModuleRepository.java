package com.avanes.backend.repository;

import com.avanes.backend.models.Modules;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Modules, Integer> {
}