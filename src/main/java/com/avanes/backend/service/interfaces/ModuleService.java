package com.avanes.backend.service.interfaces;

import com.avanes.backend.models.Modules;
import java.util.List;

public interface ModuleService {
    Modules saveModule(Modules module);
    Modules updateModule(Modules module);
    Modules getModuleById(Integer moduleId);
    List<Modules> getAllModules();
}
