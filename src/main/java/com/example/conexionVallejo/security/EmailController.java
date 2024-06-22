package com.example.conexionVallejo.security;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.conexionVallejo.servicios.EmailRequest;
import com.example.conexionVallejo.servicios.EmailService;
import org.thymeleaf.context.Context;

import java.util.UUID;

@RestController

public class EmailController {

	@Autowired
	EmailService emailService;

	@Autowired
	UserService userService;

	@PostMapping("/send-email")
	public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
		try {
			// Preparar el contexto para la plantilla Thymeleaf
			Context context = new Context();
			context.setVariable("message", emailRequest.getMessage());

			// Enviar el correo electrónico
			emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(), "email-template", context);

			return ResponseEntity.ok("Correo enviado correctamente.");
		} catch (MessagingException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al enviar el correo: " + e.getMessage());
		}
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
