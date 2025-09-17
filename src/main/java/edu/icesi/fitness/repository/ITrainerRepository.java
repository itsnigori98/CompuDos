package edu.icesi.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.fitness.model.Trainer;
@Repository
public interface ITrainerRepository extends JpaRepository<Trainer, String> {}
