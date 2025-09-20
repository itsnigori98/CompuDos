package edu.icesi.fitness.service;

import edu.icesi.fitness.model.Permission;
import edu.icesi.fitness.model.Role;
import edu.icesi.fitness.repository.PermissionRepository;
import edu.icesi.fitness.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roles;
    private final PermissionRepository perms;

    private static final String DEFAULT_VIEW_PERMISSION = "app:view";

    public RoleService(RoleRepository roles, PermissionRepository perms) {
        this.roles = roles;
        this.perms = perms;
    }

    @Transactional
    public Role createRole(String roleName, Set<String> permissionNames) {
        if (roleName == null || roleName.isBlank())
            throw new IllegalArgumentException("Role name is required");
        if (roles.existsByName(roleName))
            throw new IllegalArgumentException("Role already exists: " + roleName);

        // 1) Normaliza el set de nombres recibido (puede venir null)
        Set<String> names = (permissionNames == null) ? new java.util.LinkedHashSet<>() :
                new java.util.LinkedHashSet<>(permissionNames);

        // 2) Asegura el permiso por defecto
        names.add(DEFAULT_VIEW_PERMISSION);

        // 3) Resuelve objetos Permission (si no existen, créalos)
        java.util.List<Permission> resolved = new java.util.ArrayList<>();
        for (String n : names) {
            Permission p = perms.findByName(n).orElseGet(() -> {
                Permission np = new Permission();
                np.setName(n);
                return perms.save(np);
            });
            resolved.add(p);
        }

        // 4) Crea el rol
        Role role = new Role();
        role.setName(roleName);
        // si tu entidad Role tiene setPermissions(List<Permission>)
        role.setPermissions(resolved); // o role.getPermissions().addAll(resolved);
        return roles.save(role);
    }


    @Transactional
    public Role setPermissions(String roleName, Set<String> permissionNames) {
        Role role = roles.findByName(roleName).orElseThrow();
        if (permissionNames == null) permissionNames = java.util.Set.of();

        // fuerza incluir el default
        java.util.Set<String> names = new java.util.LinkedHashSet<>(permissionNames);
        names.add(DEFAULT_VIEW_PERMISSION);

        // reemplaza la lista
        role.getPermissions().clear();
        for (String n : names) {
            Permission p = perms.findByName(n).orElseThrow();
            role.getPermissions().add(p);
        }
        return role;
    }

    @Transactional
    public Role addPermission(String roleName, String permissionName) {
        Role role = roles.findByName(roleName).orElseThrow();
        Permission p = perms.findByName(permissionName).orElseThrow();
        role.getPermissions().add(p);
        return role;
    }

    @Transactional
    public Role removePermission(String roleName, String permissionName) {
        Role role = roles.findByName(roleName).orElseThrow();
        Permission p = perms.findByName(permissionName).orElseThrow();
        role.getPermissions().remove(p);
        if (role.getPermissions().isEmpty())
            throw new IllegalStateException("Role cannot be left without permissions");
        return role;
    }

    private List<Permission> resolvePermissions(Set<String> names) {
        List<Permission> out = new ArrayList<>();
        for (String n : names) {
            out.add(perms.findByName(n).orElseThrow(
                    () -> new NoSuchElementException("Permission not found: " + n)));
        }
        return out;
    }
}


