package edu.icesi.fitness.service;


import edu.icesi.fitness.model.Role;
import edu.icesi.fitness.model.User;
import edu.icesi.fitness.repository.PermissionRepository;
import edu.icesi.fitness.repository.RoleRepository;
import edu.icesi.fitness.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository users;
    private final RoleRepository roles;




    public UserService(UserRepository users, RoleRepository roles) {
        this.users = users;
        this.roles = roles;
    }

    @Transactional
    public User createUser(String name, String email, String rawPassword, Set<String> roleNames) {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(rawPassword);

        // Asignar rol por defecto si no llegan roles
        if (roleNames == null || roleNames.isEmpty()) {


            Role role = roles.findByName("ROLE_USER")
                    .orElseGet(() -> roles.save(new Role("ROLE_USER")));
            u.setRole(role);



        } else {
            u.getRoles().addAll(resolveRoles(roleNames));
        }

        return users.save(u);
    }



    @Transactional
    public User assignRoles(Long userId, Set<String> roleNames) {
        if (roleNames == null || roleNames.isEmpty())
            throw new IllegalArgumentException("User must keep at least one role");

        User u = users.findById(userId).orElseThrow();
        u.getRoles().clear();
        u.getRoles().addAll(resolveRoles(roleNames));
        return u;
    }

    @Transactional
    public User addRole(Long userId, String roleName) {
        User u = users.findById(userId).orElseThrow();
        Role r = roles.findByName(roleName).orElseThrow();
        u.getRoles().add(r);
        return u;
    }

    @Transactional
    public User removeRole(Long userId, String roleName) {
        User u = users.findById(userId).orElseThrow();
        Role r = roles.findByName(roleName).orElseThrow();
        u.getRoles().remove(r);
        if (u.getRoles().isEmpty())
            throw new IllegalStateException("User cannot be left without roles");
        return u;
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return users.findByEmail(email);
    }


    private List<Role> resolveRoles(Set<String> names) {
        List<Role> out = new ArrayList<>();
        for (String n : names) {
            out.add(roles.findByName(n).orElseThrow(
                    () -> new NoSuchElementException("Role not found: " + n)));
        }
        return out;
    }

    public boolean findUserById(Long userId) {
        return users.findById(userId).isPresent();
    }
}



