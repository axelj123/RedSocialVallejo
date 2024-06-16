package com.example.conexionVallejo.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.repositorios.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UserService {

    private static final String UPLOAD_DIR = "uploads/";
   
    @Autowired
    private UserRepository userRepository;

    public void actualizarPerfilUsuario(Authentication authentication, User user, MultipartFile file) throws IOException {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String emailAddress = userDetails.getUsername();
            Optional<User> optionalUser = userRepository.findByEmailAddress(emailAddress);
            if (optionalUser.isPresent()) {
                User existingUser = optionalUser.get();
                // Actualiza el perfil del usuario con la información proporcionada
                existingUser.setDisplayName(user.getDisplayName());
                existingUser.setAboutMe(user.getAboutMe());
                existingUser.setCarrera(user.getCarrera());
                existingUser.setFacebookUrl(user.getFacebookUrl());
                existingUser.setTiktokUrl(user.getTiktokUrl());
                existingUser.setLinkedinUrl(user.getLinkedinUrl());
                existingUser.setInstagramUrl(user.getInstagramUrl());

                if (!file.isEmpty()) {
                    // Maneja la carga de la imagen de perfil
                    String fileName = file.getOriginalFilename();
                    Path filePath = Paths.get(UPLOAD_DIR + fileName);
                    Files.createDirectories(filePath.getParent());
                    Files.write(filePath, file.getBytes());
                    existingUser.setProfileImage("/" + UPLOAD_DIR + fileName);
                }

                userRepository.save(existingUser);
            } else {
                throw new IllegalArgumentException("El usuario no existe");
            }
        } else {
            throw new IllegalArgumentException("Usuario no autenticado");
        }
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailAddress(email);
    }
    
}
