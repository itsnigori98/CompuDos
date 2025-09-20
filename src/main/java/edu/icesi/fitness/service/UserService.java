package edu.icesi.fitness.service;

import edu.icesi.fitness.model.Role;
import edu.icesi.fitness.model.User;
import edu.icesi.fitness.model.Permission;
import edu.icesi.fitness.repository.PermissionRepository;
import edu.icesi.fitness.repository.RoleRepository;
import edu.icesi.fitness.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public static final String DEFAULT_ROLE = "ROLE_USER";
    public static final String DEFAULT_VIEW_PERMISSION = "app:view";

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Transactional(readOnly = true)
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
    }

    /**
     * Crea un usuario y garantiza asignar ROLE_USER (que incluye el permiso "app:view").
     * Si vienen roles extra, los agrega si existen (o los crea si quieres activar el crear-si-no-existe).
     */
    @Transactional
    public User createUser(String name, String email, String rawPassword, Set<String> extraRoles) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name is required");
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email is required");
        if (rawPassword == null || rawPassword.isBlank()) throw new IllegalArgumentException("Password is required");

        // asegurar ROLE_USER idempotente y que tenga app:view
        Role roleUser = roleRepository.findByName(DEFAULT_ROLE).orElseGet(() -> {
            Role r = new Role();
            r.setName(DEFAULT_ROLE);
            r.setPermissions(new ArrayList<>());

            Permission view = permissionRepository.findByName(DEFAULT_VIEW_PERMISSION)
                    .orElseGet(() -> {
                        Permission p = new Permission();
                        p.setName(DEFAULT_VIEW_PERMISSION);
                        return permissionRepository.save(p);
                    });

            r.getPermissions().add(view);
            return roleRepository.save(r);
        });

        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(rawPassword); // en real, encriptar

        // siempre ROLE_USER
        if (u.getRoles() == null) u.setRoles(new ArrayList<>());
        u.getRoles().add(roleUser);

        // roles extra si vienen
        if (extraRoles != null) {
            for (String rn : extraRoles) {
                if (rn == null || rn.isBlank()) continue;
                if (DEFAULT_ROLE.equals(rn)) continue; // ya está
                roleRepository.findByName(rn).ifPresent(role -> {
                    boolean exists = u.getRoles().stream()
                            .anyMatch(r -> r.getName().equals(role.getName()));
                    if (!exists) u.getRoles().add(role);
                });
            }
        }

        return userRepository.save(u);
    }

    @Transactional
    public User setRoles(long userId, Set<String> roleNames) {
        User u = getById(userId);
        if (u.getRoles() == null) u.setRoles(new ArrayList<>());

        // Garantizar ROLE_USER
        Role roleUser = roleRepository.findByName(DEFAULT_ROLE).orElseGet(() -> {
            Role r = new Role();
            r.setName(DEFAULT_ROLE);
            r.setPermissions(new ArrayList<>());

            Permission view = permissionRepository.findByName(DEFAULT_VIEW_PERMISSION)
                    .orElseGet(() -> {
                        Permission p = new Permission();
                        p.setName(DEFAULT_VIEW_PERMISSION);
                        return permissionRepository.save(p);
                    });
            r.getPermissions().add(view);
            return roleRepository.save(r);
        });

        Set<String> toAssign = new LinkedHashSet<>();
        if (roleNames != null) toAssign.addAll(roleNames);
        toAssign.add(DEFAULT_ROLE); // siempre incluido

        List<Role> newList = new ArrayList<>();
        for (String rn : toAssign) {
            roleRepository.findByName(rn).ifPresent(newList::add);
        }
        u.setRoles(newList);
        return userRepository.save(u);
    }

    @Transactional
    public User addRole(long userId, String roleName) {
        if (roleName == null || roleName.isBlank()) {
            throw new IllegalArgumentException("Role name is required");
        }
        User u = getById(userId);
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName));
        if (u.getRoles() == null) u.setRoles(new ArrayList<>());
        boolean exists = u.getRoles().stream().anyMatch(r -> r.getName().equals(role.getName()));
        if (!exists) {
            u.getRoles().add(role);
            u = userRepository.save(u);
        }
        return u;
    }

    @Transactional
    public User removeRole(long userId, String roleName) {
        User u = getById(userId);
        if (u.getRoles() == null) return u;

        // (Opcional) impedir que se quite el DEFAULT_ROLE
        if (DEFAULT_ROLE.equals(roleName)) {
            return u; // ignorar
        }

        u.getRoles().removeIf(r -> roleName.equals(r.getName()));
        return userRepository.save(u);
    }

    @Transactional
    public void deleteUser(long id) {
        User u = getById(id);
        userRepository.delete(u);
    }

    @Transactional
    public User updateBasicInfo(long id, String name, String email, String rawPassword) {
        User u = getById(id);
        if (name != null && !name.isBlank()) u.setName(name);
        if (email != null && !email.isBlank()) u.setEmail(email);
        if (rawPassword != null && !rawPassword.isBlank()) u.setPassword(rawPassword);
        return userRepository.save(u);
    }
}
