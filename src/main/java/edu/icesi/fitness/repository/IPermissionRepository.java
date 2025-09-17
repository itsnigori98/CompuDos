package edu.icesi.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.fitness.model.Permission;
@Repository
public interface IPermissionRepository extends JpaRepository<Permission, String> {}
