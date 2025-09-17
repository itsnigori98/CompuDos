package edu.icesi.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.fitness.model.User;
@Repository
public interface IUserRepository extends JpaRepository<User, String> {}
