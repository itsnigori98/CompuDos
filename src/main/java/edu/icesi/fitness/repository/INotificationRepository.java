package edu.icesi.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.icesi.fitness.model.Notification;
@Repository
public interface INotificationRepository extends JpaRepository<Notification, String> {}
