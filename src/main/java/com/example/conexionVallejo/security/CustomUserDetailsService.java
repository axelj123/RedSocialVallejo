package com.example.conexionVallejo.security;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.repositorios.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmailAddress(emailAddress);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Cargar roles del usuario (aquí asumiendo que tienes un método en UserRepository para cargar los roles)
            List<String> roles = userRepository.getUserRoles(user.getId());

            // Construir UserDetails con roles
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmailAddress())
                    .password(user.getPassword()) // Debes proporcionar la contraseña codificada del usuario
                    .roles(roles.toArray(new String[0])) // Asignar roles al UserDetails
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo electrónico: " + emailAddress);
        }
    }
    
    
}
