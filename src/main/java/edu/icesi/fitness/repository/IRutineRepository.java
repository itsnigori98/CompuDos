package edu.icesi.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.fitness.model.Rutine;
@Repository
public interface IRutineRepository extends JpaRepository<Rutine, String> {}
