package com.example.conexionVallejo.security;

import com.example.conexionVallejo.servicios.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.repositorios.UserRepository;

@RestController
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register/user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Verificar si el correo electrónico tiene el dominio deseado y está bien formateado
        if (!emailService.isEmailValid(user.getEmailAddress())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or unauthorized email address");
        }

        // Verificar si el correo electrónico ya está en uso
        if (userRepository.existsByEmailAddress(user.getEmailAddress())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        // Codificar la contraseña antes de guardar el usuario
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Guardar el usuario en la base de datos
        userRepository.save(user);

        // Respuesta de éxito
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

}
