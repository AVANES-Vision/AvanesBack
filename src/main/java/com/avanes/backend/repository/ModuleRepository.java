package com.avanes.backend.repository;

import com.avanes.backend.models.Modules;
import com.avanes.backend.models.PaymentExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Modules, Integer> {
    List<Modules> findByModuleId(Long moduleId);
}