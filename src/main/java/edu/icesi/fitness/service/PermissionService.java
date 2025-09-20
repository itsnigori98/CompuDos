package edu.icesi.fitness.service;

import edu.icesi.fitness.model.Permission;
import edu.icesi.fitness.repository.PermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionService {

    private final PermissionRepository permissions;

    public PermissionService(PermissionRepository permissions) {
        this.permissions = permissions;
    }

    @Transactional
    public Permission create(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Permission name is required");
        }
        if (permissions.existsByName(name)) {
            // idempotente
            return permissions.findByName(name).orElseThrow();
        }
        Permission p = new Permission();
        p.setName(name);
        return permissions.save(p);
    }

    @Transactional(readOnly = true)
    public List<Permission> listAll() {
        return permissions.findAll();
    }
}

