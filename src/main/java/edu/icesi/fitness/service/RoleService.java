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

    public RoleService(RoleRepository roles, PermissionRepository perms) {
        this.roles = roles;
        this.perms = perms;
    }

    @Transactional
    public Role createRole(String roleName, Set<String> permissionNames) {
        if (roleName == null || roleName.isBlank())
            throw new IllegalArgumentException("Role name is required");
        if (permissionNames == null || permissionNames.isEmpty())
            throw new IllegalArgumentException("Role must have at least one permission");
        if (roles.existsByName(roleName))
            throw new IllegalArgumentException("Role already exists: " + roleName);

        Role role = new Role();
        role.setName(roleName);
        role.setPermissions(resolvePermissions(permissionNames));

        return roles.save(role);
    }

    @Transactional
    public Role setPermissions(String roleName, Set<String> permissionNames) {
        Role role = roles.findByName(roleName)
                .orElseThrow(() -> new NoSuchElementException("Role not found: " + roleName));

        if (permissionNames == null || permissionNames.isEmpty())
            throw new IllegalArgumentException("Role must retain at least one permission");

        role.getPermissions().clear();
        role.getPermissions().addAll(resolvePermissions(permissionNames));
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


