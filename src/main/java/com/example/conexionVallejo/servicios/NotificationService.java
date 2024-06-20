package com.example.conexionVallejo.servicios;

import com.example.conexionVallejo.modelos.Notification;
import com.example.conexionVallejo.repositorios.NotificationRepository;
import com.example.conexionVallejo.modelos.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;


    public void createNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsForUser(User user) {
        return notificationRepository.findByUserOrderByCreatedDateDesc(user);
    }

    public void markNotificationAsRead(Notification notification) {
        notification.setRead(true);
        notificationRepository.save(notification);
    }
    // Método para calcular la antigüedad de las notificaciones
    public void calculateNotificationAges(List<Notification> notifications) {
        for (Notification notification : notifications) {
            String age = AgeCalculatorService.calculatePostAge(notification.getCreatedDate().toInstant());
            notification.setAge(age);
        }
    }
}
