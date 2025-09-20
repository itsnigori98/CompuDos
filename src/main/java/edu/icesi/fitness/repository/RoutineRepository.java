package edu.icesi.fitness.repository;

import edu.icesi.fitness.model.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    List<Routine> findByNameContainingIgnoreCaseOrderByIdAsc(String name);

    List<Routine> findByUsers_IdOrderByIdAsc(Integer userId);
}

