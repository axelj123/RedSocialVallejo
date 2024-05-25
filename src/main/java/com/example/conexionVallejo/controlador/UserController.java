package com.example.conexionVallejo.controlador;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.servicios.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(@RequestParam("profile_picture") MultipartFile file, User user, Model model, Authentication authentication) {
        try {
            userService.actualizarPerfilUsuario(authentication, user, file);
            return "redirect:/perfil?tab=info"; // Redirigir a la página de perfil con la pestaña de información activa
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error al guardar la imagen de perfil.");
            return "redirect:/perfil?tab=info"; // Redirigir a la página de perfil con un mensaje de error
        }
    }

}