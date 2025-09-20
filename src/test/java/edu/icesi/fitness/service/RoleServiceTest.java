package edu.icesi.fitness.service;

import edu.icesi.fitness.model.Permission;
import edu.icesi.fitness.model.Role;
import edu.icesi.fitness.repository.PermissionRepository;
import edu.icesi.fitness.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static edu.icesi.fitness.service.RoleService.DEFAULT_VIEW_PERMISSION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock RoleRepository roleRepository;
    @Mock PermissionRepository permissionRepository;

    @InjectMocks RoleService roleService;

    @Test
    void createRole_nuevo_añadeDefaultYExtras() {
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.empty());

        // default app:view no existe -> se crea
        when(permissionRepository.findByName(DEFAULT_VIEW_PERMISSION)).thenReturn(Optional.empty());
        when(permissionRepository.save(any(Permission.class))).thenAnswer(inv -> inv.getArgument(0));

        // permiso extra
        when(permissionRepository.findByName("exercise:edit")).thenReturn(Optional.empty());

        when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));

        Role r = roleService.createRole("ADMIN", Set.of("exercise:edit"));

        assertEquals("ADMIN", r.getName());
        assertNotNull(r.getPermissions());
        assertTrue(r.getPermissions().stream().anyMatch(p -> DEFAULT_VIEW_PERMISSION.equals(p.getName())));
        assertTrue(r.getPermissions().stream().anyMatch(p -> "exercise:edit".equals(p.getName())));
        // no duplicados
        long countView = r.getPermissions().stream().filter(p -> DEFAULT_VIEW_PERMISSION.equals(p.getName())).count();
        assertEquals(1, countView);
    }

    @Test
    void createRole_existente_noDuplicaDefault() {
        Role existing = new Role();
        existing.setName("ADMIN");
        existing.setPermissions(new ArrayList<>());
        Permission view = new Permission();
        view.setName(DEFAULT_VIEW_PERMISSION);
        existing.getPermissions().add(view);

        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(existing));
        when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));
        when(permissionRepository.findByName(DEFAULT_VIEW_PERMISSION)).thenReturn(Optional.of(view));

        Role r = roleService.createRole("ADMIN", Set.of()); // nada extra

        long countView = r.getPermissions().stream().filter(p -> DEFAULT_VIEW_PERMISSION.equals(p.getName())).count();
        assertEquals(1, countView);
    }

    @Test
    void setPermissions_alwaysIncluyeDefault() {
        Role existing = new Role();
        existing.setName("ADMIN");
        existing.setPermissions(new ArrayList<>());

        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(existing));
        when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));

        // default
        Permission view = new Permission();
        view.setName(DEFAULT_VIEW_PERMISSION);
        when(permissionRepository.findByName(DEFAULT_VIEW_PERMISSION)).thenReturn(Optional.of(view));

        // otro permiso
        Permission p = new Permission();
        p.setName("events:read");
        when(permissionRepository.findByName("events:read")).thenReturn(Optional.of(p));

        Role r = roleService.setPermissions("ADMIN", Set.of("events:read"));

        assertTrue(r.getPermissions().stream().anyMatch(x -> "events:read".equals(x.getName())));
        assertTrue(r.getPermissions().stream().anyMatch(x -> DEFAULT_VIEW_PERMISSION.equals(x.getName())));
    }

    @Test
    void addPermission_agregaSiNoExiste() {
        Role existing = new Role();
        existing.setName("ADMIN");
        existing.setPermissions(new ArrayList<>());

        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(existing));
        when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));

        Permission p = new Permission();
        p.setName("x");
        when(permissionRepository.findByName("x")).thenReturn(Optional.of(p));

        Role r = roleService.addPermission("ADMIN", "x");
        assertTrue(r.getPermissions().stream().anyMatch(pp -> "x".equals(pp.getName())));
    }

    @Test
    void removePermission_noQuitaDefault() {
        // Arrange
        Role existing = new Role();
        existing.setName("ADMIN");
        existing.setPermissions(new ArrayList<>());
        Permission view = new Permission();
        view.setName(RoleService.DEFAULT_VIEW_PERMISSION);
        existing.getPermissions().add(view);

        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(existing));

        // Act
        Role out = roleService.removePermission("ADMIN", RoleService.DEFAULT_VIEW_PERMISSION);

        // Assert: NO debe quitar el default
        assertTrue(out.getPermissions().stream()
                .anyMatch(p -> RoleService.DEFAULT_VIEW_PERMISSION.equals(p.getName())));

        // Como retorna temprano, típicamente NO guarda ni consulta permisos extra:
        verify(roleRepository, never()).save(any(Role.class));
        verifyNoInteractions(permissionRepository);
    }


    @Test
    void removePermission_quitaOtros() {
        Role existing = new Role();
        existing.setName("ADMIN");
        existing.setPermissions(new ArrayList<>());
        Permission p = new Permission();
        p.setName("x");
        existing.getPermissions().add(p);

        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(existing));
        when(roleRepository.save(any(Role.class))).thenAnswer(inv -> inv.getArgument(0));

        Role r = roleService.removePermission("ADMIN", "x");
        assertFalse(r.getPermissions().stream().anyMatch(pp -> "x".equals(pp.getName())));
    }

    @Test
    void getByName_noExiste_lanza() {
        when(roleRepository.findByName("NOPE")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> roleService.getByName("NOPE"));
    }
}

