package edu.icesi.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.fitness.model.Exercise;
@Repository
public interface IExerciseRepository extends JpaRepository<Exercise, String> {}
