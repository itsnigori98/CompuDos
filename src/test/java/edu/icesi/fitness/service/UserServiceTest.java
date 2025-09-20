package edu.icesi.fitness.service;

import edu.icesi.fitness.model.Permission;
import edu.icesi.fitness.model.Role;
import edu.icesi.fitness.model.User;
import edu.icesi.fitness.repository.PermissionRepository;
import edu.icesi.fitness.repository.RoleRepository;
import edu.icesi.fitness.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static edu.icesi.fitness.service.UserService.DEFAULT_ROLE;
import static edu.icesi.fitness.service.UserService.DEFAULT_VIEW_PERMISSION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock UserRepository userRepository;
    @Mock RoleRepository roleRepository;
    @Mock PermissionRepository permissionRepository;

    @InjectMocks UserService userService;

    // Helpers
    private Role roleUserWithDefaultPermission() {
        Role r = new Role();
        r.setName(DEFAULT_ROLE);
        r.setPermissions(new ArrayList<>());
        Permission view = new Permission();
        view.setName(DEFAULT_VIEW_PERMISSION);
        r.getPermissions().add(view);
        return r;
    }

    @Test
    void createUser_creaRoleUserSiNoExiste_yAsigna() {
        // ROLE_USER no existe
        when(roleRepository.findByName(DEFAULT_ROLE)).thenReturn(Optional.empty());

        // crear permiso default si no existe
        when(permissionRepository.findByName(DEFAULT_VIEW_PERMISSION)).thenReturn(Optional.empty());
        when(permissionRepository.save(any(Permission.class))).thenAnswer(inv -> inv.getArgument(0));

        // guardar rol recién creado
        when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));

        // guardar user
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User u = userService.createUser("Ana", "ana@ex.com", "123", null);

        assertEquals("Ana", u.getName());
        assertTrue(u.getRoles().stream().anyMatch(r -> DEFAULT_ROLE.equals(r.getName())));
        // y que el rol tenga app:view
        Role roleUser = u.getRoles().stream().filter(r -> DEFAULT_ROLE.equals(r.getName())).findFirst().orElseThrow();
        assertTrue(roleUser.getPermissions().stream().anyMatch(p -> DEFAULT_VIEW_PERMISSION.equals(p.getName())));
    }

    @Test
    void createUser_usaRoleUserExistente() {
        Role roleUser = roleUserWithDefaultPermission();
        when(roleRepository.findByName(DEFAULT_ROLE)).thenReturn(Optional.of(roleUser));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User u = userService.createUser("Ana", "ana@ex.com", "123", null);

        assertTrue(u.getRoles().contains(roleUser));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void createUser_agregaRolesExtrasSiExisten() {
        Role roleUser = roleUserWithDefaultPermission();
        Role admin = new Role();
        admin.setName("ADMIN");

        when(roleRepository.findByName(DEFAULT_ROLE)).thenReturn(Optional.of(roleUser));
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(admin));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User u = userService.createUser("Ana", "ana@ex.com", "123", Set.of("ADMIN"));

        assertTrue(u.getRoles().stream().anyMatch(r -> "ADMIN".equals(r.getName())));
    }

    @Test
    void setRoles_siempreIncluyeRoleUser() {
        User u = new User();
        u.setId(1L);
        u.setRoles(new ArrayList<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        // role user existe
        Role roleUser = roleUserWithDefaultPermission();
        when(roleRepository.findByName(DEFAULT_ROLE)).thenReturn(Optional.of(roleUser));

        // otro rol existe
        Role admin = new Role();
        admin.setName("ADMIN");
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(admin));

        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User out = userService.setRoles(1L, Set.of("ADMIN")); // no incluye ROLE_USER

        assertTrue(out.getRoles().stream().anyMatch(r -> DEFAULT_ROLE.equals(r.getName())));
        assertTrue(out.getRoles().stream().anyMatch(r -> "ADMIN".equals(r.getName())));
    }

    @Test
    void addRole_agregaSiExiste() {
        User u = new User();
        u.setId(1L);
        u.setRoles(new ArrayList<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        Role admin = new Role();
        admin.setName("ADMIN");
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(admin));

        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User out = userService.addRole(1L, "ADMIN");
        assertTrue(out.getRoles().stream().anyMatch(r -> "ADMIN".equals(r.getName())));
    }

    @Test
    void removeRole_noQuitaRoleUser() {
        // Usuario con ROLE_USER
        User u = new User();
        u.setId(1L);
        Role roleUser = new Role();
        roleUser.setName(DEFAULT_ROLE);
        u.setRoles(new ArrayList<>(List.of(roleUser)));

        when(userRepository.findById(1L)).thenReturn(Optional.of(u));
        // No stubeamos save porque no debería llamarse si no hay cambios

        // Act
        User out = userService.removeRole(1L, DEFAULT_ROLE);

        // Assert: ROLE_USER debe permanecer
        assertTrue(out.getRoles().stream().anyMatch(r -> DEFAULT_ROLE.equals(r.getName())));
        // Y NO debe haberse llamado a save
        verify(userRepository, never()).save(any(User.class));
    }



    @Test
    void listAll_delegaAlRepo() {
        when(userRepository.findAll()).thenReturn(List.of(new User(), new User()));
        assertEquals(2, userService.listAll().size());
    }

    @Test
    void getById_ok() {
        User u = new User();
        u.setId(5L);
        when(userRepository.findById(5L)).thenReturn(Optional.of(u));

        User out = userService.getById(5L);
        assertSame(u, out);
    }

    @Test
    void getById_noExiste_lanza() {
        when(userRepository.findById(7L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.getById(7L));
    }

    @Test
    void updateBasicInfo_actualizaCampos() {
        User u = new User();
        u.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(u));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User out = userService.updateBasicInfo(1L, "Nuevo", "nuevo@ex.com", "pwd");
        assertEquals("Nuevo", out.getName());
        assertEquals("nuevo@ex.com", out.getEmail());
        assertEquals("pwd", out.getPassword());
    }

    @Test
    void deleteUser_ok() {
        User u = new User();
        u.setId(9L);
        when(userRepository.findById(9L)).thenReturn(Optional.of(u));

        userService.deleteUser(9L);

        verify(userRepository).delete(u);
    }
}
