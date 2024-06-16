package com.example.conexionVallejo.controlador;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.repositorios.UserRepository;
import com.example.conexionVallejo.security.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
private UserRepository userRepository;

    @PostMapping("/perfil/actualizar")
    public ResponseEntity<Map<String, String>> actualizarPerfil(@RequestParam("profile_picture") MultipartFile file, User user, Authentication authentication) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.actualizarPerfilUsuario(authentication, user, file);
            response.put("message", "Perfil actualizado correctamente");
            response.put("type", "success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("message", "Error al guardar la imagen de perfil.");
            response.put("type", "error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/perfil/{userId}")
    public String verPerfilPublico(@PathVariable Long userId, Model model) {
        // Aquí obtienes los datos del usuario con el ID proporcionado y los agregas al modelo
        // Por simplicidad, asumiremos que ya tienes el objeto User preparado
        User user = userRepository.findById(userId).orElse(null);
        model.addAttribute("user", user);
        
        return "perfil-publico"; // El nombre de la plantilla HTML para el perfil público
    }
    
    
    

}