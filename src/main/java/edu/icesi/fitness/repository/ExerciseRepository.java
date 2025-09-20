package edu.icesi.fitness.repository;


import edu.icesi.fitness.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByNameContainingIgnoreCaseOrderByIdAsc(String name);
}

