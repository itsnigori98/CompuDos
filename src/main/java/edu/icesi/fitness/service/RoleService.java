package edu.icesi.fitness.service;

import edu.icesi.fitness.model.Permission;
import edu.icesi.fitness.model.Role;
import edu.icesi.fitness.repository.PermissionRepository;
import edu.icesi.fitness.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    // Permiso por defecto de solo visualización
    public static final String DEFAULT_VIEW_PERMISSION = "app:view";

    public RoleService(RoleRepository roleRepository,
                       PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> listAll() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Role getByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
    }

    /**
     * Crea el rol si no existe (idempotente) y garantiza que tenga el permiso DEFAULT_VIEW_PERMISSION.
     * Si se pasan permisos, se agregan (creándolos si no existen).
     */
    @Transactional
    public Role createRole(String roleName, Set<String> permissionNames) {
        if (roleName == null || roleName.isBlank()) {
            throw new IllegalArgumentException("Role name is required");
        }

        Role role = roleRepository.findByName(roleName).orElse(null);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            role.setPermissions(new ArrayList<>());
        } else if (role.getPermissions() == null) {
            role.setPermissions(new ArrayList<>());
        }

        // Asegurar permiso por defecto
        Permission defaultView = permissionRepository.findByName(DEFAULT_VIEW_PERMISSION)
                .orElseGet(() -> {
                    Permission np = new Permission();
                    np.setName(DEFAULT_VIEW_PERMISSION);
                    return permissionRepository.save(np);
                });

        // Añadir el default si no está
        if (role.getPermissions().stream().noneMatch(p -> DEFAULT_VIEW_PERMISSION.equals(p.getName()))) {
            role.getPermissions().add(defaultView);
        }

        // Agregar adicionales (si vienen)
        if (permissionNames != null) {
            for (String pn : permissionNames) {
                if (pn == null || pn.isBlank()) continue;
                if (DEFAULT_VIEW_PERMISSION.equals(pn)) continue; // ya garantizado

                Permission p = permissionRepository.findByName(pn)
                        .orElseGet(() -> {
                            Permission np = new Permission();
                            np.setName(pn);
                            return permissionRepository.save(np);
                        });

                // evitar duplicados por nombre
                boolean exists = role.getPermissions().stream()
                        .anyMatch(existing -> existing.getName().equals(p.getName()));
                if (!exists) {
                    role.getPermissions().add(p);
                }
            }
        }

        return roleRepository.save(role);
    }

    /**
     * Reemplaza la lista de permisos por los indicados, siempre conservando DEFAULT_VIEW_PERMISSION.
     */
    @Transactional
    public Role setPermissions(String roleName, Set<String> permissionNames) {
        Role role = getByName(roleName);

        // Siempre incluir el permiso por defecto
        Set<String> names = new LinkedHashSet<>();
        if (permissionNames != null) names.addAll(permissionNames);
        names.add(DEFAULT_VIEW_PERMISSION);

        List<Permission> newList = new ArrayList<>();
        for (String n : names) {
            Permission p = permissionRepository.findByName(n)
                    .orElseGet(() -> {
                        Permission np = new Permission();
                        np.setName(n);
                        return permissionRepository.save(np);
                    });
            newList.add(p);
        }

        role.setPermissions(newList);
        return roleRepository.save(role);
    }

    @Transactional
    public Role addPermission(String roleName, String permissionName) {
        Role role = getByName(roleName);
        if (permissionName == null || permissionName.isBlank()) {
            throw new IllegalArgumentException("Permission name is required");
        }

        Permission p = permissionRepository.findByName(permissionName)
                .orElseGet(() -> {
                    Permission np = new Permission();
                    np.setName(permissionName);
                    return permissionRepository.save(np);
                });

        if (role.getPermissions() == null) role.setPermissions(new ArrayList<>());
        boolean exists = role.getPermissions().stream().anyMatch(x -> x.getName().equals(p.getName()));
        if (!exists) {
            role.getPermissions().add(p);
            role = roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    public Role removePermission(String roleName, String permissionName) {
        Role role = getByName(roleName);
        if (role.getPermissions() == null) return role;

        // Evitar quitar el permiso por defecto
        if (DEFAULT_VIEW_PERMISSION.equals(permissionName)) {
            return role;
        }

        role.getPermissions().removeIf(p -> permissionName.equals(p.getName()));
        return roleRepository.save(role);
    }

    @Transactional
    public void delete(String roleName) {
        Role role = getByName(roleName);
        roleRepository.delete(role);
    }
}
