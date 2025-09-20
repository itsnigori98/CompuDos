package edu.icesi.fitness.service;

import edu.icesi.fitness.model.Permission;
import edu.icesi.fitness.repository.PermissionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

    @Mock
    PermissionRepository permissionRepository;

    @InjectMocks
    PermissionService permissionService;

    @Test
    void create_creaCuandoNoExiste() {
        when(permissionRepository.findByName("app:view")).thenReturn(Optional.empty());
        when(permissionRepository.save(any(Permission.class))).thenAnswer(inv -> inv.getArgument(0));

        Permission p = permissionService.create("app:view");

        assertEquals("app:view", p.getName());
        verify(permissionRepository).save(any(Permission.class));
    }

    @Test
    void create_retornaExistenteCuandoYaExiste() {
        Permission existing = new Permission();
        existing.setName("app:view");
        when(permissionRepository.findByName("app:view")).thenReturn(Optional.of(existing));

        Permission p = permissionService.create("app:view");

        assertSame(existing, p);
        verify(permissionRepository, never()).save(any());
    }

    @Test
    void listAll_delegaAlRepo() {
        when(permissionRepository.findAll()).thenReturn(List.of(new Permission()));
        assertEquals(1, permissionService.listAll().size());
    }

    @Test
    void getByName_ok() {
        Permission existing = new Permission();
        existing.setName("x");
        when(permissionRepository.findByName("x")).thenReturn(Optional.of(existing));

        Permission p = permissionService.getByName("x");
        assertSame(existing, p);
    }

    @Test
    void getByName_noExiste_lanza() {
        when(permissionRepository.findByName("x")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> permissionService.getByName("x"));
    }

    @Test
    void deleteByName_ok() {
        Permission existing = new Permission();
        existing.setName("x");
        when(permissionRepository.findByName("x")).thenReturn(Optional.of(existing));

        permissionService.deleteByName("x");

        verify(permissionRepository).delete(existing);
    }

    @Test
    void deleteByName_noExiste_lanza() {
        when(permissionRepository.findByName("x")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> permissionService.deleteByName("x"));
        verify(permissionRepository, never()).delete(any());
    }
}

