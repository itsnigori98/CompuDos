package edu.icesi.fitness.service;

import edu.icesi.fitness.model.Role;
import edu.icesi.fitness.model.User;
import edu.icesi.fitness.repository.RoleRepository;
import edu.icesi.fitness.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateUserWithDefaultRole() {
        // asegurar que el rol exista en BD
        Role defaultRole = new Role();
        defaultRole.setName("ROLE_USER");
        roleRepository.save(defaultRole);

        User u = userService.createUser("Juan", "juan@mail.com", "1234", Collections.emptySet());

        assertEquals("Juan", u.getName());
        assertTrue(u.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_USER")));
    }

    
    @Test
    public void findUserByEmailTest(){}

    @Test
    public void deleteUserTest(){}


    @Test
    public void addRoleUserTest(){}

    @Test
    public void resolveRolesTest(){}

    @Test
    public void deleteRoleUserTest(){}
}
