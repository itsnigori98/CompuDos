package edu.icesi.fitness.repository;

import edu.icesi.fitness.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findByNameContainingIgnoreCaseOrderByDateAsc(String name);

    List<Event> findByDateAfterOrderByDateAsc(Date from);

    List<Event> findByUsers_IdOrderByDateAsc(Integer userId);

    List<Event> findByNotificationId_IdOrderByDateAsc(Integer notificationId);
}

