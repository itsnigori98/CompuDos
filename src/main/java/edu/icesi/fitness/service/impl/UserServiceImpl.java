package edu.icesi.fitness.service.impl;

import org.springframework.stereotype.Service;
import edu.icesi.fitness.repository.IUserRepository;
import edu.icesi.fitness.model.User;
import java.util.*;

@Service
public class UserServiceImpl {
    private final IUserRepository userRepository;
    public UserServiceImpl(IUserRepository r){this.userRepository = r;}
    public List<User> getAll(){return userRepository.findAll();}
    public Optional<User> getById(String id){return userRepository.findById(id);}
    public User save(User u){return userRepository.save(u);}
    public void delete(String id){userRepository.deleteById(id);}
}
