package edu.icesi.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.fitness.model.Event;
@Repository
public interface IEventRepository extends JpaRepository<Event, String> {}
