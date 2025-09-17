package edu.icesi.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.fitness.model.Role;
@Repository
public interface IRoleRepository extends JpaRepository<Role, String> {}
