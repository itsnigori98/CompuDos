package edu.icesi.fitness.controller;

import edu.icesi.fitness.model.Permission;
import edu.icesi.fitness.model.Role;
import edu.icesi.fitness.repository.*;
import edu.icesi.fitness.service.PermissionService;
import edu.icesi.fitness.service.RoleService;
import edu.icesi.fitness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import edu.icesi.fitness.model.User;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private PermissionRepository permRepo;
    @Autowired
    private RoutineRepository routineRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ProgressRepository progressRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private TrainerRepository trainerRepository;
    @Autowired
    private EventRepository eventRepository;


    // ejercicio1: Usuarios por rol "ROLE_USER"
    @GetMapping("ejercicio1")
    public ResponseEntity<?> ejercicio1() {
        var output = userRepository.findByRoles_NameOrderByIdAsc("ROLE_USER");
        return ResponseEntity.status(200).body(output);
    }

    // ejercicio2: Rutinas del usuario id=1
    @GetMapping("ejercicio2")
    public ResponseEntity<?> ejercicio2() {
        var output = routineRepository.findByUsers_IdOrderByIdAsc(1);
        return ResponseEntity.status(200).body(output);
    }

    // ejercicio3: Último progreso del usuario id=2
    @GetMapping("ejercicio3")
    public ResponseEntity<?> ejercicio3() {
        var output = progressRepository.findTopByUser_IdOrderByDateDesc(2).orElse(null);
        return ResponseEntity.status(200).body(output);
    }

    // ejercicio4: Ejercicios cuyo nombre contiene "press"
    @GetMapping("ejercicio4")
    public ResponseEntity<?> ejercicio4() {
        var output = exerciseRepository.findByNameContainingIgnoreCaseOrderByIdAsc("press");
        return ResponseEntity.status(200).body(output);
    }

    // ejercicio5: Notificaciones del usuario id=1
    @GetMapping("ejercicio5")
    public ResponseEntity<?> ejercicio5() {
        var output = notificationRepository.findByUsers_IdOrderByIdAsc(1);
        return ResponseEntity.status(200).body(output);
    }

    // ejercicio6: Próximos eventos desde ahora
    @GetMapping("ejercicio6")
    public ResponseEntity<?> ejercicio6() {
        var output = eventRepository.findByDateAfterOrderByDateAsc(new Date());
        return ResponseEntity.status(200).body(output);
    }

    // ejercicio7: Eventos en los que participa el usuario id=2
    @GetMapping("ejercicio7")
    public ResponseEntity<?> ejercicio7() {
        var output = eventRepository.findByUsers_IdOrderByDateAsc(2);
        return ResponseEntity.status(200).body(output);
    }

    // ejercicio8: Usuarios con edad entre 18 y 30
    @GetMapping("ejercicio8")
    public ResponseEntity<?> ejercicio8() {
        var output = userRepository.findByAgeBetweenOrderByAgeAsc(18, 30);
        return ResponseEntity.status(200).body(output);
    }

    // ejercicio9: Entrenadores por especialidad que contenga "Bici"
    @GetMapping("ejercicio9")
    public ResponseEntity<?> ejercicio9() {
        var output = trainerRepository.findBySpecialtyContainingIgnoreCaseOrderByIdAsc("Bici");
        return ResponseEntity.status(200).body(output);
    }

    // ejercicio10: Eventos asociados a la notificación id=1
    @GetMapping("ejercicio10")
    public ResponseEntity<?> ejercicio10() {
        var output = eventRepository.findByNotificationId_IdOrderByDateAsc(1);
        return ResponseEntity.status(200).body(output);
    }

    // -------------------------------------------------------
    // Versión con parámetros (útiles para probar libremente)
    // -------------------------------------------------------
    

    @GetMapping("routines/by-user/{userId}")
    public ResponseEntity<?> routinesByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(routineRepository.findByUsers_IdOrderByIdAsc(userId));
    }

    @GetMapping("progress/{userId}/last")
    public ResponseEntity<?> lastProgress(@PathVariable Integer userId) {
        return ResponseEntity.ok(
                progressRepository.findTopByUser_IdOrderByDateDesc(userId).orElse(null)
        );
    }

    @GetMapping("progress/{userId}/last7days")
    public ResponseEntity<?> progressLast7(@PathVariable Integer userId) {
        Calendar cal = Calendar.getInstance();
        Date to = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        Date from = cal.getTime();

        return ResponseEntity.ok(
                progressRepository.findByUser_IdAndDateBetweenOrderByDateAsc(userId, from, to)
        );
    }

    @GetMapping("exercises/search")
    public ResponseEntity<?> exercisesSearch(@RequestParam String q) {
        return ResponseEntity.ok(exerciseRepository.findByNameContainingIgnoreCaseOrderByIdAsc(q));
    }

    @GetMapping("notifications/by-user/{userId}")
    public ResponseEntity<?> notifsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(notificationRepository.findByUsers_IdOrderByIdAsc(userId));
    }

    @GetMapping("events/upcoming")
    public ResponseEntity<?> eventsUpcoming(@RequestParam(required = false) Date from) {
        return ResponseEntity.ok(eventRepository.findByDateAfterOrderByDateAsc(from != null ? from : new Date()));
    }

    @GetMapping("events/by-user/{userId}")
    public ResponseEntity<?> eventsByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(eventRepository.findByUsers_IdOrderByDateAsc(userId));
    }

    @GetMapping("events/by-notification/{notifId}")
    public ResponseEntity<?> eventsByNotification(@PathVariable Integer notifId) {
        return ResponseEntity.ok(eventRepository.findByNotificationId_IdOrderByDateAsc(notifId));
    }


    // ---- Users ----
    @GetMapping("/users")
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @GetMapping("/users/by-role/{roleName}")
    public List<User> usersByRole(@PathVariable String roleName) {
        return userRepository.findAllByRole(roleName);
    }

    // ---- Roles ----
    @GetMapping("/roles")
    public List<Role> listRoles() {
        return roleRepo.findAll();
    }

    @GetMapping("/roles/{name}")
    public Role getRole(@PathVariable String name) {
        return roleRepo.findByName(name).orElseThrow();
    }

    // ---- Permissions ----
    @GetMapping("/permissions")
    public List<Permission> listPermissions() {
        return permRepo.findAll();
    }

    @GetMapping("/permissions/{name}")
    public Permission getPermission(@PathVariable String name) {
        return permRepo.findByName(name).orElseThrow();
    }

    // DTOs para requests JSON
    public record CreateUserRequest(String name, String email, String password, Set<String> roles) {}
    public record CreateRoleRequest(String name, Set<String> permissions) {}
    public record SetPermissionsRequest(Set<String> permissions) {}

    // ---- Permissions ----
    @PostMapping(value = "/permissions", consumes = "application/json")
    public Permission createPermission(@RequestBody String name) {
        return permissionService.create(name);
    }

    // ---- Roles ----
    @PostMapping(value = "/roles", consumes = "application/json")
    public Role createRole(@RequestBody CreateRoleRequest req) {
        return roleService.createRole(req.name(), req.permissions());
    }

    @PutMapping(value = "/roles/{name}/permissions", consumes = "application/json")
    public Role setRolePermissions(@PathVariable String name, @RequestBody SetPermissionsRequest req) {
        return roleService.setPermissions(name, req.permissions());
    }

    // ---- Users ----
    @PostMapping(value = "/users", consumes = "application/json")
    public User createUser(@RequestBody CreateUserRequest req) {
        return userService.createUser(req.name(), req.email(), req.password(), req.roles());
    }

    @PutMapping(value = "/users/{id}/roles", consumes = "application/json")
    public User assignRoles(@PathVariable Long id, @RequestBody Set<String> roles) {
        return userService.assignRoles(id, roles);
    }

    @PostMapping("/users/{id}/roles/{roleName}")
    public User addRole(@PathVariable Long id, @PathVariable String roleName) {
        return userService.addRole(id, roleName);
    }

    @DeleteMapping("/users/{id}/roles/{roleName}")
    public User removeRole(@PathVariable Long id, @PathVariable String roleName) {
        return userService.removeRole(id, roleName);
    }


}


