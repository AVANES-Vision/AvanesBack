package com.avanes.backend.controllers;

import com.avanes.backend.models.Modules;
import com.avanes.backend.service.interfaces.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    @PostMapping
    public ResponseEntity<Modules> createModule(@RequestBody Modules module) {
        Modules savedModule = moduleService.saveModule(module);
        return ResponseEntity.ok(savedModule);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modules> updateModule(@PathVariable Integer id, @RequestBody Modules module) {
        module.setModuleId(id);
        Modules updatedModule = moduleService.updateModule(module);
        return ResponseEntity.ok(updatedModule);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modules> getModuleById(@PathVariable Integer id) {
        Modules module = moduleService.getModuleById(id);
        return ResponseEntity.ok(module);
    }

    @GetMapping
    public ResponseEntity<List<Modules>> getAllModules() {
        List<Modules> modules = moduleService.getAllModules();
        return ResponseEntity.ok(modules);
    }
}