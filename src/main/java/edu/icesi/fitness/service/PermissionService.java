package edu.icesi.fitness.service;

import edu.icesi.fitness.model.Permission;
import edu.icesi.fitness.repository.PermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public Permission create(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Permission name is required");
        }
        return permissionRepository.findByName(name)
                .orElseGet(() -> {
                    Permission p = new Permission();
                    p.setName(name);
                    return permissionRepository.save(p);
                });
    }

    @Transactional(readOnly = true)
    public List<Permission> listAll() {
        return permissionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Permission getByName(String name) {
        return permissionRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + name));
    }

    @Transactional
    public void deleteByName(String name) {
        Permission p = permissionRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + name));
        permissionRepository.delete(p);
    }
}
