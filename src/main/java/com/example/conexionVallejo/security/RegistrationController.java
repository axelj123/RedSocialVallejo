    package com.example.conexionVallejo.security;

    import com.example.conexionVallejo.modelos.VerificationToken;
    import com.example.conexionVallejo.repositorios.VerificationTokenRepository;
    import com.example.conexionVallejo.servicios.EmailService;
    import jakarta.servlet.http.HttpServletRequest;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.AuthenticationException;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    import com.example.conexionVallejo.modelos.User;
    import com.example.conexionVallejo.repositorios.UserRepository;
    import org.springframework.web.servlet.ModelAndView;

    import java.util.Calendar;
    import java.util.Date;
    import java.util.UUID;

    @Controller
    public class RegistrationController {

        @Autowired
        private UserRepository userRepository;
        @Autowired
        private EmailService emailService;
        @Autowired
        private PasswordEncoder passwordEncoder;
        @Autowired
        private VerificationTokenRepository tokenRepository;
        @Autowired
        private UserService userService;
        @Autowired
        private AuthenticationManager authenticationManager;

        @PostMapping("/register/user")
        public String createUser(
                @RequestParam String displayName,
                @RequestParam String emailAddress,
                @RequestParam String password,
                @RequestParam(required = false) String aboutMe,
                @RequestParam(required = false) String carrera,
                @RequestParam(required = false) String profileImage,
                @RequestParam(required = false) String facebookUrl,
                @RequestParam(required = false) String linkedinUrl,
                @RequestParam(required = false) String instagramUrl,
                @RequestParam(required = false) String tiktokUrl,
                Model model) {

            // Validación básica de correo electrónico
            if (!emailService.isEmailValid(emailAddress)) {
                model.addAttribute("error", "Invalid or unauthorized email address");
                return "redirect:/login";
            }

            // Verificar si el correo electrónico ya está en uso
            if (userRepository.existsByEmailAddress(emailAddress)) {
                model.addAttribute("error", "Email already in use");
                return "redirect:/login";
            }

            // Codificar la contraseña antes de guardar el usuario
            String encodedPassword = passwordEncoder.encode(password);

            // Crear el objeto User y setear los campos
            User user = new User();
            user.setDisplayName(displayName);
            user.setEmailAddress(emailAddress);
            user.setPassword(encodedPassword);
            user.setAboutMe(aboutMe);
            user.setCarrera(carrera);
            user.setProfileImage(profileImage);
            user.setFacebookUrl(facebookUrl);
            user.setLinkedinUrl(linkedinUrl);
            user.setInstagramUrl(instagramUrl);
            user.setTiktokUrl(tiktokUrl);

            // Guardar el usuario en la base de datos
            userRepository.save(user);

            // Eliminar el token después del registro (si es necesario)
            VerificationToken token = tokenRepository.findByUserEmail(emailAddress);
            if (token != null) {
                tokenRepository.delete(token);
            }

            // Agregar atributo para mostrar mensaje en la vista de login
            model.addAttribute("registered", true);

            // Redirigir al usuario a /login
            return "redirect:/login";
        }




        @PostMapping("/send-confirmation-email")
        public ResponseEntity<String> sendConfirmationEmail(@RequestParam("email") String email, HttpServletRequest request) {
            // Verificar si el correo electrónico tiene el dominio deseado y está bien formateado
            if (!emailService.isEmailValid(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or unauthorized email address");
            }

            // Verificar si el correo electrónico ya está en uso
            if (userService.existsByEmail(email)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
            }

            // Crear un token de verificación
            String token = UUID.randomUUID().toString();
            Date expiryDate = calculateExpiryDate(); // Método para calcular la fecha de vencimiento
            VerificationToken verificationToken = new VerificationToken(token, email, expiryDate);
            tokenRepository.save(verificationToken);

            // Construir la URL de activación
            String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            String confirmationUrl = appUrl + "/confirmRegistration?token=" + token;

            // Enviar el correo electrónico de confirmación
            emailService.sendConfirmationEmail(email, "Confirmación de Registro", confirmationUrl);

            return ResponseEntity.status(HttpStatus.OK).body("Confirmation email sent successfully");
        }


        @GetMapping("/confirmRegistration")
        public String ss(@RequestParam("token") String token, Model model) {
            VerificationToken verificationToken = tokenRepository.findByToken(token);

            if (verificationToken == null) {
                model.addAttribute("error", "El token de verificación es inválido.");
                return "error"; // Página de error personalizada
            }

            // Prellenar el formulario con el correo electrónico
            User user = new User();
            user.setEmailAddress(verificationToken.getUserEmail());
            model.addAttribute("user", user);

            // Redirigir al formulario de registro con el correo electrónico prellenado
            return "RegistrationForm";
        }

        private Date calculateExpiryDate() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.HOUR, 24); // Expira en 24 horas
            return calendar.getTime();
        }
    }
