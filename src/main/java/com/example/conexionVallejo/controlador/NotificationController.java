package com.example.conexionVallejo.controlador;

import com.example.conexionVallejo.modelos.Notification;
import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.repositorios.UserRepository;
import com.example.conexionVallejo.servicios.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/notifications")
    public ResponseEntity<List<Notification>> getNotifications(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String loggedInUsername = authentication.getName();
        User user = userRepository.findByEmailAddress(loggedInUsername).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        List<Notification> notifications = notificationService.getNotificationsForUser(user);
        return ResponseEntity.ok(notifications);
    }


    @GetMapping("/notifications/markAsRead/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        String loggedInUsername = authentication.getName();
        User user = userRepository.findByEmailAddress(loggedInUsername).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no autenticado");
        }

        Notification notification = notificationService.getNotificationsForUser(user).stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (notification == null) {
            return ResponseEntity.notFound().build();
        }

        notificationService.markNotificationAsRead(notification);
        return ResponseEntity.ok().build();
    }
}
