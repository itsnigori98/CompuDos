package edu.icesi.fitness.repository;


import edu.icesi.fitness.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByTitleContainingIgnoreCaseOrderByIdAsc(String title);

    List<Notification> findByUsers_IdOrderByIdAsc(Integer userId);
}

