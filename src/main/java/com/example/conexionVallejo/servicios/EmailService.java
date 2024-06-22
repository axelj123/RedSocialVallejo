	package com.example.conexionVallejo.servicios;

	import jakarta.mail.internet.AddressException;
	import jakarta.mail.internet.InternetAddress;
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

		public void sendEmail(String to, String subject, String templateName, Context context) throws MessagingException {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom("axeljhosmell13@gmail.com");
			helper.setTo(to);
			helper.setSubject(subject);

			// Procesar plantilla Thymeleaf
			String htmlContent = templateEngine.process(templateName, context);
			helper.setText(htmlContent, true); // true indica que es HTML

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
	public boolean isEmailValid(String email) {
		// Validar el formato del correo electrónico
		try {
			InternetAddress internetAddress = new InternetAddress(email);
			internetAddress.validate();
		} catch (AddressException ex) {
			return false;
		}

		// Validar el dominio del correo electrónico (ejemplo: ucvvirtual.edu.pe)
		return email.endsWith("ucvvirtual.edu.pe");
	}

	public void sendConfirmationEmail(String email, String subject, String confirmationUrl) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			Context context = new Context();
			context.setVariable("confirmationUrl", confirmationUrl);
			String htmlText = templateEngine.process("confirmation-email-template", context);
			helper.setFrom("axeljhosmell13@gmail.com");
			helper.setTo(email);
			helper.setSubject(subject);
			helper.setText(htmlText, true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send email", e);
		}
	}
}
