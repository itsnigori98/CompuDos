package edu.icesi.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.fitness.model.History;
@Repository
public interface IHistoryRepository extends JpaRepository<History, String> {}
