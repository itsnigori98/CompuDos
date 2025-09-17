package edu.icesi.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.fitness.model.Progress;
@Repository
public interface IProgressRepository extends JpaRepository<Progress, String> {}
