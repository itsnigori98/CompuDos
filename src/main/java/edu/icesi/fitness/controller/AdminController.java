package edu.icesi.fitness.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.icesi.fitness.model.Event;
import edu.icesi.fitness.model.Exercise;
import edu.icesi.fitness.model.Notification;
import edu.icesi.fitness.model.User;
import edu.icesi.fitness.repository.EventRepository;
import edu.icesi.fitness.repository.ExerciseRepository;
import edu.icesi.fitness.repository.NotificationRepository;
import edu.icesi.fitness.repository.PermissionRepository;
import edu.icesi.fitness.repository.ProgressRepository;
import edu.icesi.fitness.repository.RoleRepository;
import edu.icesi.fitness.repository.RoutineRepository;
import edu.icesi.fitness.repository.TrainerRepository;
import edu.icesi.fitness.repository.UserRepository;
import edu.icesi.fitness.service.PermissionService;
import edu.icesi.fitness.service.RoleService;
import edu.icesi.fitness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // ======== Services (idempotentes) ========
    @Autowired private UserService userService;
    @Autowired private RoleService roleService;
    @Autowired private PermissionService permissionService;

    // ======== Repositories para queries y catálogos ========
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private PermissionRepository permissionRepository;
    @Autowired private RoutineRepository routineRepository;
    @Autowired private ExerciseRepository exerciseRepository;
    @Autowired private ProgressRepository progressRepository;
    @Autowired private NotificationRepository notificationRepository;
    @Autowired private TrainerRepository trainerRepository;
    @Autowired private EventRepository eventRepository;

    // ------------------------------------------------
    //                  QUERIES (TALLER)
    // ------------------------------------------------

    // ejercicio1: usuarios por rol "ROLE_USER"
    @GetMapping("ejercicio1")
    public ResponseEntity<?> ejercicio1() {
        var output = userRepository.findByRoles_NameOrderByIdAsc("ROLE_USER");
        return ResponseEntity.ok(output);
    }

    // ejercicio2: rutinas del usuario id=1
    @GetMapping("ejercicio2")
    public ResponseEntity<?> ejercicio2() {
        var output = routineRepository.findByUsers_IdOrderByIdAsc(1);
        return ResponseEntity.ok(output);
    }

    // ejercicio3: último progreso del usuario id=2 (ordenado por fecha desc)
    @GetMapping("ejercicio3")
    public ResponseEntity<?> ejercicio3() {
        var output = progressRepository.findTopByUser_IdOrderByDateDesc(2).orElse(null);
        return ResponseEntity.ok(output);
    }

    // ejercicio4: ejercicios cuyo nombre contiene "press"
    @GetMapping("ejercicio4")
    public ResponseEntity<?> ejercicio4() {
        var output = exerciseRepository.findByNameContainingIgnoreCaseOrderByIdAsc("press");
        return ResponseEntity.ok(output);
    }

    // ejercicio5: notificaciones del usuario id=1
    @GetMapping("ejercicio5")
    public ResponseEntity<?> ejercicio5() {
        var output = notificationRepository.findByUsers_IdOrderByIdAsc(1);
        return ResponseEntity.ok(output);
    }

    // ejercicio6: próximos eventos (desde ahora)
    @GetMapping("ejercicio6")
    public ResponseEntity<?> ejercicio6() {
        var output = eventRepository.findByDateAfterOrderByDateAsc(new Date());
        return ResponseEntity.ok(output);
    }

    // ejercicio7: eventos en los que participa el usuario id=2
    @GetMapping("ejercicio7")
    public ResponseEntity<?> ejercicio7() {
        var output = eventRepository.findByUsers_IdOrderByDateAsc(2);
        return ResponseEntity.ok(output);
    }

    // ejercicio8: usuarios por rango de edad 18..30
    @GetMapping("ejercicio8")
    public ResponseEntity<?> ejercicio8() {
        var output = userRepository.findByAgeBetweenOrderByAgeAsc(18, 30);
        return ResponseEntity.ok(output);
    }

    // ejercicio9: trainers por especialidad que contenga "Bici"
    @GetMapping("ejercicio9")
    public ResponseEntity<?> ejercicio9() {
        var output = trainerRepository.findBySpecialtyContainingIgnoreCaseOrderByIdAsc("Bici");
        return ResponseEntity.ok(output);
    }

    // ejercicio10: eventos ligados a la notificación id=1
    @GetMapping("ejercicio10")
    public ResponseEntity<?> ejercicio10() {
        var output = eventRepository.findByNotificationId_IdOrderByDateAsc(1);
        return ResponseEntity.ok(output);
    }

    // ------------------------------------------------
    //                    USERS (Admin)
    // ------------------------------------------------
    public static class CreateUserDto {
        public String name;
        public String email;
        public String password;
        public Set<String> roles; // opcional: extra roles además de ROLE_USER
    }
    public static class UpdateUserDto {
        public String name;
        public String email;
        public String password;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.listAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping(value = "/users", consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto dto) {
        User u = userService.createUser(dto.name, dto.email, dto.password, dto.roles);
        return new ResponseEntity<>(u, HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{id}", consumes = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody UpdateUserDto dto) {
        User u = userService.updateBasicInfo(id, dto.name, dto.email, dto.password);
        return ResponseEntity.ok(u);
    }

    @PutMapping(value = "/users/{id}/roles", consumes = "application/json")
    public ResponseEntity<User> setUserRoles(@PathVariable Integer id, @RequestBody Set<String> roleNames) {
        return ResponseEntity.ok(userService.setRoles(id, roleNames));
    }

    @PostMapping("/users/{id}/roles/{roleName}")
    public ResponseEntity<User> addRoleToUser(@PathVariable Integer id, @PathVariable String roleName) {
        return ResponseEntity.ok(userService.addRole(id, roleName));
    }

    @DeleteMapping("/users/{id}/roles/{roleName}")
    public ResponseEntity<User> removeRoleFromUser(@PathVariable Integer id, @PathVariable String roleName) {
        return ResponseEntity.ok(userService.removeRole(id, roleName));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------------------------------------
    //                    ROLES (Admin)
    // ------------------------------------------------
    public static class RoleCreateDto {
        public String name;
        public Set<String> permissions; // opcional; "app:view" se agrega siempre
    }
    public static class RolePermissionsDto {
        public Set<String> permissions;
    }

    @GetMapping("/roles")
    public ResponseEntity<?> listRoles() {
        return ResponseEntity.ok(roleService.listAll());
    }

    @GetMapping("/roles/{name}")
    public ResponseEntity<?> getRole(@PathVariable String name) {
        return ResponseEntity.ok(roleService.getByName(name));
    }

    @PostMapping(value = "/roles", consumes = "application/json")
    public ResponseEntity<?> createRole(@RequestBody RoleCreateDto dto) {
        if (dto == null || dto.name == null || dto.name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role name is required");
        }
        return new ResponseEntity<>(roleService.createRole(dto.name, dto.permissions), HttpStatus.CREATED);
    }

    @PutMapping(value = "/roles/{name}/permissions", consumes = "application/json")
    public ResponseEntity<?> setRolePermissions(@PathVariable String name, @RequestBody RolePermissionsDto body) {
        Set<String> perms = (body == null ? Collections.emptySet() : body.permissions);
        return ResponseEntity.ok(roleService.setPermissions(name, perms));
    }

    @PostMapping("/roles/{name}/permissions/{permissionName}")
    public ResponseEntity<?> addPermissionToRole(@PathVariable String name, @PathVariable String permissionName) {
        return ResponseEntity.ok(roleService.addPermission(name, permissionName));
    }

    @DeleteMapping("/roles/{name}/permissions/{permissionName}")
    public ResponseEntity<?> removePermissionFromRole(@PathVariable String name, @PathVariable String permissionName) {
        return ResponseEntity.ok(roleService.removePermission(name, permissionName));
    }

    @DeleteMapping("/roles/{name}")
    public ResponseEntity<Void> deleteRole(@PathVariable String name) {
        roleService.delete(name);
        return ResponseEntity.noContent().build();
    }

    // ------------------------------------------------
    //                 PERMISSIONS (Admin)
    // ------------------------------------------------
    @PostMapping(value = "/permissions", consumes = "application/json")
    public ResponseEntity<?> createPermission(@RequestBody String name) {
        if (name == null || name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Permission name is required");
        }
        return new ResponseEntity<>(permissionService.create(name), HttpStatus.CREATED);
    }

    @GetMapping("/permissions")
    public ResponseEntity<?> listPermissions() {
        return ResponseEntity.ok(permissionService.listAll());
    }

    @GetMapping("/permissions/{name}")
    public ResponseEntity<?> getPermission(@PathVariable String name) {
        return ResponseEntity.ok(permissionService.getByName(name));
    }

    @DeleteMapping("/permissions/{name}")
    public ResponseEntity<Void> deletePermission(@PathVariable String name) {
        permissionService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

    // ------------------------------------------------
//            EXERCISES (catálogo – Admin)
// ------------------------------------------------
    public static class ExerciseUpsertDto {
        public String name;
        public String type;
        public String description;
        public String videoUrl;
    }

    @PostMapping(value = "/exercises", consumes = "application/json")
    public ResponseEntity<Exercise> createExercise(@RequestBody ExerciseUpsertDto dto) {
        if (dto.name == null || dto.name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exercise.name is required");
        }
        Exercise e = new Exercise();
        e.setName(dto.name);
        e.setType(dto.type);
        e.setDescription(dto.description);
        e.setVideoUrl(dto.videoUrl);
        return new ResponseEntity<>(exerciseRepository.save(e), HttpStatus.CREATED);
    }

    @PutMapping(value = "/exercises/{id}", consumes = "application/json")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody ExerciseUpsertDto dto) {
        Exercise e = exerciseRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

        if (dto.name != null)        e.setName(dto.name);
        if (dto.type != null)        e.setType(dto.type);
        if (dto.description != null) e.setDescription(dto.description);
        if (dto.videoUrl != null)    e.setVideoUrl(dto.videoUrl);

        return ResponseEntity.ok(exerciseRepository.save(e));
    }

    @DeleteMapping("/exercises/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        if (!exerciseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found");
        }
        exerciseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }



    // ------------------------------------------------
    //               EVENTS (catálogo – Admin)
    // ------------------------------------------------
    public static class EventUpsertDto {
        public String name;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        public Date date;
        public Integer notificationId;
        public List<Integer> users; // si decides manejar participantes
    }

    @PostMapping(value = "/events", consumes = "application/json")
    public ResponseEntity<Event> createEvent(@RequestBody EventUpsertDto dto) {
        if (dto.name == null || dto.name.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event.name is required");
        }
        if (dto.date == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event.date is required (ISO-8601)");
        }
        Event ev = new Event();
        ev.setName(dto.name);
        ev.setDate(dto.date);

        if (dto.notificationId != null) {
            Notification notif = notificationRepository.findById(dto.notificationId).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.BAD_REQUEST, "Notification not found: " + dto.notificationId));
            ev.setNotificationId(notif);
        }
        return new ResponseEntity<>(eventRepository.save(ev), HttpStatus.CREATED);
    }

    @PutMapping(value = "/events/{id}", consumes = "application/json")
    public ResponseEntity<Event> updateEvent(@PathVariable Integer id, @RequestBody EventUpsertDto dto) {
        Event ev = eventRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found"));
        if (dto.name != null) ev.setName(dto.name);
        if (dto.date != null) ev.setDate(dto.date);
        if (dto.notificationId != null) {
            Notification notif = notificationRepository.findById(dto.notificationId).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.BAD_REQUEST, "Notification not found: " + dto.notificationId));
            ev.setNotificationId(notif);
        }
        return ResponseEntity.ok(eventRepository.save(ev));
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        if (!eventRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found");
        }
        eventRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
