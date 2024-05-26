package com.example.conexionVallejo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.conexionVallejo.servicios.EmailRequest;
import com.example.conexionVallejo.servicios.EmailService;

@RestController

public class EmailController {

	@Autowired
	EmailService emailService;

	@Autowired
	UserService userService;
	@GetMapping("/email/send")

	public ResponseEntity<?> sendEmail(){
		emailService.sendEmail();
		return new ResponseEntity("Corre enviado con exito",HttpStatus.OK);
	}
	
	@GetMapping("/email/send-correo")

	public ResponseEntity<?> sendEmailHtml(){
		emailService.sendEmailTemplate();
		return new ResponseEntity("Corre con plantilla enviado con exito",HttpStatus.OK);
	}
	@PostMapping("/send-reset-email")
	public ResponseEntity<String> sendResetEmail(@ModelAttribute("emailRequest") EmailRequest emailRequest) {
	    String email = emailRequest.getEmail();
	    if (!userService.existsByEmail(email)) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El correo no existe en la base de datos");
	    }
	    emailService.sendResetEmail(email);
	    return ResponseEntity.ok("Correo enviado con éxito");
	}
	


	


}
