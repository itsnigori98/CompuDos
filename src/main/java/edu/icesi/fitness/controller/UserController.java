package edu.icesi.fitness.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import edu.icesi.fitness.model.User;
import edu.icesi.fitness.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserServiceImpl service;
    public UserController(UserServiceImpl s){this.service = s;}
    @GetMapping
    public List<User> all(){ return service.getAll(); }
    @GetMapping("/{id}")
    public Optional<User> get(@PathVariable String id){ return service.getById(id); }
    @PostMapping
    public User create(@RequestBody User u){ return service.save(u); }
    @PutMapping("/{id}")
    public User update(@PathVariable String id, @RequestBody User u){
        u.setId(id);
        return service.save(u);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){ service.delete(id); }
}
