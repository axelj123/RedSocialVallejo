package com.example.conexionVallejo.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	

@Autowired
JavaMailSender javaMailSender;
@Autowired
TemplateEngine templateEngine;

public void sendEmail() {
	SimpleMailMessage message= new SimpleMailMessage();
	message.setFrom("axeljhosmell13@gmail.com");
	message.setTo("axeljhosmell13@gmail.com");
	message.setSubject("Prueba de envio email simple");
	message.setText("Esto es el contenido del email");
	
	javaMailSender.send(message);

}


public void sendEmailTemplate() {
	MimeMessage message=javaMailSender.createMimeMessage();
	try {
		MimeMessageHelper helper=new MimeMessageHelper(message,true);		
		Context context=new Context();
		String htmlText=templateEngine.process("email-template", context);
		helper.setFrom("axeljhosmell13@gmail.com");
		helper.setTo("axeljhosmell13@gmail.com");
		helper.setSubject("Prueba de envio email simple");
		helper.setText(htmlText,true);
		javaMailSender.send(message);
	} catch (MessagingException e) {

	}
}

public void sendResetEmail(String email) {
	MimeMessage message=javaMailSender.createMimeMessage();
	try {
		MimeMessageHelper helper=new MimeMessageHelper(message,true);		
		Context context=new Context();
		String htmlText=templateEngine.process("email-template", context);
		helper.setFrom("axeljhosmell13@gmail.com");
		helper.setTo(email);
		helper.setSubject("Prueba de envio email simple");
		helper.setText(htmlText,true);
		javaMailSender.send(message);
	} catch (MessagingException e) {

	}
}

}
