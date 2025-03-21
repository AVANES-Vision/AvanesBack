package com.avanes.backend.service;

import com.avanes.backend.models.Modules;
import com.avanes.backend.repository.ModuleRepository;
import com.avanes.backend.service.interfaces.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public Modules saveModule(Modules module) {
        return moduleRepository.save(module);
    }

    @Override
    public Modules updateModule(Modules module) {
        // Ensure the module exists before updating
        if (moduleRepository.existsById(module.getModuleId())) {
            return moduleRepository.save(module);
        } else {
            throw new RuntimeException("Module with ID " + module.getModuleId() + " not found");
        }
    }

    @Override
    public Modules getModuleById(Integer moduleId) {
        return moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module with ID " + moduleId + " not found"));
    }

    @Override
    public List<Modules> getAllModules() {
        return moduleRepository.findAll();
    }
}