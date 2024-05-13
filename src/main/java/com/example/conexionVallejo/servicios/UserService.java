package com.example.conexionVallejo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.conexionVallejo.repositorios.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
}
