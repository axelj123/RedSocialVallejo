package com.example.conexionVallejo.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.conexionVallejo.modelos.User;
import com.example.conexionVallejo.repositorios.UserRepository;




@Controller
@RestController
public class RegistrationController {

	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
@PostMapping ("/register/user")	
public User createUser (@RequestBody User user) {
	user.setPassword(passwordEncoder.encode(user.getPassword()));
	return userRepository.save(user);
}
	
	
}
