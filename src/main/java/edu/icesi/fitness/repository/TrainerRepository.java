package edu.icesi.fitness.repository;

import edu.icesi.fitness.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    List<Trainer> findByNameContainingIgnoreCaseOrderByIdAsc(String name);
    List<Trainer> findBySpecialtyContainingIgnoreCaseOrderByIdAsc(String specialty);
}

