package com.example.conexionVallejo.security;

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
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmailAddress())
                    .password(user.getPassword()) // Aquí debes proporcionar la contraseña codificada del usuario
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo electrónico: " + emailAddress);
        }
    }
    
    
    
}
