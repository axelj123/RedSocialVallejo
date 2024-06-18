package com.example.conexionVallejo.repositorios;

import com.example.conexionVallejo.modelos.Notification;
import com.example.conexionVallejo.modelos.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedDateDesc(User user);
}
