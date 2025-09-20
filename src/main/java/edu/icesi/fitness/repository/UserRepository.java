package edu.icesi.fitness.repository;


import java.util.*;
import edu.icesi.fitness.model.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u join u.roles r where r.name = :roleName")
    List<User> findAllByRole(@Param("roleName") String roleName);

    List<User> findByNameContainingIgnoreCaseOrderByIdAsc(String name);
    List<User> findByTypeOrderByIdAsc(String type);
    List<User> findBySexOrderByIdAsc(String sex);

    List<User> findByAgeBetweenOrderByAgeAsc(Integer from, Integer to);

    Object findByRoles_NameOrderByIdAsc(String roleUser);
}

