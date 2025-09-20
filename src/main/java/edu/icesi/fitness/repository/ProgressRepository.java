package edu.icesi.fitness.repository;

import edu.icesi.fitness.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Integer> {

    Optional<Progress> findTopByUser_IdOrderByDateDesc(Integer userId);

    List<Progress> findByUser_IdAndDateBetweenOrderByDateAsc(Integer userId, Date from, Date to);
}

